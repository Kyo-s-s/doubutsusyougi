import java.awt.*;

import java.util.ArrayList;

import org.w3c.dom.events.MouseEvent;

import static constants.Constants.*;
public class Board {
    Piece[][] board = new Piece[4][3];
    ArrayList<Piece> playerHand = new ArrayList<Piece>();
    ArrayList<Piece> enemyHand = new ArrayList<Piece>();
    // select piece
    Pair<Integer, Integer> select = new Pair<Integer, Integer>(-1, -1);


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
        // TODO: HEIGHT, WIDTHのズレを直す
        
        for (int h = 0; h < 4; h++) {
            for (int w = 0; w < 3; w++) {
                drawRect(g, h, w, Color.black);
                board[h][w].draw(g, h, w);
            }
        }

        if (select.getFirst() != -1 && select.getSecond() != -1) {
            drawRect(g, select.getFirst(), select.getSecond(), Color.green);
        }

    }

    void drawRect(Graphics g, int h, int w, Color color) {
        int x = (SCREEN_WIDTH - 3 * CELL_SIZE) / 2 + w * CELL_SIZE;
        int y = (SCREEN_HEIGHT - 4 * CELL_SIZE) / 2 + h * CELL_SIZE;
        g.setColor(color);
        g.drawRect(x, y, CELL_SIZE - 1, CELL_SIZE - 1);
    }

    public void click(int x, int y) {
        System.out.println("x: " + x + " y: " + y);
        // 4 * 3 マスの中のどれかをクリックしたとき
        if (x >= (SCREEN_WIDTH - 3 * CELL_SIZE) / 2 && x <= (SCREEN_WIDTH + 3 * CELL_SIZE) / 2
                && y >= (SCREEN_HEIGHT - 4 * CELL_SIZE) / 2 && y <= (SCREEN_HEIGHT + 4 * CELL_SIZE) / 2) {
            int h = (y - (SCREEN_HEIGHT - 4 * CELL_SIZE) / 2) / CELL_SIZE;
            int w = (x - (SCREEN_WIDTH - 3 * CELL_SIZE) / 2) / CELL_SIZE;
            // System.out.println("h: " + h + " w: " + w);
            select = new Pair<Integer, Integer>(h, w);
        }

        // TODO: 手駒のクリック
    }
}
