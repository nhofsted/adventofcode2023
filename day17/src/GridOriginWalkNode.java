import java.util.stream.Stream;

class GridOriginWalkNode extends WalkNode {
    private final City city;

    public GridOriginWalkNode(City city, int x, int y) {
        super(x, y);
        this.city = city;
    }

    @Override
    public Stream<? extends WalkNode> getNeighbours() {
        return Stream.of(new GridOriginWalkNode(city, x - 1, y),
                        new GridOriginWalkNode(city, x + 1, y),
                        new GridOriginWalkNode(city, x, y - 1),
                        new GridOriginWalkNode(city, x, y + 1))
                .filter(next -> next.x() >= 0 && next.y() >= 0 && next.x() < city.width() && next.y() < city.height());
    }

    @Override
    public boolean isTargetNode() {
        return (x == 0) && (y == 0);
    }
}