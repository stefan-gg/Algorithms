import edu.princeton.cs.algs4.StdIn;

/*
    Write a client program Permutation.java that takes an integer k as a command-line argument;
    reads a sequence of strings from standard input using StdIn.readString(); and prints exactly
    k of them, uniformly at random. Print each item from the sequence at most once.
*/

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue randomizedQueue = new RandomizedQueue();
        int numOfNums = Integer.parseInt(args[0]);
        int i = 0;

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (Object s :
                randomizedQueue) {
            if (i < numOfNums) {
                System.out.println((String) s);
                i++;
            } else {
                break;
            }
        }
    }
}