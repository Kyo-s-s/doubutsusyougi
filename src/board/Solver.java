package board;

import java.util.ArrayList;
import java.util.Optional;

public class Solver {

    Board current;

    public Solver(Board board) {
        this.current = board;
    }

    public Optional<Board> solve() {
        ArrayList<Board> neighborhood = current.getNeighborhood(false);
        if (neighborhood.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(neighborhood.get((int) (Math.random() * neighborhood.size())));
    }

}
