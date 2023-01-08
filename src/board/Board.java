package board;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import main.GamePanel;
import main.GameState;
import constants.PieceEnum;
import data_structure.*;

import static constants.Constants.*;
import static constants.PieceData.*;

public class Board implements Cloneable {
    static Board currentBoard = new Board();
    static ChoicePiece select = new ChoicePiece();

    static final int left = (SCREEN_WIDTH - BOARD_CELL_WIDTH * BOARD_CELL_SIZE) / 2;
    static final int right = left + BOARD_CELL_WIDTH * BOARD_CELL_SIZE;
    static final int top = (SCREEN_HEIGHT - BOARD_CELL_HEIGHT * BOARD_CELL_SIZE) / 2;
    static final int bottom = top + BOARD_CELL_HEIGHT * BOARD_CELL_SIZE;
    static final int handLeft = right + BOARD_MARGIN;
    static final int handRight = handLeft + HAND_CELL_SIZE;

    static Image playImageEasy = new ImageIcon("./src/images/backeasy.png").getImage();
    static Image playImageNormal = new ImageIcon("./src/images/backnormal.png").getImage();
    static Image playImageHard = new ImageIcon("./src/images/backhard.png").getImage();

    PieceEnum[][] board = new PieceEnum[BOARD_CELL_HEIGHT][BOARD_CELL_WIDTH];
    ArrayList<Pair<PieceEnum, Integer>> playerHand = new ArrayList<>();
    ArrayList<Pair<PieceEnum, Integer>> enemyHand = new ArrayList<>();

