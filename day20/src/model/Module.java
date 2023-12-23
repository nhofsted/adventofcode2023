package model;

import java.util.HashMap;
import java.util.stream.Stream;

public interface Module {
    String name();

    void addInput(String input);

    Stream<String> targets();

    Stream<String> inputs();

    Signal[] process(String source, boolean high);

    void updateCycle(String input, long cycle, HashMap<String, Module> modules);

    long cycle();
}