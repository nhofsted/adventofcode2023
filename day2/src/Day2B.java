import java.io.BufferedReader;
import java.util.Arrays;

public class Day2B extends Day2A {
    public static void main(String[] args) throws Exception {
        new Day2B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 2286;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        return in.lines()
                .map(Game::parse)
                .map(Game::minimumBalls)
                .map(minimumBalls -> Arrays.stream(minimumBalls)
                        .reduce(Math::multiplyExact)
                        .orElse(0))
                .reduce(Integer::sum)
                .orElse(0);
    }
}