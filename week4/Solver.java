import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    private boolean isSolvable = false;
    private int moves = 0;

    private SearchNode solutionNode;

    private class SearchNode implements Comparable<SearchNode> {
        private int numOfMoves = 0;
        private SearchNode previousSearchNode;
        private Board currentBoard;

        public SearchNode(int move, SearchNode previousSearchNode, Board currentBoard) {
            this.numOfMoves = move;
            this.previousSearchNode = previousSearchNode;
            this.currentBoard = currentBoard;
        }

        public int getPriority() {
            return numOfMoves + this.currentBoard.manhattan();
        }

        public int getMove() {
            return numOfMoves;
        }

        /**
         * @param o the object to be compared.
         * @return
         */
        @Override
        public int compareTo(SearchNode o) {
            return this.getPriority() - o.getPriority();
        }
    }

    private Board board;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.board = initial;

        MinPQ<SearchNode> searchNodeMinPQ = new MinPQ<>();

        while (true) {

            if (searchNodeMinPQ.isEmpty()) {

                searchNodeMinPQ.insert(new SearchNode(0, null, this.board));

            } else {

                SearchNode searchNode = searchNodeMinPQ.delMin();

                int moves = searchNode.numOfMoves;
                Board currentBoard = searchNode.currentBoard;
                Board previousBoard = this.board;

                if (moves != 0) {
                    previousBoard = searchNode.previousSearchNode.currentBoard;
                }

                if (currentBoard.hamming() == 2 && currentBoard.twin().isGoal()) {
                    this.isSolvable = false;

                    break;
                } else if (currentBoard.isGoal()) {
                    this.isSolvable = true;
                    this.moves = moves;
                    solutionNode = searchNode;

                    break;
                }

                for (Board neighBoard : currentBoard.neighbors()) {
                    if (moves == 0) {
                        searchNodeMinPQ.insert(new SearchNode(moves + 1, searchNode, neighBoard));
                    } else if (!neighBoard.equals(previousBoard)) {
                        searchNodeMinPQ.insert(new SearchNode(moves + 1, searchNode, neighBoard));
                    }
                }

            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable ? this.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;

        ArrayList<Board> solution = new ArrayList<>();

        SearchNode solutionNode = this.solutionNode;

        while (solutionNode != null) {
            solution.add(solutionNode.currentBoard);
            solutionNode = solutionNode.previousSearchNode;
        }

        Collections.reverse(solution);

        return solution;
    }

    // test client (see below)
//    public static void main(String[] args) {
//
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
//        Board initial = new Board(tiles);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
//    }

}