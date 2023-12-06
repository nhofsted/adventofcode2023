import java.io.BufferedReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day6A extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day6A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 288;
    }

    protected Long[][] parseRaces(BufferedReader in) {
        return in.lines()
                .map(line -> Arrays.stream(line.split("\\s+"))
                        .skip(1)
                        .map(Long::parseLong)
                        .toArray(Long[]::new))
                .toArray(Long[][]::new);
    }

    @Override
    protected long getSolution(BufferedReader in) {
        Long[][] races = parseRaces(in);

        return IntStream.range(0, races[0].length)
                .map(r -> {
                    double[] solutions = solveQuadratic(-1, races[0][r], -races[1][r]);
                    if (solutions == null) return 0;
                    if (solutions[0] > solutions[1]) {
                        double t = solutions[0];
                        solutions[0] = solutions[1];
                        solutions[1] = t;
                    }
                    int distance = (int) (solutions[1]) - (int) (solutions[0]);
                    // remove exact matches
                    if (distance > 0 && solutions[1] == (int) solutions[0]) distance--;
                    if (distance > 0 && solutions[1] == (int) solutions[1]) distance--;
                    return distance;
                })
                .reduce(Math::multiplyExact)
                .orElse(0);
    }

    private double[] solveQuadratic(double a, double b, double c) {
        double D = Math.sqrt(b * b - 4 * a * c);
        if (D < 0) return null;
        return new double[]{(-b - D) / (2 * a), (-b + D) / (2 * a)};
    }
}