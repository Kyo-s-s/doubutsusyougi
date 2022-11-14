import java.awt.*;

import java.util.ArrayList;

import static constants.Constants.*;
public class Board {
    Piece[][] board = new Piece[4][3];
    ArrayList<Piece> playerHand = new ArrayList<Piece>();
    ArrayList<Piece> enemyHand = new ArrayList<Piece>();
    // select piece


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
        g.setColor(Color.black);
        for (int i = 0; i < 4; i++) {
            int y = (SCREEN_HEIGHT - 4 * CELL_SIZE) / 2 + i * CELL_SIZE;
            for (int j = 0; j < 3; j++) {
                int x = (SCREEN_WIDTH - 3 * CELL_SIZE) / 2 + j * CELL_SIZE;
                g.setColor(Color.black);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                board[i][j].draw(g, i, j);
                
            }
        }
    }
}
