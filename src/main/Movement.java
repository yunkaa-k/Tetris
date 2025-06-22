package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movement implements KeyListener {

    public static boolean UP, DOWN, LEFT, RIGHT, PAUSE;

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            UP = true;
        }
        if(code == KeyEvent.VK_A){
            LEFT = true;
        }
        if(code == KeyEvent.VK_S){
            DOWN = true;
        }
        if(code == KeyEvent.VK_D){
            RIGHT = true;
        }
        if(code == KeyEvent.VK_SPACE){
            if(PAUSE){
                PAUSE = false;
            }
            else {
                PAUSE = true;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
