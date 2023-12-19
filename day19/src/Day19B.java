import java.io.BufferedReader;
import java.io.IOException;

public class Day19B extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day19B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 167409079868000L;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        SortSystem system = SortSystem.parse(in);
        Range maxRange = new Range(1, 4001);
        return system.acceptedCombinations(new PartRange(maxRange, maxRange, maxRange, maxRange));
    }
}