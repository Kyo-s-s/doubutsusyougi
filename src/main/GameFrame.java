package main;

import javax.swing.JFrame;

import static constants.Constants.*;

public class GameFrame extends JFrame {
    public GamePanel panel;

    public GameFrame() {
        this.panel = new GamePanel();
        this.add(this.panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("doubutsushyougi");
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);

        this.addMouseListener(this.panel);
    }

}
