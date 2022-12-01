package constants;

import java.awt.*;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.GamePanel;
import data_structure.*;
import board.*;
import static constants.Constants.*;

public class PieceData {
    private PieceData() {}

    public static Image lionPlayer = new ImageIcon("./src/images/lion.png").getImage();
    public static Image lionEnemy = new ImageIcon("./src/images/lion-reverse.png").getImage();
    public static Image elephantPlayer = new ImageIcon("./src/images/elephant.png").getImage();
    public static Image elephantEnemy = new ImageIcon("./src/images/elephant-reverse.png").getImage();
    public static Image giraffePlayer = new ImageIcon("./src/images/giraffe.png").getImage();
    public static Image giraffeEnemy = new ImageIcon("./src/images/giraffe-reverse.png").getImage();
    public static Image chickinPlayer = new ImageIcon("./src/images/chicken.png").getImage();
    public static Image chickinEnemy = new ImageIcon("./src/images/chicken-reverse.png").getImage();
    public static Image chickPlayer = new ImageIcon("./src/images/chick.png").getImage();
    public static Image chickEnemy = new ImageIcon("./src/images/chick-reverse.png").getImage();
    
    public static Image flameGreen = new ImageIcon("./src/images/flame-green.png").getImage();
    public static Image flameYellow = new ImageIcon("./src/images/flame-yellow.png").getImage();

    // 将来的に角や飛車等、場所によって変わるような駒を実装するときにはposを受け取れば良い
    public static ArrayList<Pos> getMoves(PieceEnum type) {
        ArrayList<Pos> moves = new ArrayList<>();
        switch (type) {
            case EMPTY:
                break;
            case LION_PLAYER, LION_ENEMY:
                moves.add(new Pos(1, 0));
                moves.add(new Pos(-1, 0));
                moves.add(new Pos(0, 1));
                moves.add(new Pos(0, -1));
                moves.add(new Pos(1, 1));
                moves.add(new Pos(1, -1));
                moves.add(new Pos(-1, 1));
                moves.add(new Pos(-1, -1));
                break;
            case ELEPHANT_PLAYER, ELEPHANT_ENEMY:
                moves.add(new Pos(1, 1));
                moves.add(new Pos(1, -1));
                moves.add(new Pos(-1, 1));
                moves.add(new Pos(-1, -1));
                break;
            case GIRAFFE_PLAYER, GIRAFFE_ENEMY:
                moves.add(new Pos(1, 0));
                moves.add(new Pos(-1, 0));
                moves.add(new Pos(0, 1));
                moves.add(new Pos(0, -1));
                break;
            case CHICK_PLAYER:
                moves.add(new Pos(-1, 0));
                break;
            case CHICK_ENEMY:
                moves.add(new Pos(1, 0));
                break;
            case CHICKEN_PLAYER:
                moves.add(new Pos(-1, 0));
                moves.add(new Pos(-1, 1));
                moves.add(new Pos(-1, -1));
                moves.add(new Pos(0, 1));
                moves.add(new Pos(0, -1));
                moves.add(new Pos(1, 0));
                break;
            case CHICKEN_ENEMY:
                moves.add(new Pos(1, 0));
                moves.add(new Pos(1, 1));
                moves.add(new Pos(1, -1));
                moves.add(new Pos(0, 1));
                moves.add(new Pos(0, -1));
                moves.add(new Pos(-1, 0));
                break;
        }
        return moves;
    }

    public static PieceEnum pickup(PieceEnum type) {
        switch (type) {
            case LION_PLAYER:
                return PieceEnum.LION_ENEMY;
            case LION_ENEMY:
                return PieceEnum.LION_PLAYER;
            case ELEPHANT_PLAYER:
                return PieceEnum.ELEPHANT_ENEMY;
            case ELEPHANT_ENEMY:
                return PieceEnum.ELEPHANT_PLAYER;
            case GIRAFFE_PLAYER:
                return PieceEnum.GIRAFFE_ENEMY;
            case GIRAFFE_ENEMY:
                return PieceEnum.GIRAFFE_PLAYER;
            case CHICK_PLAYER:
                return PieceEnum.CHICK_ENEMY;
            case CHICK_ENEMY:
                return PieceEnum.CHICK_PLAYER;
            case CHICKEN_PLAYER:
                return PieceEnum.CHICK_ENEMY;
            case CHICKEN_ENEMY:
                return PieceEnum.CHICK_PLAYER;
            default:
                return PieceEnum.EMPTY;
        }
    }

