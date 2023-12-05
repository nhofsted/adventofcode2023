import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day5B extends Day5A {

    public static void main(String[] args) throws Exception {
        new Day5B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 46;
    }

    @Override
    protected Stream<Long> seedStream(Long[] seeds) {
        return IntStream.range(0, seeds.length)
                .boxed()
                .filter(i -> i % 2 == 0)
                .flatMap(i -> LongStream.range(seeds[i], seeds[i] + seeds[i + 1] + 1)
                        .boxed());
    }
}