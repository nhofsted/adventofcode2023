import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day5A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day5A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 35;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        Long[] seeds = Almanac.parseSeeds(in);
        Almanac almanac = Almanac.parseMaps(in);
        int locationPos = almanac.getPosition("location");

        return seedStream(seeds)
                .map(almanac::mapSeed)
                .reduce((minLocation, seedMap) ->
                        seedMap[locationPos] < minLocation[locationPos] ? seedMap : minLocation)
                .map(seedMap -> seedMap[locationPos])
                .orElseThrow();
    }

    protected Stream<Long> seedStream(Long[] seeds) {
        return Arrays.stream(seeds);
    }
}