    public static PieceEnum evolution(PieceEnum type, int h, int w) {
        if (type == PieceEnum.CHICK_PLAYER && h == 0) {
            return PieceEnum.CHICKEN_PLAYER;
        } else if (type == PieceEnum.CHICK_ENEMY && h == 4) {
            return PieceEnum.CHICKEN_ENEMY;
        }
        return null;
    }

    public static boolean isPlayer(PieceEnum type) {
        switch (type) {
            case LION_PLAYER, ELEPHANT_PLAYER, GIRAFFE_PLAYER, CHICK_PLAYER, CHICKEN_PLAYER:
                return true;
            default:
                return false;
        }
    }

    public static boolean isEnemy(PieceEnum type) {
        switch (type) {
            case LION_ENEMY, ELEPHANT_ENEMY, GIRAFFE_ENEMY, CHICK_ENEMY, CHICKEN_ENEMY:
                return true;
            default:
                return false;
        }
    }

    public static boolean isKing(PieceEnum type) {
        switch (type) {
            case LION_PLAYER, LION_ENEMY:
                return true;
            default:
                return false;
        }
    }

    public static void drawCell(Graphics g, int x, int y, PieceEnum type, PieceState state, GamePanel observer) {

        if (type == PieceEnum.LION_PLAYER) {
            g.drawImage(lionPlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
        }

        switch (type) {
            case LION_PLAYER:   
                g.drawImage(lionPlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case LION_ENEMY:   
                g.drawImage(lionEnemy, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case ELEPHANT_PLAYER:
                g.drawImage(elephantPlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case ELEPHANT_ENEMY:
                g.drawImage(elephantEnemy, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case GIRAFFE_PLAYER:
                g.drawImage(giraffePlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case GIRAFFE_ENEMY:
                g.drawImage(giraffeEnemy, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case CHICKEN_PLAYER:
                g.drawImage(chickinPlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case CHICKEN_ENEMY:
                g.drawImage(chickinEnemy, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case CHICK_PLAYER:
                g.drawImage(chickPlayer, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case CHICK_ENEMY:
                g.drawImage(chickEnemy, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
        }

        switch (state) {
            case NORMAL:
                g.setColor(Color.black);
                g.drawRect(x, y, BOARD_CELL_SIZE - 1, BOARD_CELL_SIZE - 1);
                break;
            case SELECT:
                g.drawImage(flameGreen, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
            case CANMOVE:
                g.drawImage(flameYellow, x, y, BOARD_CELL_SIZE, BOARD_CELL_SIZE, observer);
                break;
        }
    }

    public static void drawHand(Graphics g, int x, int y, PieceEnum type, boolean isSelect, GamePanel observer) {
        // TODO: 個数を表示する
        switch (type) {
            case LION_PLAYER:   
                g.drawImage(lionPlayer, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case LION_ENEMY:   
                g.drawImage(lionEnemy, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case ELEPHANT_PLAYER:
                g.drawImage(elephantPlayer, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case ELEPHANT_ENEMY:
                g.drawImage(elephantEnemy, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case GIRAFFE_PLAYER:
                g.drawImage(giraffePlayer, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case GIRAFFE_ENEMY:
                g.drawImage(giraffeEnemy, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case CHICKEN_PLAYER:
                g.drawImage(chickinPlayer, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case CHICKEN_ENEMY:
                g.drawImage(chickinEnemy, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case CHICK_PLAYER:
                g.drawImage(chickPlayer, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
            case CHICK_ENEMY:
                g.drawImage(chickEnemy, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
                break;
        }
        if (isSelect) {
            g.drawImage(flameGreen, x, y, HAND_CELL_SIZE, HAND_CELL_SIZE, observer);
        } else {
            g.setColor(Color.black);
            g.drawRect(x, y, HAND_CELL_SIZE, HAND_CELL_SIZE);
        }
    }
}
