import java.util.Objects;
import java.util.stream.Stream;

class CrucibleWalkNode extends WalkNode {
    public final static int NORTH = 0x11;
    public final static int EAST = 0x22;
    public final static int SOUTH = 0x14;
    public final static int WEST = 0x28;

    private final WalkParams config;
    private final int enterDirection;
    private final int stepsInDirection;

    public CrucibleWalkNode(WalkParams wp, int x, int y) {
        super(x, y);
        this.config = wp;
        this.enterDirection = 0;
        this.stepsInDirection = 0;
    }

    public CrucibleWalkNode(CrucibleWalkNode prev, int enterDirection) {
        super(enterDirection == NORTH ? prev.x - 1 : enterDirection == SOUTH ? prev.x + 1 : prev.x,
                enterDirection == WEST ? prev.y - 1 : enterDirection == EAST ? prev.y + 1 : prev.y,
                prev);
        this.config = prev.config;
        this.enterDirection = enterDirection;
        stepsInDirection = enterDirection == prev.enterDirection ? prev.stepsInDirection + 1 : 1;
    }

    @Override
    public Stream<CrucibleWalkNode> getNeighbours() {
        return Stream.of(NORTH, EAST, SOUTH, WEST)
                .filter(d -> d != enterDirection)
                .filter(d -> (d & enterDirection) == 0 || stepsInDirection < config.maxStepsInDirection())
                .filter(d -> stepsInDirection >= config.minStepsInDirection() || (d & enterDirection) != 0 || enterDirection == 0)
                .map(d -> switch (d) {
                    case NORTH -> new CrucibleWalkNode(this, SOUTH);
                    case EAST -> new CrucibleWalkNode(this, WEST);
                    case SOUTH -> new CrucibleWalkNode(this, NORTH);
                    case WEST -> new CrucibleWalkNode(this, EAST);
                    default -> throw new IllegalStateException("Unknown direction");
                })
                .filter(next -> next.x() >= 0 && next.y() >= 0 && next.x() < config.width() && next.y() < config.height());
    }

    @Override
    public boolean isTargetNode() {
        return x == config.width() - 1 && y == config.height() - 1 && stepsInDirection >= config.minStepsInDirection();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CrucibleWalkNode that = (CrucibleWalkNode) o;
        return enterDirection == that.enterDirection && stepsInDirection == that.stepsInDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), enterDirection, stepsInDirection);
    }

    public record WalkParams(int maxStepsInDirection, int minStepsInDirection, int width, int height) {
    }
}