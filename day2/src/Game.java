import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Game(int id, int[][] draws) {
    private static final String[] colors = {"red", "green", "blue"};
    private static final HashMap<String, Integer> colorIndexLookup = new HashMap<>();
    private static final Pattern gamePattern = Pattern.compile("Game (\\d+)");
    private static final Pattern ballsPattern = Pattern.compile(" (\\d+) (\\w+)");

    static {
        for (int i = 0; i < colors.length; ++i) {
            colorIndexLookup.put(colors[i], i);
        }
    }

    public static Game parse(String game) {
        String[] parts = game.split(":");
        Matcher gameMatcher = Parser.getMatchedMatcher(gamePattern, parts[0]);
        int id = Integer.parseInt(gameMatcher.group(1));

        int[][] draws = Arrays.stream(parts[1].split(";"))
                .map(draw -> {
                    final int[] ballCounts = new int[colors.length];
                    Arrays.stream(draw.split(","))
                            .forEach(balls -> {
                                Matcher ballsMatcher = Parser.getMatchedMatcher(ballsPattern, balls);
                                ballCounts[colorIndexLookup.get(ballsMatcher.group(2))] = Integer.parseInt(ballsMatcher.group(1));
                            });
                    return ballCounts;
                })
                .toArray(int[][]::new);

        return new Game(id, draws);
    }

    public boolean isPossible(int[] balls) {
        for (int[] draw : draws) {
            for (int i = 0; i < balls.length; ++i) {
                if (balls[i] < draw[i]) return false;
            }
        }
        return true;
    }

    public int[] minimumBalls() {
        return Arrays.stream(draws)
                .reduce((c, e) -> ArrayUtil.combineArrays(c, e, Integer::max))
                .orElse(new int[colors.length]);
    }
}