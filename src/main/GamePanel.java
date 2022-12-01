package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import board.Board;

import static constants.Constants.*;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    Timer timer;
    Board board;

    public GamePanel() {
        // board = new Board_before();
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        Board.draw(g, this);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        // 上のタイトルバーの分で-30
        Board.click(e.getX(), e.getY() - 30, this.getGraphics(), this);
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
