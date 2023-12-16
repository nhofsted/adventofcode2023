import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16B extends Day16A {

    public static void main(String[] args) throws Exception {
        new Day16B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 51;
    }

    @Override
    protected Stream<Contraption.LightBeam> generateStartingPositions() {
        int width = contraption.getWidth();
        int height = contraption.getHeight();
        return Stream.of(IntStream.range(0, width)
                                .mapToObj(x -> new Contraption.LightBeam(x, 0, Contraption.LIGHT_SOUTH)),
                        IntStream.range(0, width)
                                .mapToObj(x -> new Contraption.LightBeam(x, width - 1, Contraption.LIGHT_NORTH)),
                        IntStream.range(0, height)
                                .mapToObj(y -> new Contraption.LightBeam(0, y, Contraption.LIGHT_EAST)),
                        IntStream.range(0, height)
                                .mapToObj(y -> new Contraption.LightBeam(height - 1, y, Contraption.LIGHT_WEST)))
                .flatMap(x -> x);
    }
}