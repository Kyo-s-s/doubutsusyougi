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

        Image lion = new ImageIcon("lion.png").getImage();

        switch (state) {
            case NORMAL:
                if (isPlayer(type)) {
                    g.setColor(Color.blue);
                } else if (isEnemy(type)) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }
                break;
            case SELECT:
                g.setColor(Color.green);
                break;
            case CANMOVE:
                g.setColor(Color.yellow);
                break;
        }
        g.drawString("" + type, x + 30, y + 30);
        if(type == PieceEnum.LION_PLAYER) {
            g.drawImage(lion, x, y, observer);
        }
        g.drawRect(x, y, BOARD_CELL_SIZE - 1, BOARD_CELL_SIZE - 1);
    }

    public static void drawHand(Graphics g, int x, int y, PieceEnum type, boolean isSelect) {
        // TODO: 個数を表示する
        if (isSelect) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.black);
        }
        g.drawString("" + type, x + 30, y + 30);
        g.drawRect(x, y, HAND_CELL_SIZE, HAND_CELL_SIZE);
    }
}
