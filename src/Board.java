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

            if (select.isChoicePos()) {
                Pair<Integer, Integer> pos = select.getChoicePos();
                for (Pair<Integer, Integer> move : board[pos.getFirst()][pos.getSecond()].moveCell()) {
                    int nextH = pos.getFirst() + move.getFirst();
                    int nextW = pos.getSecond() + move.getSecond();
                    if (nextH == h && nextW == w && !board[nextH][nextW].isPlayer()) {
                        movePiece(pos.getFirst(), pos.getSecond(), nextH, nextW);
                        select.reset();
                        enemyTurn();
                        return;
                    }
                }
            }

            if (board[h][w].isPlayer()) {
                select.setChoicePos(new Pair<Integer, Integer>(h, w));
                return;
            }
        }

        // TODO: 手駒のクリック

        select.reset();
    }

    void movePiece(int h, int w, int nextH, int nextW) {
        Piece get = board[nextH][nextW];
        if (board[h][w].isPlayer() && board[nextH][nextW].isEnemy()) {
            if (get.type == EnumPiece.LION_ENEMY) {
                System.out.println("You win!");
                System.exit(0);
            }
            playerHand.add(get.pickup());
        } else if (board[h][w].isEnemy() && board[nextH][nextW].isPlayer()) {
            if (get.type == EnumPiece.LION_PLAYER) {
                System.out.println("You lose...");
                System.exit(0);
            }
            enemyHand.add(get.pickup());
        }
        board[nextH][nextW] = board[h][w];
        if (board[nextH][nextW].type == EnumPiece.CHICK_PLAYER && nextH == 0) {
            board[nextH][nextW].type = EnumPiece.CHICKEN_PLAYER;
        } else if (board[nextH][nextW].type == EnumPiece.CHICK_ENEMY && nextH == 3) {
            board[nextH][nextW].type = EnumPiece.CHICKEN_ENEMY;
        }
        board[h][w] = new Piece(EnumPiece.EMPTY);
    }

    void enemyTurn() {
        ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> moveList = new ArrayList<>();
        for (int h = 0; h < 4; h++) for (int w = 0; w < 3; w++) {
            if (!board[h][w].isEnemy()) continue;
            for (Pair<Integer, Integer> move : board[h][w].moveCell()) {
                int nextH = h + move.getFirst();
                int nextW = w + move.getSecond();
                if (nextH >= 0 && nextH < 4 && nextW >= 0 && nextW < 3 && !board[nextH][nextW].isEnemy()) {
                    moveList.add(new Pair<>(new Pair<>(h, w), new Pair<>(nextH, nextW)));
                }
            }
        }
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move = moveList.get((int)(Math.random() * moveList.size()));
        Pair<Integer, Integer> now = move.getFirst();
        Pair<Integer, Integer> next = move.getSecond();
        movePiece(now.getFirst(), now.getSecond(), next.getFirst(), next.getSecond());
    }

}
