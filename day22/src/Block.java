import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Block(int x1, int y1, int z1, int x2, int y2, int z2) implements Comparable<Block> {
    private static final Pattern blockPattern = Pattern.compile("([0-9]+),([0-9]+),([0-9]+)~([0-9]+),([0-9]+),([0-9]+)");

    public static Block parse(String s) {
        Matcher matchedBlock = Parser.getMatchedMatcher(blockPattern, s);
        return new Block(Integer.parseInt(matchedBlock.group(1)),
                Integer.parseInt(matchedBlock.group(2)),
                Integer.parseInt(matchedBlock.group(3)),
                Integer.parseInt(matchedBlock.group(4)),
                Integer.parseInt(matchedBlock.group(5)),
                Integer.parseInt(matchedBlock.group(6)));
    }

    @Override
    public int compareTo(Block o) {
        if (o == this) return 0;
        return Math.min(z1, z2) - Math.min(o.z1, o.z2);
    }

    public Stream<Point> coverageXY() {
        return IntStream.range(x1, x2 + 1)
                .mapToObj(x -> IntStream.range(y1, y2 + 1)
                        .mapToObj(y -> new Point(x, y)))
                .flatMap(p -> p);
    }

    public int height() {
        return Math.abs(z1 - z2) + 1;
    }

    public int areaXY() {
        return (Math.abs(x1 - x2) + 1) * (Math.abs(y1 - y2) + 1);
    }
}