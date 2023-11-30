import java.util.ArrayList;

// description https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php

public class Board {

    private int size;
    private int[][] board;

    private int spaceCol;
    private int spaceRow;

//    MinPQ minPQ = new MinPQ<>();

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        this.size = tiles.length;
        this.board = new int[size][size];
//        this.board = tiles.clone();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = tiles[i][j];
                if (this.board[i][j] == 0) {
                    this.spaceRow = i;
                    this.spaceCol = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        if (this.board == null) return "";

        StringBuilder stringRepresentation = new StringBuilder();

        stringRepresentation.append(this.size + "\n");

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                stringRepresentation.append(this.board[i][j] + " ");
            }
            stringRepresentation.append("\n");
        }

        return stringRepresentation.toString();
    }

    // board dimension n
    public int dimension() {
        return this.size;
    }

    // number of tiles out of place
    public int hamming() {

        if (this.board == null) return 0;

        int counter = 0;
        int correctNumber = 1;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] != correctNumber && this.board[i][j] != 0) {
                    counter++;
                }
                correctNumber++;
            }
        }

        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int correctNumber = 1;
        int size = this.size;

        int[][] cloneBoard = this.board.clone();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (cloneBoard[i][j] != correctNumber && cloneBoard[i][j] != 0) {
                    int correctI = (cloneBoard[i][j] - 1) / size;
                    int correctJ = cloneBoard[i][j] - (correctI * size) - 1;
                    manhattan += Math.abs(i - correctI) + Math.abs(j - correctJ);
                }
                correctNumber++;
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;

        Board yBoard = (Board) y;

        if (yBoard.size != this.size) return false;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] != yBoard.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighboursStack = new ArrayList<>();

        if (this.spaceRow - 1 >= 0) {

            neighboursStack.add(new Board(
                    swap(this.spaceCol, this.spaceRow, this.spaceCol, this.spaceRow - 1)
            ));
        }

        if (this.spaceRow + 1 < this.size) {
            neighboursStack.add(new Board(
                    swap(this.spaceCol, this.spaceRow, this.spaceCol, this.spaceRow + 1)
            ));
        }

        if (this.spaceCol - 1 >= 0) {
            neighboursStack.add(new Board(
                    swap(this.spaceCol, this.spaceRow, this.spaceCol - 1, this.spaceRow)
            ));
        }

        if (this.spaceCol + 1 < this.size) {
            neighboursStack.add(new Board(
                    swap(this.spaceCol, this.spaceRow, this.spaceCol + 1, this.spaceRow)
            ));
        }

        return neighboursStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    // very bad definition in the assignment !!!
    // I had to lookup real purpose of this method but people have just been switching first 2
    // numbers they can swap, and it works for tests...
    public Board twin() {
        // I added this extra condition to check if board is unsolvable in Solver.java
        if (hamming() == 2) {
            int correctNumber = 1;

            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size - 1; j++) {
                    if (this.board[i][j] != correctNumber && this.board[i][j + 1] != correctNumber + 1
                            && this.board[i][j] != 0 && this.board[i][j + 1] != 0) {
                        return new Board(swap(j, i, j + 1, i));
                    }
                    correctNumber++;
                }
            }
        }

        if (spaceRow != 0) {
            return new Board(swap(0, 0, 1, 0));
        }

        return new Board(swap(0, 1, 1, 1));
    }

    private int[][] swap(int col, int row, int secondCol, int secondRow) {
        int size = this.size;
        int[][] arrCopy = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arrCopy[i][j] = this.board[i][j];
            }
        }

        int tempVal = arrCopy[row][col];

        arrCopy[row][col] = arrCopy[secondRow][secondCol];
        arrCopy[secondRow][secondCol] = tempVal;

        return arrCopy;
    }

    // unit testing (not graded)
//    public static void main(String[] args) {
//
////        int[][] niz = {{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};
//        int[][] niz = {{3, 2}, {0, 1}};
//        Board board1 = new Board(niz);
//        Board board2 = new Board(niz);
//
////        board1.neighbors();
//
//        System.out.println(board1.twin().toString());
//
////        for (Board aaa :
////                board1.neighbors()) {
////            System.out.println(aaa.toString());
////        }
//
////        board1.neighbors().forEach(System.out::println);
//
////        System.out.println("Mahetan " + board1.manhattan());
//
////        System.out.println(board1.toString());
//    }

}
