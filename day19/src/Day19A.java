import java.io.BufferedReader;
import java.io.IOException;

public class Day19A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day19A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 19114;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        SortSystem system = SortSystem.parse(in);
        return in.lines()
                .map(Part::parse)
                .map(system::process)
                .reduce(Long::sum)
                .orElse(0L);
    }
}