import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Part(int x, int m, int a, int s) {

    private final static Pattern partPattern = Pattern.compile("\\{x=([0-9]+),m=([0-9]+),a=([0-9]+),s=([0-9]+)}");

    public static Part parse(String part) {
        Matcher matchedPart = Parser.getMatchedMatcher(partPattern, part);
        return new Part(Integer.parseInt(matchedPart.group(1)),
                Integer.parseInt(matchedPart.group(2)),
                Integer.parseInt(matchedPart.group(3)),
                Integer.parseInt(matchedPart.group(4)));
    }

    public long rating() {
        return x + m + a + s;
    }

    public int getCategory(char category) {
        return switch (category) {
            case 'x' -> x;
            case 'm' -> m;
            case 'a' -> a;
            case 's' -> s;
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }
}