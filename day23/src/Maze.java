import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Optional;

public class Maze {
    private final int[][] maze;

    Maze(int[][] maze) {
        this.maze = maze;
    }

    public static Maze parse(BufferedReader in, boolean isDry) {
        return new Maze(in.lines()
                .map(line -> line.chars()
                        .map(tile -> (isDry && (tile == '<'
                                || tile == '>'
                                || tile == 'v'
                                || tile == '^')) ? '.' : tile)
                        .toArray())
                .toArray(int[][]::new));
    }

    public Pair<Node, Node> simplify() {
        // Find and Mark crossroads
        HashSet<Node> nodes = markCrossroads();

        // Add start and end Nodes
        Node start = new Node(1, 0);
        nodes.add(start);
        Node end = new Node(maze[maze.length - 1].length - 2, maze.length - 1);
        nodes.add(end);

        // Collect Distances
        nodes.forEach(n -> collectDistances(n, nodes));

        return Pair.of(start, end);
    }

    private HashSet<Node> markCrossroads() {
        HashSet<Node> crossRoads = new HashSet<>();
        for (int y = 1; y < maze.length - 1; ++y) {
            for (int x = 1; x < maze[y].length - 1; ++x) {
                int paths = 0;
                if (maze[y][x] != '.') continue;
                for (int ny = y - 1; ny <= y + 1; ++ny) {
                    for (int nx = x - 1; nx <= x + 1; ++nx) {
                        if (nx != x && ny != y) continue;
                        int tile = maze[ny][nx];
                        if (tile == '.'
                                || tile == '<'
                                || tile == '>'
                                || tile == 'v'
                                || tile == '^') {
                            paths++;
                        }
                    }
                }
                if (paths > 3) {
                    crossRoads.add(new Node(x, y));
                }
            }
        }
        return crossRoads;
    }

    private void collectDistances(Node n, HashSet<Node> crossRoads) {
        int x = n.x();
        int y = n.y();
        for (int ny = y - 1; ny <= y + 1; ++ny) {
            if (ny < 0 || ny >= maze.length) continue;
            for (int nx = x - 1; nx <= x + 1; ++nx) {
                if (nx != x && ny != y) continue;
                if (ny == y && nx == x) continue;
                int tile = maze[ny][nx];
                if (tile == '.'
                        || tile == '<' && nx < x
                        || tile == '>' && nx > x
                        || tile == 'v' && ny > y
                        || tile == '^' && ny < y) {
                    measureDistance(n, x, y, nx, ny, 1, crossRoads);
                }
            }
        }
    }

    private void measureDistance(Node n, int x, int y, int nx, int ny, int distance, HashSet<Node> crossRoads) {
        Optional<Node> fork = crossRoads.stream().filter(node -> node.x() == nx && node.y() == ny).findFirst();
        if (fork.isPresent()) {
            n.addConnection(fork.get(), distance);
            return;
        }

        for (int nny = ny - 1; nny <= ny + 1; ++nny) {
            if (nny < 0 || nny >= maze.length) continue;
            for (int nnx = nx - 1; nnx <= nx + 1; ++nnx) {
                if (nnx != nx && nny != ny) continue;
                if (nny == y && nnx == x) continue;
                if (nny == ny && nnx == nx) continue;
                int tile = maze[nny][nnx];
                if (tile == '.'
                        || tile == '<' && nnx < nx
                        || tile == '>' && nnx > nx
                        || tile == 'v' && nny > ny
                        || tile == '^' && nny < ny) {
                    measureDistance(n, nx, ny, nnx, nny, ++distance, crossRoads);
                    return;
                }
            }
        }
    }
}