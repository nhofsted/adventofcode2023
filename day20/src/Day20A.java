import model.Module;
import model.*;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20A extends Puzzle {
    private static final Pattern modulePattern = Pattern.compile("(broadcaster|([%&])([a-z]+)) -> (([a-z]+)(, [a-z]+)*)");

    protected HashMap<String, Module> modules;

    public static void main(String[] args) throws Exception {
        new Day20A().run();
    }

    @Override
    protected long getSampleSolution() {
        return 11687500;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        parseModules(in);

        modules.get("broadcaster").updateCycle("button", 1, modules);
        long cycle = modules.values().stream()
                .map(Module::cycle)
                .reduce(Long::max)
                .orElse(1L);

        long target = 1000;

        // If we're interested in a number smaller than the cycle length, simply brute force
        if (cycle > target) {
            SignalQueue signals = pressButton(modules, target);
            return signals.lowCount() * signals.highCount();
        } else {
            long iterations = target / cycle;
            long remainder = target % cycle;
            // calculate cycle
            SignalQueue signals = pressButton(modules, cycle);
            long highCounts = signals.highCount() * iterations;
            long lowCounts = signals.lowCount() * iterations;
            // add reminder
            SignalQueue remainderSignals = pressButton(modules, remainder);
            highCounts += remainderSignals.highCount();
            lowCounts += remainderSignals.lowCount();
            return highCounts * lowCounts;
        }
    }

    private SignalQueue pressButton(HashMap<String, Module> modules, long times) {
        Button button = new Button();
        SignalQueue signals = new SignalQueue();
        for (int i = 0; i < times; ++i) {
            signals.addAll(button.press());
            Signal current;
            while ((current = signals.poll()) != null) {
                Module m = modules.get(current.target());
                if (m != null) signals.addAll(m.process(current.source(), current.high()));
            }
        }
        return signals;
    }

    protected void parseModules(BufferedReader in) {
        modules = new HashMap<>();
        in.lines().map(this::parseModule)
                .forEach(m -> modules.put(m.name(), m));

        modules.forEach((key, value) -> value.targets()
                .map(modules::get)
                .filter(Objects::nonNull)
                .forEach(m -> m.addInput(key)));
    }

    public Module parseModule(String line) {
        Matcher matchedModule = Parser.getMatchedMatcher(modulePattern, line);

        String[] targets = matchedModule.group(4).split(", ");

        String type = matchedModule.group(2);
        if (type != null && type.equals("&")) {
            return new Conjunction(matchedModule.group(3), targets);
        } else if (type != null && type.equals("%")) {
            return new FlipFlop(matchedModule.group(3), targets);
        } else if (matchedModule.group(1).equals("broadcaster")) {
            return new Broadcaster(targets);
        }
        throw new RuntimeException("Couldn't parse input: " + line);
    }
}