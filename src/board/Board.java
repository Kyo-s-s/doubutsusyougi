package board;
import java.awt.*;

import java.util.ArrayList;

import constants.PieceEnum;
import data_structure.*;

import static constants.Constants.*;
import static constants.PieceData.*;

public class Board {
    static Board currentBoard = new Board();
    static ChoicePiece select = new ChoicePiece();
    
    static final int left = (SCREEN_WIDTH - BOARD_CELL_WIDTH * BOARD_CELL_SIZE) / 2;
    static final int right = left + BOARD_CELL_WIDTH * BOARD_CELL_SIZE;
    static final int top = (SCREEN_HEIGHT - BOARD_CELL_HEIGHT * BOARD_CELL_SIZE) / 2;
    static final int bottom = top + BOARD_CELL_HEIGHT * BOARD_CELL_SIZE;
    static final int handLeft = right + BOARD_MARGIN;
    static final int handRight = handLeft + HAND_CELL_SIZE;    



    PieceEnum[][] board = new PieceEnum[BOARD_CELL_HEIGHT][BOARD_CELL_WIDTH];
    ArrayList<Pair<PieceEnum, Integer>> playerHand = new ArrayList<>();
    ArrayList<Pair<PieceEnum, Integer>> enemyHand = new ArrayList<>();

    public Board() {
        board = BOARD;
    }

