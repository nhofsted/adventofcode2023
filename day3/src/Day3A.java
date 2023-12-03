import grid.Grid;
import grid.visitors.PartNumberAdder;
import grid.visitors.PartNumberTagger;

import java.io.BufferedReader;

public class Day3A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day3A().run();
    }

    @Override
    protected int getSampleSolution() {
        return 4361;
    }

    @Override
    protected int getSolution(BufferedReader in) {
        Grid g = Grid.parse(in);

        // grow parts - this problem is separable
        PartNumberTagger pnt = new PartNumberTagger();
        g.processHorizontally(pnt);
        g.processVertically(pnt);

        // sum part numbers
        PartNumberAdder pna = new PartNumberAdder();
        g.processHorizontally(pna);

        return pna.getSum();
    }
}