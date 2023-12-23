import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

public class Day21A extends Puzzle {
    protected int[][] farm;
    protected int startX;
    protected int startY;

    public static void main(String[] args) throws Exception {
        new Day21A().run();
    }

    protected static Stream<Point> getNeighbours(int[][] farm, int x, int y) {
        return Stream.of(new Point(x - 1, y),
                        new Point(x, y - 1),
                        new Point(x + 1, y),
                        new Point(x, y + 1))
                .filter(p -> p.y >= 0 && p.y < farm.length && p.x >= 0 && p.x < farm[p.y].length)
                .filter(p -> farm[p.y][p.x] == 0x00);
    }

    @Override
    protected long getSampleSolution() {
        return 16;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        parseFarm(in);

        HashSet<Point> edge = new HashSet<>();
        edge.add(new Point(startX, startY));

        int evenReachable = 0;

        boolean isSample = farm.length < 12;
        int steps = isSample ? 6 : 64;
        for (int step = 0; step <= steps; ++step) {
            HashSet<Point> nextEdge = new HashSet<>();
            for (Point p : edge) {
                if (step % 2 == 0) {
                    farm[p.y()][p.x()] = 0x02;
                    evenReachable++;
                } else {
                    farm[p.y()][p.x()] = 0x01;
                }
                getNeighbours(farm, p.x, p.y).forEach(nextEdge::add);
            }
            edge = nextEdge;
        }

        return evenReachable;
    }

    protected void parseFarm(BufferedReader in) throws IOException {
        String line;
        ArrayList<int[]> tempFarm = new ArrayList<>();
        int y = 0;
        while ((line = in.readLine()) != null) {
            int[] tempLine = new int[line.length()];
            int x = 0;
            while (x < line.length()) {
                tempLine[x] = switch (line.charAt(x)) {
                    case '.' -> 0x00;
                    case '#' -> 0xF0;
                    case 'S' -> rememberStart(x, y);
                    default -> throw new IllegalStateException("Unexpected value: " + line.charAt(x));
                };
                x++;
            }
            tempFarm.add(tempLine);
            y++;
        }
        farm = tempFarm.toArray(new int[tempFarm.size()][]);
    }

    private int rememberStart(int x, int y) {
        startX = x;
        startY = y;
        return 0x00;
    }

    protected record Point(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}