package model;

import math.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractModule implements Module {
    private final String name;
    private final String[] targets;
    private final ArrayList<String> inputs;
    private long cycle = 0;

    AbstractModule(String name, String[] targets) {
        this.name = name;
        this.targets = targets;
        this.inputs = new ArrayList<>();
    }

    public String name() {
        return name;
    }

    public Stream<String> targets() {
        return Arrays.stream(targets);
    }

    public Stream<String> inputs() {
        return inputs.stream();
    }

    public void addInput(String input) {
        inputs.add(input);
    }

    @Override
    public void updateCycle(String input, long c, HashMap<String, Module> modules) {
        long newCycle = MathUtil.lcm(Math.max(cycle, 1), c);
        if (newCycle > cycle) {
            cycle = newCycle * internalStates();
            targets().map(modules::get)
                    .filter(Objects::nonNull)
                    .forEach(m -> m.updateCycle(name(), cycle, modules));
        }
    }

    protected long internalStates() {
        return 1;
    }

    @Override
    public long cycle() {
        return cycle;
    }

    @Override
    public String toString() {
        return "AbstractModule{" +
                "name='" + name + '\'' +
                ", targets=" + Arrays.toString(targets) +
                ", inputs=" + inputs +
                ", cycle=" + cycle +
                '}';
    }
}