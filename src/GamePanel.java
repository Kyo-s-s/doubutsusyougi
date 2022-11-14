import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import static constants.Constants.*;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    Timer timer;
    Board board;
    public GamePanel() {
        board = new Board();
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // TODO: draw Board
        board.draw(g); 
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        board.click(e.getX(), e.getY());
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