    public static void draw(Graphics g) {
        ArrayList<Pos> movePos = new ArrayList<>();
        if (select.isChoicePos()) {
            int h = select.getChoicePos().getFirst();
            int w = select.getChoicePos().getSecond();
            for (Pos move : getMoves(currentBoard.board[h][w])) {
                int nh = h + move.getFirst();
                int nw = w + move.getSecond();
                if (new Pos(nh, nw).checkInBoard() && !isPlayer(currentBoard.board[nh][nw])) {
                    movePos.add(new Pos(nh, nw));
                }
            }
        }
        if (select.isChoiceHand()) {
            PieceEnum handPiece = currentBoard.playerHand.get(select.getChoiceHand()).getFirst();
            for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
                for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                    if (currentBoard.putCheck(handPiece, h, w)) {
                        movePos.add(new Pos(h, w));
                    }
                }
            }
        }

        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                int x = left + w * BOARD_CELL_SIZE;
                int y = top + h * BOARD_CELL_SIZE;
                PieceState state = PieceState.NORMAL;
                if (select.getChoicePos().equals(new Pair<>(h, w))) {
                    state = PieceState.SELECT;
                }
                if (movePos.contains(new Pos(h, w))) {
                    state = PieceState.CANMOVE;
                }
                drawCell(g, x, y, currentBoard.board[h][w], state);
            }
        }

        for (int i = 0; i < currentBoard.enemyHand.size(); i++) {
            PieceEnum handPiece = currentBoard.enemyHand.get(i).getFirst();
            int handCount = currentBoard.enemyHand.get(i).getSecond();
            int x = left - HAND_CELL_SIZE - BOARD_MARGIN;
            int y = top + i * (HAND_CELL_SIZE + HAND_MARGIN);
            drawHand(g, x, y, handPiece, false);
        }

        for (int i = 0; i < currentBoard.playerHand.size(); i++) {
            PieceEnum handPiece = currentBoard.playerHand.get(i).getFirst();
            int handCount = currentBoard.playerHand.get(i).getSecond();
            int x = right + BOARD_MARGIN;
            int y = bottom - HAND_CELL_SIZE - i * (HAND_CELL_SIZE + HAND_MARGIN);
            drawHand(g, x, y, handPiece, select.getChoiceHand() == i);
        }
    }

    public static void click(int x, int y) {
        if (left <= x && x <= right && top <= y && y <= bottom) {
            int h = (y - top) / BOARD_CELL_SIZE;
            int w = (x - left) / BOARD_CELL_SIZE;
            clickBoard(h, w);
            return;
        }

        if (handLeft <= x && x <= handRight) {
            for (int i = 0; i < currentBoard.playerHand.size(); i++) {
                int cellBottom = bottom - i * (HAND_CELL_SIZE + HAND_MARGIN);
                int cellTop = cellBottom - HAND_CELL_SIZE;
                if (cellTop <= y && y <= cellBottom) {
                    clickHand(i);
                    return;
                }
            }
        }

        select.reset();
    }

    public static void clickBoard(int h, int w) {
        if (select.isChoicePos()) {
            Pos pos = select.getChoicePos();
            for (Pos move : getMoves(currentBoard.board[pos.getFirst()][pos.getSecond()])) {
                int nextH = pos.getFirst() + move.getFirst();
                int nextW = pos.getSecond() + move.getSecond();
                if (h == nextH && w == nextW && currentBoard.canMovePiece(pos.getFirst(), pos.getSecond(), nextH, nextW)) {
                    currentBoard.movePiece(pos.getFirst(), pos.getSecond(), nextH, nextW);
                    select.reset();
                    currentBoard.enemyTurn();
                    return;
                }
            }
        } 

        if (select.isChoiceHand()) {
            PieceEnum handPiece = currentBoard.playerHand.get(select.getChoiceHand()).getFirst();
            if (currentBoard.putCheck(handPiece, h, w)) {
                currentBoard.putPiece(handPiece, h, w);
                select.reset();
                currentBoard.enemyTurn();
                return;
            }
        }
        if (isPlayer(currentBoard.board[h][w])) {
            select.setChoicePos(new Pos(h, w));
            return;
        }
        select.reset();
    }

    public static void clickHand(int i) {
        select.setChoiceHand(i);
    }

    boolean canMovePiece(int h, int w, int nextH, int nextW) {
        return (isPlayer(board[h][w]) && !isPlayer(board[nextH][nextW]))
            || (isEnemy(board[h][w]) && !isEnemy(board[nextH][nextW]));
    }

    void movePiece(int h, int w, int nextH, int nextW) {
        PieceEnum get = board[nextH][nextW];
        if (isPlayer(board[h][w]) && isEnemy(get)) {
            if (isKing(get)) {
                System.out.println("You win!");
                System.exit(0);
            }
            handAdd(playerHand, pickup(get));
        } else if (isEnemy(board[h][w]) && isPlayer(get)) {
            if (isKing(get)) {
                System.out.println("You lose...");
                System.exit(0);
            }
            handAdd(enemyHand, pickup(get));
        }
        board[nextH][nextW] = board[h][w];
        // TODO: 成りをユーザーに選択させる
        PieceEnum evol = evolution(board[nextH][nextW], nextH, nextW);
        if (evol != null) {
            board[nextH][nextW] = evol;
        }
        board[h][w] = PieceEnum.EMPTY;
    }

    boolean putCheck(PieceEnum type, int h, int w) {
        if (board[h][w] != PieceEnum.EMPTY) {
            return false;
        }
        // そのPieceのみの盤面を作成し、getMovesを行う
        for (Pos move : getMoves(type)) {
            int nextH = h + move.getFirst();
            int nextW = w + move.getSecond();
            if (new Pos(nextH, nextW).checkInBoard()) {
                return true;
            }
        }
        return false;
    }

    void putPiece(PieceEnum type, int h, int w) {
        board[h][w] = type;
        ArrayList<Pair<PieceEnum, Integer>> hand = isPlayer(type) ? playerHand : enemyHand;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getFirst() == type) {
                hand.get(i).setSecond(hand.get(i).getSecond() - 1);
                if (hand.get(i).getSecond() == 0) {
                    hand.remove(i);
                }
                break;
            }
        }
    }

    void handAdd(ArrayList<Pair<PieceEnum, Integer>> hand, PieceEnum type) {
        for (Pair<PieceEnum, Integer> pair : hand) {
            if (pair.getFirst() == type) {
                pair.setSecond(pair.getSecond() + 1);
                return;
            }
        }
        hand.add(new Pair<PieceEnum, Integer>(type, 1));
        // sort
    }

    void enemyTurn() {
        ArrayList<Pair<Pos, Pos>> moveList = new ArrayList<>();
        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                if (!isEnemy(board[h][w])) continue;
                for (Pos pos : getMoves(board[h][w])) {
                    int nextH = h + pos.getFirst();
                    int nextW = w + pos.getSecond();
                    if (!new Pos(nextH, nextW).checkInBoard()) {
                        continue;
                    }
                    if (canMovePiece(h, w, nextH, nextW)) {
                        moveList.add(new Pair<Pos, Pos>(new Pos(h, w), new Pos(nextH, nextW)));
                    }
                }
            }
        }
        Pair<Pos, Pos> move = moveList.get((int) (Math.random() * moveList.size()));
        Pos now = move.getFirst();
        Pos next = move.getSecond();
        movePiece(now.getFirst(), now.getSecond(), next.getFirst(), next.getSecond());
    }

}
