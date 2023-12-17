import java.io.BufferedReader;

public class Day17A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day17A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 102;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        City c = City.parse(in);
        return c.findShortestDistance(new CrucibleWalkNode(getWalkConfig(c), 0, 0));
    }

    protected CrucibleWalkNode.WalkParams getWalkConfig(City c) {
        return new CrucibleWalkNode.WalkParams(3, 0, c.width(), c.height());
    }
}