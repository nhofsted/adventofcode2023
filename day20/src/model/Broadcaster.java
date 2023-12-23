package model;

public class Broadcaster extends AbstractModule {
    public Broadcaster(String[] targets) {
        super("broadcaster", targets);
    }

    @Override
    public Signal[] process(String source, boolean high) {
        return targets()
                .map(target -> new Signal(name(), target, high))
                .toArray(Signal[]::new);
    }
}