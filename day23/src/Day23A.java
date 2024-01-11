import java.io.BufferedReader;
import java.util.HashSet;

public class Day23A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day23A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 94;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        Maze m = Maze.parse(in, isDry());
        Pair<Node, Node> startEnd = m.simplify();
        return longestPath(startEnd.first(), startEnd.second(), 0, new HashSet<>());
    }

    protected boolean isDry() {
        return false;
    }

    private int longestPath(Node current, Node target, int distance, HashSet<Node> visited) {
        if (current == target) return distance;
        return current.connections.stream()
                .filter(next -> !visited.contains(next.second()))
                .map(next -> {
                    HashSet<Node> newVisited = new HashSet<>(visited);
                    newVisited.add(next.second());
                    return longestPath(next.second(), target, distance + next.first(), newVisited);
                }).reduce(Integer::max)
                .orElse(0);
    }
}