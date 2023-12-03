package model;

import java.util.ArrayList;

public class Label {
    private final int x;
    private final int y;
    private final int length;
    private final int value;

    private final ArrayList<Part> parts = new ArrayList<>();

    public Label(int x, int y, int length, int value) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public int getValue() {
        return value;
    }

    public void connect(Part part) {
        parts.add(part);
    }

    public boolean isPartLabel() {
        return parts.size() > 0;
    }

    public Part[] getParts() {
        return parts.toArray(Part[]::new);
    }
}