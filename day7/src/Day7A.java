import java.io.BufferedReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day7A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day7A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 6440;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        Hand[] hands = in.lines()
                .map(hand -> hand.split(" "))
                .map(this::createHand)
                .toArray(Hand[]::new);

        Arrays.sort(hands);

        return IntStream.range(0, hands.length)
                .map(i -> (i + 1) * hands[i].getBid())
                .sum();
    }

    protected Hand createHand(String[] strings) {
        return new Hand(strings[0], Integer.parseInt(strings[1]));
    }
}