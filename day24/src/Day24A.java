import java.io.BufferedReader;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day24A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day24A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 2;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        HailStone[] hailStones = in.lines()
                .map(HailStone::parse)
                .toArray(HailStone[]::new);

        // Sample has different bounds
        final double min = hailStones.length == 5 ? 7 : 2e14;
        final double max = hailStones.length == 5 ? 27 : 4e14;

        return IntStream.range(0, hailStones.length)
                .mapToObj(h1 -> IntStream.range(h1 + 1, hailStones.length)
                        .mapToObj(h2 -> intersectXYFuture(hailStones[h1], hailStones[h2]))
                        .filter(Objects::nonNull)
                        .filter(p -> p.x() >= min && p.x() <= max
                                && p.y() >= min && p.y() <= max)
                        .count())
                .reduce(Long::sum)
                .orElse(0L);
    }

    private Point intersectXYFuture(HailStone h1, HailStone h2) {
        if (h1.vy() * h2.vx() == h1.vx() * h2.vy()) return null;
        double x = (h1.vx() * (h2.vy() * h2.x() + h2.vx() * (h1.y() - h2.y())) - h1.vy() * h2.vx() * h1.x()) / (h1.vx() * h2.vy() - h1.vy() * h2.vx());
        double y = (h1.vx() * h2.vy() * h1.y() - h1.vy() * (h2.vy() * (h1.x() - h2.x()) + h2.vx() * h2.y())) / (h1.vx() * h2.vy() - h1.vy() * h2.vx());
        double t1 = (x - h1.x()) / h1.vx();
        if (t1 < 0) return null;
        double t2 = (x - h2.x()) / h2.vx();
        if (t2 < 0) return null;
        return new Point(x, y);
    }
}