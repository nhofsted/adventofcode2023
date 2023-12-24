import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day5B extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day5B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 46;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        Range[] seeds = BatchOptimizedLocationAlmanac.parseSeedRanges(in);
        BatchOptimizedLocationAlmanac almanac = BatchOptimizedLocationAlmanac.parseMaps(in);

        return Arrays.stream(seeds)
                .flatMap(seedRange -> Arrays.stream(almanac.getSeedLocations(seedRange)))
                .min(Long::compareTo)
                .orElseThrow();
    }
}