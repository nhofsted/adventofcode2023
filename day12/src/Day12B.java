import java.util.Arrays;

public class Day12B extends Day12A {

    public static void main(String[] args) throws Exception {
        new Day12B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 525152;
    }

    protected Record unfold(Record record) {
        char[] condition = record.condition();
        char[] newCondition = new char[condition.length * 5 + 4];
        Arrays.fill(newCondition, '?');
        for (int i = 0; i < 5; i++){
            System.arraycopy(condition, 0, newCondition, i*(condition.length+1), condition.length);
        }
        int[] ranges = record.ranges();
        int[] newRanges = new int[ranges.length * 5];
        for (int i = 0; i < 5; i++){
            System.arraycopy(ranges, 0, newRanges, i*ranges.length, ranges.length);
        }
        return new Record(newCondition, newRanges);
    }
}