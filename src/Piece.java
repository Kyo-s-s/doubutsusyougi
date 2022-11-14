import java.util.ArrayList;

public class Piece {

    EnumPiece type;
    boolean isMine;

    public Piece(EnumPiece type, boolean isMine) {
        this.type = type;
        this.isMine = isMine;
    }

    static ArrayList<Pair<Integer, Integer>> moveCell(EnumPiece piece) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
        switch (piece) {
            case EMPTY:
                break;
            case LION:
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 0));
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, -1));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(-1, -1));
                break;
            case ELEPHANT:
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, -1));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(-1, -1));
                break;
            case GIRAFFE:
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(0, -1));
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 0));
                break;
            case CHICK:
                moves.add(new Pair<Integer, Integer>(0, 1));
                break;
            case CHICKEN:
                moves.add(new Pair<Integer, Integer>(-1, 0));
                moves.add(new Pair<Integer, Integer>(-1, 1));
                moves.add(new Pair<Integer, Integer>(0, 1));
                moves.add(new Pair<Integer, Integer>(1, 1));
                moves.add(new Pair<Integer, Integer>(1, 0));
                moves.add(new Pair<Integer, Integer>(0, -1));
                break;
        }
        return moves;
    }
}
