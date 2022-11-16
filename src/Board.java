import java.awt.*;

import java.util.ArrayList;

import static constants.Constants.*;
public class Board {
    Piece[][] board = new Piece[4][3];
    ArrayList<Piece> playerHand = new ArrayList<Piece>();
    ArrayList<Piece> enemyHand = new ArrayList<Piece>();
    ChoicePiece select = new ChoicePiece();

    public Board() {
        board[0][0] = new Piece(EnumPiece.GIRAFFE_ENEMY);
        board[0][1] = new Piece(EnumPiece.LION_ENEMY);
        board[0][2] = new Piece(EnumPiece.ELEPHANT_ENEMY);
        board[1][0] = new Piece(EnumPiece.EMPTY);
        board[1][1] = new Piece(EnumPiece.CHICK_ENEMY);
        board[1][2] = new Piece(EnumPiece.EMPTY);
        board[2][0] = new Piece(EnumPiece.EMPTY);
        board[2][1] = new Piece(EnumPiece.CHICK_PLAYER);
        board[2][2] = new Piece(EnumPiece.EMPTY);
        board[3][0] = new Piece(EnumPiece.ELEPHANT_PLAYER);
        board[3][1] = new Piece(EnumPiece.LION_PLAYER);
        board[3][2] = new Piece(EnumPiece.GIRAFFE_PLAYER);
    }


    public void draw(Graphics g) {
        for (int h = 0; h < 4; h++) {
            for (int w = 0; w < 3; w++) {
                drawRect(g, h, w, Color.black);
                board[h][w].drawBoard(g, h, w);
            }
        }

        if (select.isChoicePos()) {
            Pair<Integer, Integer> pos = select.getChoicePos();
            drawRect(g, pos.getFirst(), pos.getSecond(), Color.green);
            for (Pair<Integer, Integer> move : board[pos.getFirst()][pos.getSecond()].moveCell()) {
                int h = pos.getFirst() + move.getFirst();
                int w = pos.getSecond() + move.getSecond();
                if (h >= 0 && h < 4 && w >= 0 && w < 3 && !board[h][w].isPlayer()) {
                    drawRect(g, h, w, Color.yellow);
                }
            }
        }

        for (int i = 0; i < playerHand.size(); i++) {
            playerHand.get(i).drawHand(g, i, 1);
        }

        for (int i = 0; i < enemyHand.size(); i++) {
            enemyHand.get(i).drawHand(g, i, 1);
        }

    }

    void drawRect(Graphics g, int h, int w, Color color) {
        int x = (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2 + w * BOARD_CELL_SIZE;
        int y = (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2 + h * BOARD_CELL_SIZE;
        g.setColor(color);
        g.drawRect(x, y, BOARD_CELL_SIZE - 1, BOARD_CELL_SIZE - 1);
    }

    public void click(int x, int y) {
        // 4 * 3 マスの中のどれかをクリックしたとき
        if (x >= (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2 && x <= (SCREEN_WIDTH + 3 * BOARD_CELL_SIZE) / 2
                && y >= (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2 && y <= (SCREEN_HEIGHT + 4 * BOARD_CELL_SIZE) / 2) {
            int h = (y - (SCREEN_HEIGHT - 4 * BOARD_CELL_SIZE) / 2) / BOARD_CELL_SIZE;
            int w = (x - (SCREEN_WIDTH - 3 * BOARD_CELL_SIZE) / 2) / BOARD_CELL_SIZE;
            System.out.println("h: " + h + " w: " + w);
            if (board[h][w].isPlayer()) {
                select.setChoicePos(new Pair<Integer, Integer>(h, w));
                return;
            }
        }

        // TODO: 手駒のクリック

        select.reset();
    }
}
