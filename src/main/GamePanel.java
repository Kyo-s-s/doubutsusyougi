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
    public static GameMode gameMode;
    static Image titleImage = new ImageIcon("./src/images/titlepage.png").getImage();
    static Image resultImageWin = new ImageIcon("./src/images/result-win.png").getImage();
    static Image resultImageLose = new ImageIcon("./src/images/result-lose.png").getImage();
    static Image playImageEasy = new ImageIcon("./src/images/backeasy.png").getImage();

    public GamePanel() {
        gameState = GameState.START;
        gameMode = GameMode.EASY;
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        switch (gameState) {
            case START:
                g.drawImage(titleImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                break;
            case PLAY:
                g.drawImage(playImageEasy, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                Board.draw(g, this);
                break;
            case RESULT_WIN:
                g.drawImage(resultImageWin, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                break;
            case RESULT_LOSE:
                g.drawImage(resultImageLose, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                break;
        }

    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    boolean isCircleClicked(int x, int y, int cx, int cy, int r) {
        return (x - cx) * (x - cx) + (y - cy) * (y - cy) < r * r;
    }

    public void mouseClicked(MouseEvent e) {
        // 上のタイトルバーの分で-30
        switch (gameState) {
            case START:
                // 当たり判定
                boolean isEasyClicked = isCircleClicked(e.getX(), e.getY() - 30, EASY_X, EASY_Y, RADIUS);
                boolean isNormalClicked = isCircleClicked(e.getX(), e.getY() - 30, NORMAL_X, NORMAL_Y, RADIUS);
                boolean isHardClicked = isCircleClicked(e.getX(), e.getY() - 30, HARD_X, HARD_Y, RADIUS);
                if (isEasyClicked) {
                    gameMode = GameMode.EASY;
                } else if (isNormalClicked) {
                    gameMode = GameMode.NORMAL;
                } else if (isHardClicked) {
                    gameMode = GameMode.HARD;
                }

                if (isEasyClicked || isNormalClicked || isHardClicked) {
                    gameState = GameState.PLAY;
                    Board.init();
                }
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
