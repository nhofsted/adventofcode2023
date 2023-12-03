import grid.Grid;
import model.Engine;
import model.Label;
import model.Part;

import java.io.BufferedReader;
import java.util.Arrays;

// Unfortunately I've been trying to be too clever and Day3A isn't much help.
public class Day3B extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day3B().run();
    }

    @Override
    protected int getSampleSolution() {
        return 467835;
    }

    @Override
    protected int getSolution(BufferedReader in) {
        Grid grid = Grid.parse(in);
        Engine engine = Engine.build(grid);

        // Checking if this returns the same result as Day3A (spoiler: it does)
        int sumPartLabels = Arrays.stream(engine.getLabels())
                .map(Label::getValue)
                .reduce(Integer::sum)
                .orElse(0);
        System.out.println("(Another way to compute Day3A yields " + sumPartLabels + ")");

        return Arrays.stream(engine.getParts())
                .filter(Part::isGear)
                .map(part -> Arrays.stream(part.getLabels())
                        .map(Label::getValue)
                        .reduce(Math::multiplyExact)
                        .orElse(0))
                .reduce(Integer::sum)
                .orElse(0);
    }
}