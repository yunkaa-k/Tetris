package main;

import javax.swing.*;
import java.awt.*;

public class TetrisMenu extends JPanel {

    private Main mainApp;

    public TetrisMenu(Main mainApp) {
        this.mainApp = mainApp;

        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> mainApp.startGame());
        add(startButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}
