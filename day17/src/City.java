import java.io.BufferedReader;
import java.util.Comparator;
import java.util.HashMap;

public class City {
    private final int height;
    private final int[][] heatLoss;
    private int width = 0;

    private City(int[][] heatLoss) {
        this.heatLoss = heatLoss;
        height = this.heatLoss.length;
        if (height > 0) width = this.heatLoss[0].length;
    }

    public static City parse(BufferedReader in) {
        return new City(in.lines()
                .map(line -> line.chars()
                        .map(c -> c - '0')
                        .toArray())
                .toArray(int[][]::new));
    }

    public int findShortestDistance(WalkNode w) {
        SortedHeapSet<WalkNode> todo = new SortedHeapSet<>(Comparator.comparingInt(node -> node.heatLoss() + heuristic(node)));
        todo.add(w);

        HashMap<WalkNode, Integer> best = new HashMap<>();

        while (!todo.isEmpty()) {
            WalkNode node = todo.pollFirst();
            if (node.x() == width - 1 && node.y() == height - 1) return node.heatLoss();
            node.getNeighbours()
                    .forEach(next -> {
                        next.addHeatLoss(heatLoss[next.y()][next.x()]);
                        Integer bestHeatLoss = best.get(next);
                        if (bestHeatLoss == null || next.heatLoss() < bestHeatLoss) {
                            todo.add(next);
                            best.put(next, next.heatLoss());
                        }
                    });
        }

        return Integer.MAX_VALUE;
    }

    private int heuristic(WalkNode node) {
        return (width - 1 - node.x()) + (height - 1 - node.y());
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}