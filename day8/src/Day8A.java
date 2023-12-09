import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8A extends Puzzle {
    private static final Pattern nodePattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");

    protected String instructions;
    protected HashMap<String, String[]> network;

    public static void main(String[] args) throws Exception {
        new Day8A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 2;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        parseInput(in);
        return findPathLengthTo("AAA", n -> n.equals("ZZZ"));
    }

    protected void parseInput(BufferedReader in) throws IOException {
        instructions = in.readLine();
        in.readLine();

        network = new HashMap<>();
        in.lines().forEach(line -> {
            Matcher matched = Parser.getMatchedMatcher(nodePattern, line);
            network.put(matched.group(1), new String[]{matched.group(2), matched.group(3)});
        });
    }

    protected long findPathLengthTo(String start, Function<String, Boolean> isTarget) {
        long distance = 0;
        String position = start;
        while (true) {
            if (isTarget.apply(position)) return distance;
            String[] options = network.get(position);
            position = instructions.charAt((int) (distance % instructions.length())) == 'L' ? options[0] : options[1];
            distance++;
        }
    }
}