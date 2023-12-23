package model;

public class FlipFlop extends AbstractModule {
    boolean state = false;

    public FlipFlop(String name, String[] targets) {
        super(name, targets);
    }

    @Override
    public Signal[] process(String source, boolean high) {
        if (high) return new Signal[]{};
        state = !state;
        return targets()
                .map(target -> new Signal(name(), target, state))
                .toArray(Signal[]::new);
    }

    protected long internalStates() {
        return 2;
    }
}