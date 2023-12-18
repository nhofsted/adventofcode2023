import java.util.regex.Matcher;

public class Day18B extends Day18A {

    public static void main(String[] args) throws Exception {
        new Day18B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 952408144115L;
    }

    @Override
    protected char getDirection(Matcher m) {
        return switch (m.group(4).charAt(0)) {
            case '0' -> 'R';
            case '1' -> 'D';
            case '2' -> 'L';
            case '3' -> 'U';
            default -> throw new RuntimeException("Unexpected direction: " + m.group(4));
        };
    }

    @Override
    protected int getDistance(Matcher m) {
        return Integer.parseInt(m.group(3), 16);
    }
}