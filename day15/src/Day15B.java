import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15B extends Puzzle {

    private final Pattern stepPattern = Pattern.compile("([^=\\-]*)([=\\-])([0-9])?");

    public static void main(String[] args) throws Exception {
        new Day15B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 145;
    }

    @Override
    protected long getSolution(BufferedReader in) throws IOException {
        ArrayList<ArrayList<Lens>> boxes = new ArrayList<>(256);
        for (int i = 0; i < 256; ++i) {
            boxes.add(new ArrayList<>());
        }

        Arrays.stream(in.readLine().split(","))
                .forEach(step -> {
                            Matcher m = Parser.getMatchedMatcher(stepPattern, step);
                            final String label = m.group(1);
                            int boxIndex = Hash.digest(label);
                            ArrayList<Lens> box = boxes.get(boxIndex);
                            if (m.group(2).equals("-")) {
                                for (int slot = 0; slot < box.size(); ++slot) {
                                    Lens lb = box.get(slot);
                                    if (lb.label().equals(label)) {
                                        box.remove(slot);
                                        break;
                                    }
                                }
                                box.remove(new Lens(label, -1));
                            } else {
                                Lens l = new Lens(label, Integer.parseInt(m.group(3)));
                                boolean replaced = false;
                                for (int slot = 0; slot < box.size(); ++slot) {
                                    Lens lb = box.get(slot);
                                    if (lb.label().equals(label)) {
                                        box.set(slot, l);
                                        replaced = true;
                                        break;
                                    }
                                }
                                if (!replaced) {
                                    box.add(l);
                                }
                            }
                        }
                );

        int focusingPower = 0;
        for (int i = 0; i < 256; ++i) {
            ArrayList<Lens> box = boxes.get(i);
            for (int slot = 0; slot < box.size(); ++slot) {
                Lens l = box.get(slot);
                focusingPower += (i + 1) * (slot + 1) * l.focalLength();
            }
        }

        return focusingPower;
    }

    public record Lens(String label, int focalLength) {
    }
}