import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import static constants.Constants.*;

public class GamePanel extends JPanel implements ActionListener {
    Timer timer;
    public GamePanel() {
        // TODO:  MouseListener
        timer = new Timer(50, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // TODO: draw Board
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
