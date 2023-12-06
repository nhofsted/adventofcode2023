import java.io.BufferedReader;
import java.util.Arrays;

public class Day6B extends Day6A {

    public static void main(String[] args) throws Exception {
        new Day6B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 71503;
    }

    protected Long[][] parseRaces(BufferedReader in) {
        return in.lines()
                .map(line -> Arrays.stream(line
                                .replaceAll("\\s", "")
                                .split(":"))
                        .skip(1)
                        .map(Long::parseLong)
                        .toArray(Long[]::new))
                .toArray(Long[][]::new);
    }
}