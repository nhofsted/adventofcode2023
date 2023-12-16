import java.io.BufferedReader;
import java.util.stream.Stream;

public class Day16A extends Puzzle {

    protected Contraption contraption;

    public static void main(String[] args) throws Exception {
        new Day16A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 46;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        contraption = Contraption.parse(in);
        return generateStartingPositions()
                .map(this::calculateEnergyLevel)
                .reduce(Long::max)
                .orElse(0L);
    }

    protected Stream<Contraption.LightBeam> generateStartingPositions() {
        return Stream.of(new Contraption.LightBeam(0, 0, Contraption.LIGHT_EAST));
    }

    private long calculateEnergyLevel(Contraption.LightBeam b) {
        Contraption c = new Contraption(contraption);
        c.simulate(b);
        return c.countEnergized();
    }
}