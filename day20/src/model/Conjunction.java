package model;

import java.util.HashMap;

public class Conjunction extends AbstractModule {
    HashMap<String, Boolean> inputs = new HashMap<>();

    public Conjunction(String name, String[] targets) {
        super(name, targets);
    }

    @Override
    public void addInput(String input) {
        super.addInput(input);
        inputs.put(input, false);
    }

    @Override
    public Signal[] process(String source, boolean high) {
        inputs.put(source, high);

        boolean newPulse = !inputs.values().stream().allMatch(b -> b);
        return targets()
                .map(target -> new Signal(name(), target, newPulse))
                .toArray(Signal[]::new);
    }
}