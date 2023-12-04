import java.io.BufferedReader;

public class Day4A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day4A().run();
    }

    @Override
    protected int getSampleSolution() {
        return 13;
    }

    @Override
    protected int getSolution(BufferedReader in) {
        return in.lines()
                .map(Ticket::parse)
                .map(Ticket::points)
                .reduce(Integer::sum)
                .orElse(0);
    }
}