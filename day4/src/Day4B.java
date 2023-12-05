import java.io.BufferedReader;
import java.util.Arrays;

public class Day4B extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day4B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 30;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        Integer[] matches = in.lines()
                .map(Ticket::parse)
                .map(Ticket::matches)
                .toArray(Integer[]::new);

        int[] cards = new int[matches.length];
        Arrays.fill(cards, 1);

        for (int i = 0; i < matches.length - 1; ++i) {
            for (int j = i + 1; j < Math.min(cards.length, i + 1 + matches[i]); ++j) {
                cards[j] += cards[i];
            }
        }

        return Arrays.stream(cards).sum();
    }
}