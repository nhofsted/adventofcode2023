import java.io.BufferedReader;

public class Day2A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day2A().run();
    }

    @Override
    protected int getSampleSolution() {
        return 8;
    }

    @Override
    protected int getSolution(BufferedReader in) {
        return in.lines()
                .map(Game::parse)
                .filter(game -> game.isPossible(new int[]{12, 13, 14}))
                .map(Game::id)
                .reduce(Integer::sum)
                .orElse(0);
    }
}