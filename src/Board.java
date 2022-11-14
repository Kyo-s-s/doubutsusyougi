import java.awt.*;

import java.util.ArrayList;

public class Board {
    Piece[][] board = new Piece[4][3];
    ArrayList<Piece> playerHand = new ArrayList<Piece>();
    ArrayList<Piece> enemyHand = new ArrayList<Piece>();


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
        // TODO: draw board
    }
}
