import java.awt.*;

import java.util.ArrayList;

import static constants.Constants.*;
public class Board {
    Piece[][] board = new Piece[4][3];
    ArrayList<Piece> playerHand = new ArrayList<Piece>();
    ArrayList<Piece> enemyHand = new ArrayList<Piece>();
    // select piece


    public Board() {
        board[0][0] = new Piece(EnumPiece.GIRAFFE, false);
        board[0][1] = new Piece(EnumPiece.LION, false);
        board[0][2] = new Piece(EnumPiece.ELEPHANT, false);
        board[1][0] = new Piece(EnumPiece.EMPTY, false);
        board[1][1] = new Piece(EnumPiece.CHICK, false);
        board[1][2] = new Piece(EnumPiece.EMPTY, false);
        board[2][0] = new Piece(EnumPiece.EMPTY, false);
        board[2][1] = new Piece(EnumPiece.CHICK, true);
        board[2][2] = new Piece(EnumPiece.EMPTY, false);
        board[3][0] = new Piece(EnumPiece.ELEPHANT, true);
        board[3][1] = new Piece(EnumPiece.LION, true);
        board[3][2] = new Piece(EnumPiece.GIRAFFE, true);
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
                if (board[i][j].isMine) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.blue);
                }
                // TODO: imageの用意/差し替え
                // Piece に draw(g, i, j) を作ってそこで描画するといいかも、こっちでは枠だけ
                switch (board[i][j].type) {
                    case EMPTY:
                        break;
                    case LION:
                        g.drawString("LION", x + CELL_SIZE / 3, y + CELL_SIZE / 3);
                        break;
                    case ELEPHANT:
                        g.drawString("ELEPHANT", x + CELL_SIZE / 3, y + CELL_SIZE / 3);
                        break;
                    case GIRAFFE:
                        g.drawString("GIRAFFE", x + CELL_SIZE / 3, y + CELL_SIZE / 3);
                        break;
                    case CHICK:
                        g.drawString("CHICK", x + CELL_SIZE / 3, y + CELL_SIZE / 3);
                        break;
                    case CHICKEN:
                        g.drawString("CHICKEN", x + CELL_SIZE / 3, y + CELL_SIZE / 3);
                        break;
                }
            }
        }
    }
}