    public Board() {
        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                board[h][w] = BOARD[h][w];
            }
        }
    }

    public static void init() {
        currentBoard = new Board();
        select = new ChoicePiece();
    }

    public static void draw(Graphics g, GamePanel observer) {
        switch (GamePanel.gameMode) {
            case EASY:
                g.drawImage(playImageEasy, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, observer);
                break;
            case NORMAL:
                g.drawImage(playImageNormal, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, observer);
                break;
            case HARD:
                g.drawImage(playImageHard, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, observer);
                break;
        }
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
                drawCell(g, x, y, currentBoard.board[h][w], state, observer);
            }
        }

        for (int i = 0; i < currentBoard.enemyHand.size(); i++) {
            PieceEnum handPiece = currentBoard.enemyHand.get(i).getFirst();
            int handCount = currentBoard.enemyHand.get(i).getSecond();
            int x = left - HAND_CELL_SIZE - BOARD_MARGIN;
            int y = top + i * (HAND_CELL_SIZE + HAND_MARGIN);
            drawHand(g, x, y, handPiece, handCount, false, observer);
        }

        for (int i = 0; i < currentBoard.playerHand.size(); i++) {
            PieceEnum handPiece = currentBoard.playerHand.get(i).getFirst();
            int handCount = currentBoard.playerHand.get(i).getSecond();
            int x = right + BOARD_MARGIN;
            int y = bottom - HAND_CELL_SIZE - i * (HAND_CELL_SIZE + HAND_MARGIN);
            drawHand(g, x, y, handPiece, handCount, select.getChoiceHand() == i, observer);
        }
    }

    public static void click(int x, int y, Graphics g, GamePanel observer) {
        if (left <= x && x <= right && top <= y && y <= bottom) {
            int h = (y - top) / BOARD_CELL_SIZE;
            int w = (x - left) / BOARD_CELL_SIZE;
            clickBoard(h, w, g, observer);
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

    public static void clickBoard(int h, int w, Graphics g, GamePanel observer) {
        if (select.isChoicePos()) {
            Pos pos = select.getChoicePos();
            for (Pos move : getMoves(currentBoard.board[pos.getFirst()][pos.getSecond()])) {
                int nextH = pos.getFirst() + move.getFirst();
                int nextW = pos.getSecond() + move.getSecond();
                if (h == nextH && w == nextW
                        && currentBoard.canMovePiece(pos.getFirst(), pos.getSecond(), nextH, nextW)) {
                    currentBoard.movePiece(pos.getFirst(), pos.getSecond(), nextH, nextW);
                    select.reset();
                    if (currentBoard.isWin(true)) {
                        GamePanel.gameState = GameState.RESULT_WIN;
                        return;
                    }
                    currentBoard.enemyTurn(g, observer);
                    if (currentBoard.isWin(false)) {
                        GamePanel.gameState = GameState.RESULT_LOSE;
                    }
                    return;
                }
            }
        }

        if (select.isChoiceHand()) {
            PieceEnum handPiece = currentBoard.playerHand.get(select.getChoiceHand()).getFirst();
            if (currentBoard.putCheck(handPiece, h, w)) {
                currentBoard.putPiece(handPiece, h, w);
                select.reset();
                currentBoard.enemyTurn(g, observer);
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
            handAdd(playerHand, pickup(get));
        } else if (isEnemy(board[h][w]) && isPlayer(get)) {
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

    void enemyTurn(Graphics g, GamePanel observer) {
        draw(g, observer);

        Solver solver = new Solver(this);
        Optional<Board> nextBoard = solver.solve();
        nextBoard.ifPresentOrElse(next -> {
            currentBoard = next;
        }, () -> {
            ArrayList<Board> neighborhood = this.getNeighborhood(false);
            Board next = neighborhood.get(new Random().nextInt(neighborhood.size()));
            currentBoard = next;
        });
    }

    ArrayList<Board> getNeighborhood(boolean isPlayerTurn) {
        ArrayList<Board> neighborhood = new ArrayList<>();

        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                if ((isPlayerTurn && !isPlayer(board[h][w])) || (!isPlayerTurn && !isEnemy(board[h][w]))) {
                    continue;
                }
                for (Pos pos : getMoves(board[h][w])) {
                    int nextH = h + pos.getFirst();
                    int nextW = w + pos.getSecond();
                    if (!new Pos(nextH, nextW).checkInBoard()) {
                        continue;
                    }
                    if (canMovePiece(h, w, nextH, nextW)) {
                        Board clone = this.clone();
                        clone.movePiece(h, w, nextH, nextW);
                        neighborhood.add(clone);
                    }
                }
            }
        }

        for (int i = 0; i < (isPlayerTurn ? playerHand : enemyHand).size(); i++) {
            PieceEnum type = (isPlayerTurn ? playerHand : enemyHand).get(i).getFirst();
            for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
                for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                    if (putCheck(type, h, w)) {
                        Board clone = this.clone();
                        clone.putPiece(type, h, w);
                        neighborhood.add(clone);
                    }
                }
            }
        }

        return neighborhood;
    }

    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.board = new PieceEnum[BOARD_CELL_HEIGHT][BOARD_CELL_WIDTH];
            for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
                for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                    clone.board[h][w] = board[h][w];
                }
            }
            clone.playerHand = new ArrayList<>();
            for (Pair<PieceEnum, Integer> pair : playerHand) {
                clone.playerHand.add(new Pair<PieceEnum, Integer>(pair.getFirst(), pair.getSecond()));
            }
            clone.enemyHand = new ArrayList<>();
            for (Pair<PieceEnum, Integer> pair : enemyHand) {
                clone.enemyHand.add(new Pair<PieceEnum, Integer>(pair.getFirst(), pair.getSecond()));
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int score() {
        if (isWin(true)) {
            return -1000000;
        }
        int score = 0;
        score -= playerHand.size() * 200;
        score += enemyHand.size() * 200;
        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                if (isPlayer(board[h][w])) {
                    for (Pos move : getMoves(board[h][w])) {
                        int nextH = h + move.getFirst();
                        int nextW = w + move.getSecond();
                        if (!new Pos(nextH, nextW).checkInBoard()) {
                            continue;
                        }
                        if (isEnemy(board[nextH][nextW]) && isKing(board[nextH][nextW])) {
                            score -= 10000;
                        }
                        if (isKing(board[h][w])
                                && (h == 0 || w == 0 || h == BOARD_CELL_HEIGHT - 1 || w == BOARD_CELL_WIDTH - 1)) {
                            score += 500;
                        }
                    }
                } else if (isEnemy(board[h][w])) {
                    for (Pos move : getMoves(board[h][w])) {
                        int nextH = h + move.getFirst();
                        int nextW = w + move.getSecond();
                        if (!new Pos(nextH, nextW).checkInBoard()) {
                            continue;
                        }
                        if (isPlayer(board[nextH][nextW]) && isKing(board[nextH][nextW])) {
                            score += 1000;
                        }
                        if (isKing(board[h][w])
                                && (h == 0 || w == 0 || h == BOARD_CELL_HEIGHT - 1 || w == BOARD_CELL_WIDTH - 1)) {
                            score -= 500;
                        }
                    }
                }
            }
        }
        return score;
    }

    boolean isWin(boolean isPlayer) {
        boolean fin = true;
        for (int h = 0; h < BOARD_CELL_HEIGHT; h++) {
            for (int w = 0; w < BOARD_CELL_WIDTH; w++) {
                if ((isPlayer && isEnemy(board[h][w]) && isKing(board[h][w]))
                        || (!isPlayer && isPlayer(board[h][w]) && isKing(board[h][w]))) {
                    return false;
                }
            }
        }
        return fin;
    }

}
