import math.MathUtil;
import model.Button;
import model.Module;
import model.Signal;
import model.SignalQueue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Day20B extends Day20A {
    private static final Pattern modulePattern = Pattern.compile("(broadcaster|([%&])([a-z]+)) -> (([a-z]+)(, [a-z]+)*)");

    public static void main(String[] args) throws Exception {
        new Day20B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 143;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        // Again - similar to Day 8 - I dislike this puzzle. This solution hinges on peculiarities in the input.
        // In particular, the input should contain a network of counters triggering once every x presses which
        // then get aggregated.
        parseModules(in);

        String hub = modules.values().stream()
                .filter(m -> m.targets().anyMatch("rx"::equals))
                .findFirst()
                .map(Module::name)
                .orElseThrow();
        Module[] arms = modules.values().stream()
                .filter(m -> m.targets().anyMatch(hub::equals))
                .toArray(Module[]::new);

        HashMap<String, Long> repetitions = new HashMap<>();

        Button button = new Button();
        SignalQueue signals = new SignalQueue();
        long presses = 0;
        while (true) {
            signals.addAll(button.press());
            presses++;
            Signal current;
            while ((current = signals.poll()) != null) {
                if (current.target().equals(hub) && current.high() && !repetitions.containsKey(current.source())) {
                    repetitions.put(current.source(), presses);
                    System.out.println("Assuming repetition: " + presses);
                    if (repetitions.size() == arms.length) {
                        return repetitions.values().stream().reduce(MathUtil::lcm).orElse(1L);
                    }
                }
                Module m = modules.get(current.target());
                if (m != null) signals.addAll(m.process(current.source(), current.high()));
            }
        }
    }
}