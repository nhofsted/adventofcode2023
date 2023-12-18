import java.util.Objects;
import java.util.stream.Stream;

public abstract class WalkNode {
    protected final int x;
    protected final int y;

    public WalkNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    final public int y() {
        return y;
    }

    final public int x() {
        return x;
    }

    public abstract Stream<? extends WalkNode> getNeighbours();

    public abstract boolean isTargetNode();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalkNode that = (WalkNode) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}