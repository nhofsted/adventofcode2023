import math.MathUtil;

import java.io.BufferedReader;
import java.io.IOException;

public class Day8B extends Day8A {

    public static void main(String[] args) throws Exception {
        new Day8B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 6;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        // I dislike this puzzle. This solution hinges on peculiarities in the input.
        // Solving the general case is much harder.
        // I finally settled on solving it the way I think the author intended it, but I'm not happy with it.
        parseInput(in);

        return network.keySet().stream()
                .filter(node -> node.endsWith("A"))
                .map(n -> findPathLengthTo(n, t -> t.endsWith("Z")))
                .reduce(MathUtil::lcm)
                .orElse((long)0);
    }
}