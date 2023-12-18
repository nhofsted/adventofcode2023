import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class City {
    private final int height;
    private final int[][] heatLoss;
    private final int[][] minHeatLoss;
    private int width = 0;

    private City(int[][] heatLoss) {
        this.heatLoss = heatLoss;
        height = this.heatLoss.length;
        if (height > 0) width = this.heatLoss[0].length;
        minHeatLoss = new int[heatLoss.length][];
        initializeMinHeatLoss();
    }

    public static City parse(BufferedReader in) {
        return new City(in.lines()
                .map(line -> line.chars()
                        .map(c -> c - '0')
                        .toArray())
                .toArray(int[][]::new));
    }

    private void initializeMinHeatLoss() {
        for (int i = 0; i < height; ++i) {
            minHeatLoss[i] = new int[heatLoss[i].length];
            Arrays.fill(minHeatLoss[i], Integer.MAX_VALUE);
        }

        SortedHeapSet<WalkNode> todo = new SortedHeapSet<>(Comparator.comparingInt(node -> minHeatLoss[node.y()][node.x()]));
        minHeatLoss[height - 1][width - 1] = 0;
        todo.add(new GridOriginWalkNode(this, width - 1, height - 1));

        while (!todo.isEmpty()) {
            WalkNode node = todo.pollFirst();
            node.getNeighbours()
                    .forEach(next -> {
                        int nextHeatLoss = minHeatLoss[node.y()][node.x()] + heatLoss[node.y()][node.x()];
                        int bestHeatLoss = minHeatLoss[next.y()][next.x()];
                        if (nextHeatLoss < bestHeatLoss) {
                            minHeatLoss[next.y()][next.x()] = nextHeatLoss;
                            todo.add(next);
                        }
                    });
        }
    }

    public int findShortestDistance(WalkNode w) {
        HashMap<WalkNode, Integer> best = new HashMap<>();

        SortedHeapSet<WalkNode> todo = new SortedHeapSet<>(Comparator.comparingInt(node -> best.get(node) + heuristic(node)));
        best.put(w, 0);
        todo.add(w);

        while (!todo.isEmpty()) {
            WalkNode node = todo.pollFirst();
            if (node.isTargetNode()) return best.get(node);
            node.getNeighbours()
                    .forEach(next -> {
                        int nextHeatLoss = best.get(node) + heatLoss[next.y()][next.x()];
                        Integer bestHeatLoss = best.get(next);
                        if (bestHeatLoss == null || nextHeatLoss < bestHeatLoss) {
                            best.put(next, nextHeatLoss);
                            todo.add(next);
                        }
                    });
        }

        return Integer.MAX_VALUE;
    }

    private int heuristic(WalkNode node) {
        return minHeatLoss[node.y()][node.x()];
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}