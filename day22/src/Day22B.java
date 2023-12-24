import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day22B extends Day22A {

    public static void main(String[] args) throws Exception {
        new Day22B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 7;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        ArrayList<Block> blocks = parseBlocks(in).sorted().collect(Collectors.toCollection(ArrayList<Block>::new));
        HashSet<Block> required = settleAndReturnRequiredBlocks(blocks);
        int falling = 0;
        for (Block r : required) {
            falling += countDroppedIfMissing(blocks, r);
        }
        return falling;
    }

    private int countDroppedIfMissing(ArrayList<Block> blocks, Block r) {
        HashMap<Point, Pair<Block, Integer>> heightMap = new HashMap<>();
        AtomicInteger blocksFalling = new AtomicInteger();
        blocks.stream()
                .filter(b -> !b.equals(r))
                .forEach(b -> {
                    int restingHeight = b.coverageXY()
                            .map(heightMap::get)
                            .filter(Objects::nonNull)
                            .map(Pair::second)
                            .reduce(Integer::max).orElse(0);
                    int topHeight = restingHeight + b.height();
                    int drop = Math.min(b.z1(), b.z2()) - restingHeight;
                    if (drop > 0) blocksFalling.incrementAndGet();
                    b.coverageXY().forEach(p -> heightMap.put(p, Pair.of(b, topHeight)));
                });
        return blocksFalling.get();
    }
}