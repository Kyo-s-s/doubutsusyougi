import java.awt.*;

import java.util.ArrayList;

import static constants.Constants.*;
public class Piece {

    EnumPiece type;

    public Piece(EnumPiece type) {
        this.type = type;
    }

    // {h, w} 方向への加算
    ArrayList<Pair<Integer, Integer>> moveCell() {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
        switch (type) {
            case EMPTY:
                break;
            case LION_PLAYER, LION_ENEMY:
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 0));
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, -1));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(-1, -1));
                break;
            case ELEPHANT_PLAYER, ELEPHANT_ENEMY:
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, -1));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(-1, -1));
                break;
            case GIRAFFE_PLAYER, GIRAFFE_ENEMY:
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 0));
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                break;
            case CHICK_PLAYER:
                moves.add(new Pair<Integer, Integer>(-1, 0));
                break;
            case CHICK_ENEMY:
                moves.add(new Pair<Integer, Integer>(1, 0));
                break;
            case CHICKEN_PLAYER:
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, -1));
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                moves.add(new Pair<Integer, Integer>(-1, 0));
                break;
            case CHICKEN_ENEMY:
                moves.add(new Pair<Integer, Integer>(-1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(-1, -1));
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                moves.add(new Pair<Integer, Integer>(1, 0));
                break;
        }
        return moves;
    }

    public boolean isPlayer() {
        if (type == EnumPiece.LION_PLAYER || type == EnumPiece.ELEPHANT_PLAYER || type == EnumPiece.GIRAFFE_PLAYER
                || type == EnumPiece.CHICK_PLAYER || type == EnumPiece.CHICKEN_PLAYER) {
            return true;
        }
        return false;
    }

    public boolean isEnemy() {
        if (type == EnumPiece.LION_ENEMY || type == EnumPiece.ELEPHANT_ENEMY || type == EnumPiece.GIRAFFE_ENEMY
                || type == EnumPiece.CHICK_ENEMY || type == EnumPiece.CHICKEN_ENEMY) {
            return true;
        }
        return false;
    }

    public void drawBoard(Graphics g, int h, int w) {
        int x = (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2 + w * BOARD_CELL_SIZE;
        int y = (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2 + h * BOARD_CELL_SIZE;
        if (isEnemy()) {
            g.setColor(Color.red);
        } else if (isPlayer()) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.black);
        }
        g.drawString("" + type, x + 30, y + 30);
    }

    public void drawHand(Graphics g, int i, int count) {
        int x, y;
        if (!isPlayer()) {
            g.setColor(Color.red);
            x = (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2 - BOARD_MARGIN - HAND_CELL_SIZE;
            y = (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2 + i * (HAND_CELL_SIZE + HAND_MARGIN);
        } else {
            g.setColor(Color.blue);
            x = (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2 + 3 * BOARD_CELL_SIZE + BOARD_MARGIN;
            y = (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2 + 4 * BOARD_CELL_SIZE - HAND_CELL_SIZE - i * (HAND_CELL_SIZE + HAND_MARGIN);
        }
        g.drawString("" + type, x + 30, y + 30);
        g.drawRect(x, y, HAND_CELL_SIZE, HAND_CELL_SIZE);
        // TOOD: 個数を表示するように
    }
}
