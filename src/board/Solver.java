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
            if (next.isWin(false)) {
                return Optional.of(next);
            }
            queues[0].add(new State(next.clone(), next.clone(), next.score()));
        }

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < TIME_LIMIT * 1e9) {
            for (int i = 0; i < SEARCH_DEPTH - 1; i++) {
                if (queues[i].isEmpty()) {
                    continue;
                }
                State state = queues[i].poll();
                if (state.predict.isWin(true)) {
                    continue;
                }
                if (state.predict.isWin(false)) {
                    queues[i + 1].add(state);
                    continue;
                }
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
                System.out.println(" >> " + predict.score());

                for (Board nextEnemyBoard : predict.getNeighborhood(false)) {
                    queues[i + 1].add(new State(state.next, nextEnemyBoard, nextEnemyBoard.score()));
                    // System.out.println(" >> >> " + nextEnemyBoard.score(true));
                }
            }
        }
        if (queues[SEARCH_DEPTH - 1].isEmpty()) {
            return Optional.empty();
        }
        System.out.println(queues[SEARCH_DEPTH - 1].peek().score);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        if (queues[SEARCH_DEPTH - 1].isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(queues[SEARCH_DEPTH - 1].poll().next);
        // return Optional.of(queues[SEARCH_DEPTH - 1].peek().predict);
    }

}
