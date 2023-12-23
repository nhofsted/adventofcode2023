import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day21B extends Day21A {
    public static void main(String[] args) throws Exception {
        new Day21B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 702322399865956L;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        parseFarm(in);

        // This solution will depend on peculiarities in the input, just like day 8 and 20.
        // We depend on the fact that we start in the center, the farm os square, the size is odd and that there is a
        // border and a free row and column in the center. Note that the sample doesn't have all these features.
        // It still annoys me that you need to recognize you can use it and that this isn't part of the problem
        // description; I'd like to think my program is able to handle any input that conforms to the description
        // that is thrown at it.
        // Anyway, given these additional features we can calculate this relatively easy by considering only 9 types
        // of tile.

        int size = farm.length;
        int halfSize = size / 2;

        ArrayList<Long> center = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), startX, startY);
        ArrayList<Long> left = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), farm[startY].length - 1, startY);
        ArrayList<Long> top = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), startX, farm.length - 1);
        ArrayList<Long> right = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), 0, startY);
        ArrayList<Long> bottom = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), startX, 0);
        ArrayList<Long> top_left = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), farm[farm.length - 1].length - 1, farm.length - 1);
        ArrayList<Long> top_right = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), 0, farm.length - 1);
        ArrayList<Long> bottom_left = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), farm[0].length - 1, 0);
        ArrayList<Long> bottom_right = getNumberOfReachablePlots(ArrayUtil.deepClone(farm), 0, 0);

        int target = 26501365;

        long positions = 0;
        positions += getPositions(center, target);
        int orthogonalTarget = target;
        orthogonalTarget -= halfSize + 1;
        while (orthogonalTarget >= 0) {
            positions += getPositions(left, orthogonalTarget);
            positions += getPositions(right, orthogonalTarget);
            positions += getPositions(top, orthogonalTarget);
            positions += getPositions(bottom, orthogonalTarget);
            orthogonalTarget -= size;
        }
        int diagonalTarget = target;
        diagonalTarget -= 2 * (halfSize + 1);
        int span = 1;
        while (diagonalTarget >= 0) {
            positions += span * getPositions(top_left, diagonalTarget);
            positions += span * getPositions(top_right, diagonalTarget);
            positions += span * getPositions(bottom_left, diagonalTarget);
            positions += span * getPositions(bottom_right, diagonalTarget);
            diagonalTarget -= size;
            span += 1;
        }

        return positions;
    }

    private long getPositions(ArrayList<Long> fill, int target) {
        if (target < fill.size()) return fill.get(target);
        else if (target % 2 == fill.size() % 2) return fill.get(fill.size() - 2);
        else return fill.get(fill.size() - 1);
    }

    private ArrayList<Long> getNumberOfReachablePlots(int[][] farm, int startX, int startY) {
        HashSet<Point> edge = new HashSet<>();
        edge.add(new Point(startX, startY));

        ArrayList<Long> reachable = new ArrayList<>();

        long evenReachable = 0;
        long oddReachable = 0;
        int step = 0;
        while (!edge.isEmpty()) {
            HashSet<Point> nextEdge = new HashSet<>();
            for (Point p : edge) {
                if (step % 2 == 0) {
                    farm[p.y()][p.x()] = 0x02;
                    evenReachable++;
                } else {
                    farm[p.y()][p.x()] = 0x01;
                    oddReachable++;
                }
                getNeighbours(farm, p.x(), p.y()).forEach(nextEdge::add);
            }
            edge = nextEdge;
            reachable.add(step % 2 == 0 ? evenReachable : oddReachable);
            step++;
        }
        return reachable;
    }
}