import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day11A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day11A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 374;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        SortedSet<Integer> xValues = new TreeSet<>();
        SortedSet<Integer> yValues = new TreeSet<>();
        ArrayList<Galaxy> galaxies = new ArrayList<>();

        int y = 0;
        String line;
        while ((line = in.readLine()) != null) {
            for (int x = 0; x < line.length(); ++x) {
                char c = line.charAt(x);
                if (c == '#') {
                    xValues.add(x);
                    yValues.add(y);
                    galaxies.add(new Galaxy(x, y));
                }
            }
            ++y;
        }

        long distance = 0L;
        for (int i = 0; i < galaxies.size(); ++i) {
            Galaxy gi = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); ++j) {
                Galaxy gj = galaxies.get(j);
                distance += getDistance(gi.x(), gj.x(), xValues);
                distance += getDistance(gi.y(), gj.y(), yValues);
            }
        }
        return distance;
    }

    private long getDistance(int c1, int c2, SortedSet<Integer> occupiedValues) {
        if (c1 > c2) {
            int t = c1;
            c1 = c2;
            c2 = t;
        }
        long distance = 0L;
        long delta = c2 - c1;
        distance += delta;
        if (delta > 1) {
            long occupied = occupiedValues.headSet(c2).tailSet(c1 + 1).size();
            long unoccupied = delta - 1 - occupied;
            distance += (getExpansion() - 1) * unoccupied;
        }
        return distance;
    }

    protected long getExpansion() {
        return 2;
    }

    private static record Galaxy(int x, int y) {
    }
}