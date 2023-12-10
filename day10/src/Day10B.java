import java.io.BufferedReader;
import java.io.IOException;

public class Day10B extends Day10A {

    public static void main(String[] args) throws Exception {
        new Day10B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 10;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        super.getSolution(in);

        boolean inside;
        int count = 0;
        for (byte[] line : maze) {
            inside = false;
            for (byte cell : line) {
                if ((cell & LOOP_PART) != 0) {
                    if ((cell & CONNECT_NORTH) != 0) {
                        inside = !inside;
                    }
                } else if (inside) {
                    count++;
                }
            }
        }
        return count;
    }
}