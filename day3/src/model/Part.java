package model;

import java.util.ArrayList;

public class Part {
    private final int x;
    private final int y;
    private final char type;

    private final ArrayList<Label> labels = new ArrayList<>();

    public Part(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getType() {
        return type;
    }

    public void connect(Label label) {
        labels.add(label);
    }

    public boolean isGear() {
        return type == '*' && labels.size() == 2;
    }

    public Label[] getLabels() {
        return labels.toArray(Label[]::new);
    }
}