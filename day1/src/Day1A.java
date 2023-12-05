import java.io.BufferedReader;

public class Day1A extends Puzzle {
    public static void main(String[] args) throws Exception {
        new Day1A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 142;
    }

    protected String preProcess(final String line) {
        return line;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        return in.lines()
                .map(this::preProcess)
                .map(line -> line
                        .chars()
                        .filter(Character::isDigit)
                        .mapToObj(i -> new int[]{i, i})
                        .reduce((firstLast, elem) -> new int[]{firstLast[0], elem[1]})
                        .map(chars -> new String(chars, 0, chars.length))
                        .map(Integer::parseInt)
                        .orElseThrow()
                )
                .reduce(0, Integer::sum);
    }
}