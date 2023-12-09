public class Day1B extends Day1A {
    private static final String[][] translation = {
            {"zero", "0"},
            {"one", "1"},
            {"two", "2"},
            {"three", "3"},
            {"four", "4"},
            {"five", "5"},
            {"six", "6"},
            {"seven", "7"},
            {"eight", "8"},
            {"nine", "9"}
    };

    public static void main(String[] args) throws Exception {
        new Day1B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 281;
    }

    @Override
    protected String preProcess(String line) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        pos:
        while (i < line.length()) {
            for (String[] replace : translation) {
                if (line.startsWith(replace[0], i)) {
                    result.append(replace[1]);
                    i++;
                    continue pos;
                }
            }
            result.append(line.charAt(i++));
        }
        return result.toString();
    }
}