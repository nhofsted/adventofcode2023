public class Day7B extends Day7A {

    public static void main(String[] args) throws Exception {
        new Day7B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 5905;
    }

    @Override
    protected Hand createHand(String[] strings) {
        return new JokerHand(strings[0], Integer.parseInt(strings[1]));
    }
}