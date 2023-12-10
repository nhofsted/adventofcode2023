import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day10A extends Puzzle {
    static byte CONNECT_NORTH = 0b0001;
    static byte CONNECT_EAST = 0b0010;
    static byte CONNECT_SOUTH = 0b0100;
    static byte CONNECT_WEST = 0b1000;
    static byte LOOP_PART = 0b1_0000;

    protected byte[][] maze;
    int startPosX;
    int startPosY;

    public static void main(String[] args) throws Exception {
        new Day10A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 8;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        parseMaze(in);

        byte direction = initializeStartPosition();
        int[] currentState = {startPosX, startPosY, direction};
        int distance = 0;
        while ((maze[currentState[1]][currentState[0]] & LOOP_PART) == 0) {
            maze[currentState[1]][currentState[0]] = (byte) (maze[currentState[1]][currentState[0]] | LOOP_PART);
            advance(currentState);
            distance++;
        }
        return distance / 2;
    }

    private void advance(int[] currentState) {
        int x = currentState[0];
        int y = currentState[1];
        byte direction = (byte) currentState[2];
        byte cell = (byte) (maze[y][x] & ~direction);
        if ((cell & CONNECT_NORTH) != 0) {
            currentState[1] -= 1;
            currentState[2] = CONNECT_SOUTH;
        } else if ((cell & CONNECT_EAST) != 0) {
            currentState[0] += 1;
            currentState[2] = CONNECT_WEST;
        } else if ((cell & CONNECT_SOUTH) != 0) {
            currentState[1] += 1;
            currentState[2] = CONNECT_NORTH;
        } else if ((cell & CONNECT_WEST) != 0) {
            currentState[0] -= 1;
            currentState[2] = CONNECT_EAST;
        }
    }

    private byte initializeStartPosition() {
        byte startDirection = 0;
        if (startPosY > 0 && (maze[startPosY - 1][startPosX] & CONNECT_SOUTH) != 0) {
            maze[startPosY][startPosX] = (byte) (maze[startPosY][startPosX] | CONNECT_NORTH);
            startDirection = CONNECT_NORTH;
        }
        if (startPosX < maze[0].length - 1 && (maze[startPosY][startPosX + 1] & CONNECT_WEST) != 0) {
            maze[startPosY][startPosX] = (byte) (maze[startPosY][startPosX] | CONNECT_EAST);
            startDirection = CONNECT_EAST;
        }
        if (startPosY < maze.length - 1 && (maze[startPosY + 1][startPosX] & CONNECT_NORTH) != 0) {
            maze[startPosY][startPosX] = (byte) (maze[startPosY][startPosX] | CONNECT_SOUTH);
            startDirection = CONNECT_SOUTH;
        }
        if (startPosX > 0 && (maze[startPosY][startPosX - 1] & CONNECT_EAST) != 0) {
            maze[startPosY][startPosX] = (byte) (maze[startPosY][startPosX] | CONNECT_WEST);
            startDirection = CONNECT_WEST;
        }
        return startDirection;
    }

    private void parseMaze(BufferedReader in) {
        ArrayList<byte[]> m = new ArrayList<>();
        in.lines().forEach(line -> {
            char[] chars = line.toCharArray();
            byte[] cells = new byte[chars.length];
            for (int i = 0; i < chars.length; ++i) {
                cells[i] = switch (chars[i]) {
                    case '|' -> (byte) 0b0101;
                    case '-' -> (byte) 0b1010;
                    case 'L' -> (byte) 0b0011;
                    case 'J' -> (byte) 0b1001;
                    case '7' -> (byte) 0b1100;
                    case 'F' -> (byte) 0b0110;
                    case '.' -> (byte) 0;
                    case 'S' -> createStart(i, m.size());
                    default -> throw new RuntimeException("Unknown cell");
                };
            }
            m.add(cells);
        });
        maze = m.toArray(new byte[][]{});
    }

    private byte createStart(int x, int y) {
        startPosX = x;
        startPosY = y;
        return 0b0000;
    }
}