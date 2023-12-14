import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Day14B extends Day14A {

    public static void main(String[] args) throws Exception {
        new Day14B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 64;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        dish = parse(in);
        HashSet<String> historySet = new HashSet<>();
        ArrayList<String> historyList = new ArrayList<>();
        final int target = 1000000000;
        for (int i = 0; i < target; ++i) {
            cycle();
            String cycleDescription = Arrays.deepToString(dish);
            historyList.add(cycleDescription);
            if (!historySet.add(cycleDescription)) {
                int j;
                for (j = 0; j < historyList.size(); ++j) {
                    if (historyList.get(j).equals(cycleDescription)) break;
                }
                int k = ((target - i) % (i - j)) - 1;
                while (k-- > 0) {
                    cycle();
                }
                return calculateLoad();
            }
        }
        return calculateLoad();
    }

    protected void cycle() {
        shiftNorth();
        shiftWest();
        shiftSouth();
        shiftEast();
    }

    protected void shiftWest() {
        for (int y = 0; y < dish[0].length; ++y) {
            final int fy = y;
            DishVisitor v = new DishVisitor() {
                @Override
                void setValue(int pos, char c) {
                    dish[fy][pos] = c;
                }
            };
            for (int x = 0; x < dish[0].length; ++x) {
                v.visit(x, dish[y][x], 1);
            }
        }
    }

    protected void shiftSouth() {
        for (int x = 0; x < dish[0].length; ++x) {
            final int fx = x;
            DishVisitor v = new DishVisitor() {
                @Override
                void setValue(int pos, char c) {
                    dish[pos][fx] = c;
                }
            };
            for (int y = dish[0].length; y-- > 0; ) {
                v.visit(y, dish[y][x], -1);
            }
        }
    }

    protected void shiftEast() {
        for (int y = 0; y < dish[0].length; ++y) {
            final int fy = y;
            DishVisitor v = new DishVisitor() {
                @Override
                void setValue(int pos, char c) {
                    dish[fy][pos] = c;
                }
            };
            for (int x = dish[0].length; x-- > 0; ) {
                v.visit(x, dish[y][x], -1);
            }
        }
    }
}