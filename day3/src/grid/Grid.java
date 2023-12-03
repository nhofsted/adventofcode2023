package grid;

import java.io.BufferedReader;
import java.util.Arrays;

public class Grid {
    // 0b 0000 0000 0000 0000
    //              ^^^^ ^^^^ number or part
    //            ^           isPart (boolean)
    //           ^            not a number (boolean) (dot, initial parts)
    public static final int NUMBER_MASK = 0xFF;
    public static final int PART_POS = 8;
    public static final int PART = 0x100;
    public static final int NOT_NUMBER_POS = 9;
    public static final int NOT_NUMBER = 0x200;

    int[][] data;

    private Grid(int[][] data) {
        this.data = data;
    }

    public static Grid parse(BufferedReader in) {
        int[][] data = in.lines()
                .map(line -> line.chars()
                        .map(c -> {
                            if (c == '.') return NOT_NUMBER;
                            if (Character.isDigit(c)) return c - '0';
                            return NOT_NUMBER | PART | c;
                        })
                        .toArray())
                .toArray(int[][]::new);
        return new Grid(data);
    }

    public static boolean isNumber(int d) {
        return (d & NOT_NUMBER) == 0;
    }

    public static boolean isPart(int d) {
        return (d & PART) != 0;
    }

    public static int extractNumber(int d) {
        return d & NUMBER_MASK;
    }

    public static char extractPart(int d) {
        return (char) (d & NUMBER_MASK);
    }

    public int height() {
        return data.length;
    }

    public int width() {
        return data.length > 0 ? data[0].length : 0;
    }

    public int get(int x, int y) {
        return data[y][x];
    }

    public void processHorizontally(GridVisitor v) {
        if (v.getSize() % 2 != 1)
            throw new RuntimeException("Grid visitors need to process an odd number of grid elements");
        final int lookahead = v.getSize() / 2;
        final int[] buffer = new int[v.getSize()];
        Arrays.fill(buffer, NOT_NUMBER);
        for (int[] row : data) {
            for (int b = 0; b < lookahead; ++b) {
                buffer[b + lookahead + 1] = row[b];
            }
            for (int pos = 0; pos < row.length - lookahead; pos++) {
                shiftBufferLeft(buffer, row[pos + lookahead]);
                row[pos] = v.visit(buffer);
            }
            for (int b = 0; b < lookahead; ++b) {
                shiftBufferLeft(buffer, NOT_NUMBER);
                row[row.length - lookahead + b] = v.visit(buffer);
            }
            v.end();
        }
    }

    public void processVertically(GridVisitor v) {
        if (v.getSize() % 2 != 1)
            throw new RuntimeException("Grid visitors need to process an odd number of grid elements");
        final int lookahead = v.getSize() / 2;
        final int[] buffer = new int[v.getSize()];
        Arrays.fill(buffer, NOT_NUMBER);
        int columnWidth = width();
        for (int column = 0; column < columnWidth; ++column) {
            for (int b = 0; b < lookahead; ++b) {
                buffer[b + lookahead + 1] = data[b][column];
            }
            for (int pos = 0; pos < data.length - lookahead; pos++) {
                shiftBufferLeft(buffer, data[pos + lookahead][column]);
                data[pos][column] = v.visit(buffer);
            }
            for (int b = 0; b < lookahead; ++b) {
                shiftBufferLeft(buffer, NOT_NUMBER);
                data[data.length - lookahead + b][column] = v.visit(buffer);
            }
            v.end();
        }
    }

    private void shiftBufferLeft(int[] buffer, int value) {
        if (buffer.length - 1 >= 0) System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);
        buffer[buffer.length - 1] = value;
    }
}