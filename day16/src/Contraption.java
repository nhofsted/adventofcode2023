import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Contraption {
    public static final int LIGHT = 0b0000_1111_0000;
    public static final int LIGHT_WEST = 0b1000_0000;
    public static final int LIGHT_SOUTH = 0b0100_0000;
    public static final int LIGHT_EAST = 0b0010_0000;
    public static final int LIGHT_NORTH = 0b0001_0000;
    public static final int CONTRAPTION = 0b0000_0000_1111;
    public static final int SPLIT_HORIZONTAL = 0b0000_1000;
    public static final int MIRROR_NE = 0b0000_0010;
    public static final int MIRROR_SE = 0b0000_0001;
    public static final int EMPTY = 0b0000_0000;
    private static final int SPLIT_VERTICAL = 0b0000_0100;

    private final int[][] contraption;

    private Contraption(int[][] contraption) {
        this.contraption = contraption;
    }

    public Contraption(Contraption other) {
        this.contraption = ArrayUtil.deepClone(other.contraption);
    }

    public static Contraption parse(BufferedReader in) {
        return new Contraption(in.lines()
                .map(line -> line.chars()
                        .map(c -> switch (c) {
                            case '.' -> EMPTY;
                            case '/' -> MIRROR_NE;
                            case '\\' -> MIRROR_SE;
                            case '-' -> SPLIT_HORIZONTAL;
                            case '|' -> SPLIT_VERTICAL;
                            default -> throw new IllegalStateException("Unexpected value: " + c);
                        })
                        .toArray())
                .toArray(int[][]::new));
    }

    public long countEnergized() {
        return Arrays.stream(contraption)
                .map(line -> Arrays.stream(line)
                        .reduce(0, (total, c) -> (c & LIGHT) != 0 ? total + 1 : total))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public void simulate(LightBeam beam) {
        Queue<LightBeam> beams = new LinkedList<>();
        beams.add(beam);
        while (!beams.isEmpty()) {
            LightBeam b = beams.poll();
            if (b.y() < 0 || b.x() < 0 || b.y() >= contraption.length || b.x() >= contraption[b.y()].length) continue;
            if ((b.direction() & contraption[b.y()][b.x()] & LIGHT) != 0) continue;
            contraption[b.y()][b.x()] |= b.direction();
            switch (contraption[b.y()][b.x()] & CONTRAPTION) {
                case MIRROR_NE -> {
                    switch (b.direction()) {
                        case LIGHT_WEST -> beams.add(b.south());
                        case LIGHT_SOUTH -> beams.add(b.west());
                        case LIGHT_EAST -> beams.add(b.north());
                        case LIGHT_NORTH -> beams.add(b.east());
                        default -> throw new IllegalStateException("Unexpected value: " + b.direction());
                    }
                }
                case MIRROR_SE -> {
                    switch (b.direction()) {
                        case LIGHT_WEST -> beams.add(b.north());
                        case LIGHT_SOUTH -> beams.add(b.east());
                        case LIGHT_EAST -> beams.add(b.south());
                        case LIGHT_NORTH -> beams.add(b.west());
                        default -> throw new IllegalStateException("Unexpected value: " + b.direction());
                    }
                }
                case SPLIT_HORIZONTAL -> {
                    switch (b.direction()) {
                        case LIGHT_WEST -> beams.add(b.west());
                        case LIGHT_EAST -> beams.add(b.east());
                        case LIGHT_SOUTH, LIGHT_NORTH -> Collections.addAll(beams, b.east(), b.west());
                        default -> throw new IllegalStateException("Unexpected value: " + b.direction());
                    }
                }
                case SPLIT_VERTICAL -> {
                    switch (b.direction()) {
                        case LIGHT_SOUTH -> beams.add(b.south());
                        case LIGHT_NORTH -> beams.add(b.north());
                        case LIGHT_EAST, LIGHT_WEST -> Collections.addAll(beams, b.north(), b.south());
                        default -> throw new IllegalStateException("Unexpected value: " + b.direction());
                    }
                }
                default -> {
                    switch (b.direction()) {
                        case LIGHT_WEST -> beams.add(b.west());
                        case LIGHT_SOUTH -> beams.add(b.south());
                        case LIGHT_EAST -> beams.add(b.east());
                        case LIGHT_NORTH -> beams.add(b.north());
                    }
                }
            }
        }
    }

    public int getWidth() {
        if (contraption.length == 0) return 0;
        return contraption[0].length;
    }

    public int getHeight() {
        return contraption.length;
    }

    public static record LightBeam(int x, int y, int direction) {
        LightBeam north() {
            return new LightBeam(x, y - 1, LIGHT_NORTH);
        }

        LightBeam east() {
            return new LightBeam(x + 1, y, LIGHT_EAST);
        }

        LightBeam south() {
            return new LightBeam(x, y + 1, LIGHT_SOUTH);
        }

        LightBeam west() {
            return new LightBeam(x - 1, y, LIGHT_WEST);
        }
    }
}