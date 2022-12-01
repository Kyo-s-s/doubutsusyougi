package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import board.Board;

import static constants.Constants.*;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    Timer timer;
    Board board;
    public static GameState gameState;

    public GamePanel() {
        // board = new Board_before();
        gameState = GameState.START;
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        switch (gameState) {
            case START:
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Start", SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2);
                break;
            case PLAY:
                Board.draw(g, this);
                break;
            case RESULT_WIN:
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("You Win!", SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2);
                break;
            case RESULT_LOSE:
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("You Lose!", SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT / 2);
                break;
        }

    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        // 上のタイトルバーの分で-30
        switch (gameState) {
            case START:
                gameState = GameState.PLAY;
                Board.init();
                break;
            case PLAY:
                Board.click(e.getX(), e.getY() - 30, this.getGraphics(), this);
                break;
            case RESULT_WIN:
                gameState = GameState.START;
                break;
            case RESULT_LOSE:
                gameState = GameState.START;
                break;
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
