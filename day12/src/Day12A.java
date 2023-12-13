import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;

public class Day12A extends Puzzle {

    private final HashMap<Record, Long> cache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new Day12A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 21;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        return in.lines()
                .map(this::parse)
                .map(this::unfold)
                .map(this::countPossibilitiesCached)
                .reduce(Long::sum)
                .orElse(0L);
    }

    protected Record unfold(Record record) {
        return record;
    }

    private long countPossibilitiesCached(Record record) {
        Long result = cache.get(record);
        if(result == null) {
            result = countPossibilities(record);
            cache.put(record, result);
        }
        return result;
    }

    private long countPossibilities(Record record) {
        if (cache.containsKey(record)) return cache.get(record);

        final char[] history = record.condition();
        final int[] ranges = record.ranges();
        int runLength = 0;
        int rangeIndex = -1;
        boolean inRun = false;
        for (int i = 0; i < history.length; ++i) {
            switch (history[i]) {
                case '#':
                    if (inRun) {
                        if (runLength > ranges[rangeIndex]) return 0;
                    } else {
                        inRun = true;
                        if (++rangeIndex == ranges.length) return 0;
                    }
                    runLength++;
                    break;
                case '.':
                    if (inRun) {
                        if (runLength != ranges[rangeIndex]) return 0;
                        inRun = false;
                        runLength = 0;
                    }
                    break;
                case '?': {
                    if (inRun) {
                        if (runLength < ranges[rangeIndex]) {
                            history[i] = '#';
                            runLength++;
                        } else if (runLength > ranges[rangeIndex]) {
                            return 0;
                        } else {
                            history[i] = '.';
                            inRun = false;
                            runLength = 0;
                        }
                    } else {
                        final char[] operational = new char[history.length - i];
                        System.arraycopy(history, i, operational, 0, history.length - i);
                        operational[0] = '.';
                        final char[] damaged = new char[history.length - i];
                        System.arraycopy(history, i, damaged, 0, history.length - i);
                        damaged[0] = '#';
                        int[] remainingRanges = new int[ranges.length - rangeIndex - 1];
                        System.arraycopy(ranges, rangeIndex + 1, remainingRanges, 0, ranges.length - rangeIndex - 1);
                        return countPossibilitiesCached(new Record(operational, remainingRanges)) +
                                countPossibilitiesCached(new Record(damaged, remainingRanges));
                    }
                }
            }
        }
        if (inRun) {
            if (runLength != ranges[rangeIndex]) return 0;
        }
        if (rangeIndex == ranges.length - 1) return 1;
        return 0;
    }

    private Record parse(String line) {
        String[] parts = line.split(" ");
        int[] ranges = Arrays.stream(parts[1].split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new Record(parts[0].toCharArray(), ranges);
    }

    record Record(char[] condition, int[] ranges) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Record record = (Record) o;
            return Arrays.equals(condition, record.condition) && Arrays.equals(ranges, record.ranges);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(condition);
            result = 31 * result + Arrays.hashCode(ranges);
            return result;
        }
    }
}