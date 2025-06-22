package figures;

import main.Movement;
import main.PlayManager;

import java.awt.*;

public class Figures {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision,rightCollision,bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }
    public void setXY(int x,int y){}
    public void updateXY(int direction){

        checkRotationCollision();
            if (leftCollision == false && rightCollision == false && bottomCollision == false) {

                this.direction = direction;
                b[0].x = tempB[0].x;
                b[0].y = tempB[0].y;
                b[1].x = tempB[1].x;
                b[1].y = tempB[1].y;
                b[2].x = tempB[2].x;
                b[2].y = tempB[2].y;
                b[3].x = tempB[3].x;
                b[3].y = tempB[3].y;
            }
    }
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for(int i = 0; i < b.length; i++){
            if(b[i].x == PlayManager.left_x){
                leftCollision = true;
                }
            }
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.size == PlayManager.right_x){
                rightCollision = true;
            }
        }
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.size == PlayManager.bottom_y){
                bottomCollision = true;
            }
        }
    }
    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < PlayManager.left_x){
                leftCollision = true;
            }
        }
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x + Block.size > PlayManager.right_x){
                rightCollision = true;
            }
        }
        for(int i = 0; i < b.length; i++){
            if(tempB[i].y + Block.size > PlayManager.bottom_y){
                bottomCollision = true;
            }
        }
    }
    private void checkStaticBlockCollision(){

        for(int i = 0; i < PlayManager.staticBlocks.size();i++){
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for(int ind = 0; ind < b.length; ind++){
                if(b[ind].y + Block.size == targetY && b[ind].x == targetX){
                    bottomCollision = true;
                }
            }
            for(int ind = 0; ind < b.length; ind++){
                if(b[ind].x - Block.size == targetX && b[ind].y == targetY){
                    leftCollision = true;
                }
            }
            for(int ind = 0; ind < b.length; ind++){
                if(b[ind].x + Block.size == targetX && b[ind].y == targetY){
                    rightCollision = true;
                }
            }
        }
    }
    public void update(){

        if(deactivating){
            deactivating();
        }

        if(Movement.UP){
            switch (direction){
                case 1:getDirection2();break;
                case 2:getDirection3();break;
                case 3:getDirection4();break;
                case 4:getDirection1();break;
            }
            Movement.UP = false;
        }

        checkMovementCollision();

        if(Movement.DOWN){
            if(bottomCollision == false) {
                b[0].y += Block.size;
                b[1].y += Block.size;
                b[2].y += Block.size;
                b[3].y += Block.size;
                autoDropCounter = 0;
            }
                Movement.DOWN = false;
        }
        if(Movement.LEFT) {
            if(leftCollision == false) {
                b[0].x -= Block.size;
                b[1].x -= Block.size;
                b[2].x -= Block.size;
                b[3].x -= Block.size;
            }
                Movement.LEFT = false;
        }
        if(Movement.RIGHT){
            if(rightCollision == false) {
                b[0].x += Block.size;
                b[1].x += Block.size;
                b[2].x += Block.size;
                b[3].x += Block.size;
            }
                Movement.RIGHT = false;
        }

            if(bottomCollision) {
                deactivating = true;
            }
            else {
                autoDropCounter++;
                if(autoDropCounter == PlayManager.dropInterval) {
                    b[0].y += Block.size;
                    b[1].y += Block.size;
                    b[2].y += Block.size;
                    b[3].y += Block.size;
                    autoDropCounter = 0;
            }
        }
    }

    private void deactivating() {
        deactivateCounter++;

        if(deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();

            if(bottomCollision){
                active = false;
            }
        }
    }
    public void draw(Graphics2D g2){

            int margin = 2;
            g2.setColor(b[0].c);
            g2.fillRect(b[0].x+margin,b[0].y+margin, Block.size- (margin*2), Block.size- (margin*2));
            g2.fillRect(b[1].x+margin,b[1].y+margin, Block.size- (margin*2), Block.size- (margin*2));
            g2.fillRect(b[2].x+margin,b[2].y+margin, Block.size- (margin*2), Block.size- (margin*2));
            g2.fillRect(b[3].x+margin,b[3].y+margin, Block.size- (margin*2), Block.size- (margin*2));
    }
}
