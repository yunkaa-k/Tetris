package main;

import figures.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    final int width = 360;
    final int height = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Figures currentFigure;
    final int Figure_start_x;
    final int Figure_start_y;
    Figures nextFigure;
    final int nextFigure_x;
    final int nextFigure_y;

    public final static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;

    boolean GameOver;

    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    int level = 1;
    int lines;
    int score;

    public PlayManager() {
        left_x = (GamePanel.width / 2) - (width / 2);
        right_x = left_x + width;
        top_y = 50;
        bottom_y = top_y + height;

        Figure_start_x = left_x + (width / 2) - Block.size;
        Figure_start_y = top_y + Block.size;

        nextFigure_x = right_x + 175;
        nextFigure_y = top_y + 530;

        currentFigure = pickFigures();
        currentFigure.setXY(Figure_start_x, Figure_start_y);
        nextFigure = pickFigures();
        nextFigure.setXY(nextFigure_x, nextFigure_y);
    }

    public Figures pickFigures() {
        Figures Figures = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0:
                Figures = new L1();
                break;
            case 1:
                Figures = new L2();
                break;
            case 2:
                Figures = new Cube();
                break;
            case 3:
                Figures = new Bar();
                break;
            case 4:
                Figures = new T();
                break;
            case 5:
                Figures = new Z1();
                break;
            case 6:
                Figures = new Z2();
                break;
        }
        return Figures;
    }

    public void update() {

        if (currentFigure.active == false){
            staticBlocks.add(currentFigure.b[0]);
            staticBlocks.add(currentFigure.b[1]);
            staticBlocks.add(currentFigure.b[2]);
            staticBlocks.add(currentFigure.b[3]);

            if (currentFigure.b[0].x == Figure_start_x && currentFigure.b[0].y == Figure_start_y){
                GameOver = true;
            }

            currentFigure.deactivating = false;

            currentFigure = nextFigure;
            currentFigure.setXY(Figure_start_x,Figure_start_y);
            nextFigure = pickFigures();
            nextFigure.setXY(nextFigure_x,nextFigure_y);

            checkDelete();
    }
    else {
            currentFigure.update();
        }
    }

    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y){
            for (int i = 0; i < staticBlocks.size();i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }

            x+= Block.size;

            if (x == right_x){

                if (blockCount == 12){

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size()-1; i> -1; i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    if (lines % 5 == 0 && dropInterval > 1){
                        level++;
                        if (dropInterval > 10){
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    for(int i = 0; i < staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.size;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y+= Block.size;
            }
        }

        if (lineCount > 0){
            int singleLineScore = 10 * level;
            score += singleLineScore + lineCount;
        }
    }
    public void draw(Graphics2D g2){

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, width+8, height+8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x,y,200,200);
        g2.setFont(new Font("Times New Roman",Font.PLAIN, 24));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("Наступна фігура", x+18, y+60);

        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("Рівень: " + level,x,y);
            y+= 70;
        g2.drawString("Ліній: " + lines,x,y);
            y+= 70;
        g2.drawString("Очки: " + score,x,y);

        if(currentFigure != null){
            currentFigure.draw(g2);
        }

        nextFigure.draw(g2);

            for(int i = 0; i < staticBlocks.size(); i++){
                staticBlocks.get(i).draw(g2);
            }

        if(effectCounterOn){
            effectCounter++;

            g2.setColor(Color.yellow);
            for (int i = 0; i < effectY.size(); i++){
                g2.fillRect(left_x,effectY.get(i), width, Block.size);
            }

            if (effectCounter == 10){
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (GameOver){
            x = 35;
            y = top_y + 320;
            g2.drawString("GAME OVER",x ,y);
        }
        if(Movement.PAUSE){
            x = 75;
            y = top_y + 100;
            g2.drawString("Пауза", x, y);
        }
    }
}
