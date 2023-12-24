import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day22A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 5;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        ArrayList<Block> blocks = parseBlocks(in).sorted().collect(Collectors.toCollection(ArrayList<Block>::new));
        HashSet<Block> required = settleAndReturnRequiredBlocks(blocks);
        return blocks.size() - required.size();
    }

    protected HashSet<Block> settleAndReturnRequiredBlocks(ArrayList<Block> blocks) {
        HashMap<Point, Pair<Block, Integer>> heightMap = new HashMap<>();
        HashMap<Block, Integer> visible = new HashMap<>();
        // Overkill for part 1, but this allows us to reuse the code for part 2
        HashSet<Block> required = new HashSet<>();
        for (int i = 0; i < blocks.size(); ++i) {
            Block falling = blocks.get(i);
            int restingHeight = falling.coverageXY()
                    .map(heightMap::get)
                    .filter(Objects::nonNull)
                    .map(Pair::second)
                    .reduce(Integer::max).orElse(0);
            int topHeight = restingHeight + falling.height();
            // Overkill for part 1, but this allows us to reuse the code for part 2
            int drop = Math.min(falling.z1(), falling.z2()) - restingHeight;
            final Block b = new Block(falling.x1(), falling.y1(), falling.z1() - drop, falling.x2(), falling.y2(), falling.z2() - drop);
            blocks.set(i, b);
            HashSet<Block> supportingBlocks = b.coverageXY()
                    .map(heightMap::get)
                    .filter(Objects::nonNull)
                    .filter(s -> s.second() == restingHeight)
                    .map(Pair::first)
                    .collect(Collectors.toCollection(HashSet::new));
            if (supportingBlocks.size() == 1) required.addAll(supportingBlocks);
            b.coverageXY()
                    .map(heightMap::get)
                    .filter(Objects::nonNull)
                    .map(Pair::first)
                    .forEach(c -> {
                        int newVisible = visible.get(c) - 1;
                        visible.put(c, newVisible);
                        if (newVisible == 0) {
                            visible.remove(c);
                        }
                    });
            b.coverageXY().forEach(p -> heightMap.put(p, Pair.of(b, topHeight)));
            visible.put(b, b.areaXY());
        }
        return required;
    }

    protected Stream<Block> parseBlocks(BufferedReader in) {
        return in.lines().map(Block::parse);
    }
}