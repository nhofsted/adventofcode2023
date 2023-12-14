import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day14A extends Puzzle {

    protected char[][] dish;

    public static void main(String[] args) throws Exception {
        new Day14A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 136;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        dish = parse(in);
        shiftNorth();
        return calculateLoad();
    }

    protected long calculateLoad() {
        long load = 0;
        for (int y = 0; y < dish.length; ++y) {
            long loadAmount = dish.length - y;
            for (int x = 0; x < dish[0].length; ++x) {
                if(dish[y][x] == 'O') load += loadAmount;
            }
        }
        return load;
    }

    protected void shiftNorth() {
        for (int x = 0; x < dish[0].length; ++x) {
            final int fx = x;
            DishVisitor v = new DishVisitor() {
                @Override
                void setValue(int pos, char c) {
                    dish[pos][fx] = c;
                }
            };
            for (int y = 0; y < dish[0].length; ++y) {
                v.visit(y, dish[y][x], 1);
            }
        }
    }

    protected char[][] parse(BufferedReader in) throws IOException {
        ArrayList<char[]> builder = new ArrayList<>();
        String input;
        while ((input = in.readLine()) != null) {
            builder.add(input.toCharArray());
        }
        return builder.toArray(new char[][]{});
    }
}