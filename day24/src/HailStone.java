import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record HailStone(double x, double y, double z, double vx, double vy, double vz) {
    private static final Pattern hailStonePattern = Pattern.compile("(-?\\d+), +(-?\\d+), +(-?\\d+) +@ +(-?\\d+), +(-?\\d+), +(-?\\d+)");

    public static HailStone parse(String input) {
        Matcher m = Parser.getMatchedMatcher(hailStonePattern, input);
        return new HailStone(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)),
                Long.parseLong(m.group(4)), Long.parseLong(m.group(5)), Long.parseLong(m.group(6)));
    }
}