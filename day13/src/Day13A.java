import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day13A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day13A().run();
    }

    private static boolean isMirror(char[] data, int pos) {
        int distance = Math.min(pos, data.length - pos);
        int start = pos - distance;
        for (int i = 0; i < distance; ++i) {
            if (data[start + i] != data[pos + distance - i - 1]) return false;
        }
        return true;
    }

    @Override
    protected long getSampleSolution() {
        return 405;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        ArrayList<char[][]> patterns = new ArrayList<>();

        String input;
        while ((input = in.readLine()) != null) {
            ArrayList<char[]> builder = new ArrayList<>();
            do {
                builder.add(input.toCharArray());
            } while ((input = in.readLine()) != null && !input.isEmpty());
            patterns.add(builder.toArray(new char[][]{}));
        }

        int verticals = patterns.stream()
                .map(this::findMirror)
                .reduce(Integer::sum)
                .orElse(0);

        int horizontals = patterns.stream()
                .map(this::transpose)
                .map(this::findMirror)
                .reduce(Integer::sum)
                .orElse(0);

        return verticals + 100L * horizontals;
    }

    private char[][] transpose(char[][] chars) {
        int width = chars[0].length;
        int height = chars.length;
        char[][] retVal = new char[width][];
        for (int i = 0; i < width; ++i) {
            retVal[i] = new char[height];
        }
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                retVal[x][y] = chars[y][x];
            }
        }
        return retVal;
    }

    private int findMirror(char[][] chars) {
        int[] possibilities = new int[chars[0].length - 1];
        Arrays.fill(possibilities, getSmudges());
        for (char[] line : chars) {
            for (int p = 0; p < possibilities.length; ++p) {
                if ((possibilities[p] >= 0) && !isMirror(line, p+1)) {
                    possibilities[p]--;
                }
            }
        }

        for (int p = 0; p < possibilities.length; ++p) {
            if (possibilities[p] == 0) return p+1;
        }
        return 0;
    }

    protected int getSmudges() {
        return 0;
    }
}