import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Day15A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day15A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 1320;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        return Arrays.stream(in.readLine().split(","))
                .map(Hash::digest)
                .reduce(Integer::sum)
                .orElse(0);
    }
}