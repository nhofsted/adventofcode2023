package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SignalQueue {
    Queue<Signal> signals = new LinkedList<>();
    int highCount = 0;
    int lowCount = 0;

    public Signal poll() {
        return signals.poll();
    }

    public void addAll(Signal[] newSignals) {
        Arrays.stream(newSignals)
                .forEach(signal -> {
                    if (signal.high()) highCount++;
                    else lowCount++;
                    signals.add(signal);
                });
    }

    public long highCount() {
        return highCount;
    }

    public long lowCount() {
        return lowCount;
    }
}