import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static Matcher getMatchedMatcher(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) throw new RuntimeException("Unexpected input: " + input);
        return matcher;
    }
}
