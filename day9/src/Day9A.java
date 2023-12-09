import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Day9A extends Puzzle {
    public static void main(String[] args) throws Exception {
        new Day9A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 114;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        return in.lines()
                .map(this::parse)
                .map(n -> calculateIndex(getConstants(n), positionRequested(n.length)))
                .reduce(Long::sum)
                .orElse((long) 0);
    }

    protected int positionRequested(int inputLength) {
        return inputLength;
    }

    private ArrayList<Long> getConstants(Long[] numbers) {
        // Build pyramid
        ArrayList<ArrayList<Long>> pyramidLines = new ArrayList<>();
        ArrayList<Long> retVal = new ArrayList<>();

        int numberIndex = 0;
        long lastAdded;
        ArrayList<Long> firstLine = new ArrayList<>();
        lastAdded = numbers[numberIndex++];
        firstLine.add(lastAdded);
        pyramidLines.add(firstLine);
        retVal.add(lastAdded);

        do {
            pyramidLines.add(new ArrayList<>());
            firstLine.add(numbers[numberIndex++]);
            ArrayList<Long> previousLine = firstLine;
            for (int i = 1; i < pyramidLines.size(); ++i) {
                ArrayList<Long> line = pyramidLines.get(i);
                lastAdded = previousLine.get(line.size() + 1) - previousLine.get(line.size());
                line.add(lastAdded);
                previousLine = line;
            }
            // Collect first elements
            retVal.add(lastAdded);
        } while (lastAdded != 0 || calculateIndex(retVal, numbers.length - 1) != numbers[numbers.length - 1]);
        return retVal;
    }

    private long calculateIndex(ArrayList<Long> constants, int index) {
        double value = constants.get(0);
        long divisor = 1;
        long xPower = 1;
        for (int i = 1; i < constants.size(); ++i) {
            divisor *= i;
            xPower *= index + 1 - i;
            value += (double) (constants.get(i)) * xPower / divisor;
        }
        return (long) value;
    }

    private Long[] parse(String s) {
        return Arrays.stream(s.split(" "))
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }
}