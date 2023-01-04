package board;

import java.util.ArrayList;
import java.util.Optional;
import java.util.PriorityQueue;

import data_structure.Pair;

public class Solver {

    static final int SEARCH_DEPTH = 5;
    static final double TIME_LIMIT = 0.5;

    Board current;

    class State {
        Board next;
        Board predict;
        int score;

        State(Board next, Board predict, int score) {
            this.next = next;
            this.predict = predict;
            this.score = score;
        }
    }

    public Solver(Board board) {
        this.current = board;
    }

    public Optional<Board> solve() {
        ArrayList<Board> neighborhood = current.getNeighborhood(false);
        if (neighborhood.isEmpty()) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        PriorityQueue<State>[] queues = new PriorityQueue[SEARCH_DEPTH];
        for (int i = 0; i < SEARCH_DEPTH; i++) {
            queues[i] = new PriorityQueue<>((a, b) -> b.score - a.score);
        }
        for (Board next : neighborhood) {
            queues[0].add(new State(next.clone(), next.clone(), next.score()));
        }

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < TIME_LIMIT * 1e9) {
            for (int i = 0; i < SEARCH_DEPTH - 1; i++) {
                if (queues[i].isEmpty()) {
                    continue;
                }
                State state = queues[i].poll();
                Board predict = null;
                int score = 0;
                for (Board nextPlayerTurn : state.predict.getNeighborhood(true)) {
                    if (predict == null || nextPlayerTurn.score() < score) {
                        predict = nextPlayerTurn;
                        score = nextPlayerTurn.score();
                    }
                }
                if (predict == null) {
                    continue;
                }

                for (Board nextEnemyBoard : predict.getNeighborhood(false)) {
                    queues[i + 1].add(new State(state.next, nextEnemyBoard, score));
                }
            }
        }
        if (queues[SEARCH_DEPTH - 1].isEmpty()) {
            return Optional.empty();
        }
        System.out.println(queues[SEARCH_DEPTH - 1].peek().score);
        return Optional.of(queues[SEARCH_DEPTH - 1].poll().next);
    }

}
