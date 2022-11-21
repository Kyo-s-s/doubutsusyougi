package data_structure;

import static constants.Constants.*;

public class Pos extends Pair<Integer, Integer> {
    public Pos(int first, int second) {
        super(first, second);
    }
    
    public boolean checkInBoard() {
        return first >= 0 && first < BOARD_CELL_HEIGHT && second >= 0 && second < BOARD_CELL_WIDTH;
    }
}
