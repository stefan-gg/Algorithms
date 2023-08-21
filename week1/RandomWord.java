import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class RandomWord {
    public static void main(String[] args) {
        int i = 1;
        String finalChampion = "";

        while (!StdIn.isEmpty()) {

            String currentWord = StdIn.readString();

            if(StdRandom.bernoulli((double) 1 / i)){
                finalChampion = currentWord;
            }
            i++;
        }

        System.out.println(finalChampion);
    }
}