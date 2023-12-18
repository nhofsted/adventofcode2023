import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18A extends Puzzle {

    private final static Pattern operation = Pattern.compile("^([RLUD]) ([0-9]+) \\(#([0-9a-f]{5})([0-9a-f])\\)");

    public static void main(String[] args) throws Exception {
        new Day18A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 62;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        long prevX = 0;
        long prevY = 0;
        long x = 0;
        long y = 0;
        long c = 0;
        long sum = 0;

        String op;
        while ((op = in.readLine()) != null) {
            Matcher m = Parser.getMatchedMatcher(operation, op);
            int d = getDistance(m);
            c += d;
            switch (getDirection(m)) {
                case 'R' -> x += d;
                case 'L' -> x -= d;
                case 'U' -> y += d;
                case 'D' -> y -= d;
                default -> throw new RuntimeException("Unexpected direction: " + getDirection(m));
            }
            sum += prevX * y;
            sum -= prevY * x;

            prevX = x;
            prevY = y;
        }

        return Math.abs(sum / 2) + (c + 2) / 2;
    }

    protected char getDirection(Matcher m) {
        return m.group(1).charAt(0);
    }

    protected int getDistance(Matcher m) {
        return Integer.parseInt(m.group(2));
    }
}