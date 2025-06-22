package main;

import javax.swing.*;

public class Main {

    private JFrame menuWindow;
    private JFrame gameWindow;

    public static void main(String[] args) {
        Main app = new Main();
        app.showMenu();
    }

    private void showMenu() {
        menuWindow = new JFrame("Tetris");
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setSize(400, 300);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setResizable(false);

        TetrisMenu menuPanel = new TetrisMenu(this);
        menuWindow.add(menuPanel);
        menuWindow.setVisible(true);
    }

    public void startGame() {
        if (menuWindow != null) {
            menuWindow.dispose();
        }

        gameWindow = new JFrame("Tetris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        GamePanel gp = new GamePanel();
        gameWindow.add(gp);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        gp.StartGame();
    }
}
