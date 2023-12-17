public class Day17B extends Day17A {

    public static void main(String[] args) throws Exception {
        new Day17B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 94;
    }

    protected CrucibleWalkNode.WalkParams getWalkConfig(City c) {
        return new CrucibleWalkNode.WalkParams(10, 4, c.width(), c.height());
    }
}