package model;

import grid.Grid;

import java.util.ArrayList;

public class Engine {
    ArrayList<Part> parts;
    ArrayList<Label> labels;

    private Engine(ArrayList<Part> parts, ArrayList<Label> labels) {
        this.parts = parts;
        this.labels = labels;
    }

    public static Engine build(Grid grid) {
        // Extract all labels
        ArrayList<ArrayList<Label>> labelsOnRow = new ArrayList<>(grid.height());
        for (int i = 0; i < grid.height(); ++i) {
            labelsOnRow.add(new ArrayList<>());
        }

        int workingLabel = 0;
        int startLabel = 0;
        boolean buildingLabel = false;
        for (int y = 0; y < grid.height(); ++y) {
            for (int x = 0; x < grid.width(); ++x) {
                int d = grid.get(x, y);
                if (Grid.isNumber(d)) {
                    if (!buildingLabel) {
                        startLabel = x;
                    }
                    buildingLabel = true;
                    workingLabel *= 10;
                    workingLabel += Grid.extractNumber(d);
                } else {
                    if (buildingLabel) {
                        labelsOnRow.get(y).add(new Label(startLabel, y, x - startLabel, workingLabel));
                        buildingLabel = false;
                        workingLabel = 0;
                    }
                }
            }
            if (buildingLabel) {
                labelsOnRow.get(y).add(new Label(startLabel, y, grid.width() - startLabel, workingLabel));
                buildingLabel = false;
                workingLabel = 0;
            }
        }

        // Extract all parts
        ArrayList<Part> parts = new ArrayList<>();

        for (int y = 0; y < grid.height(); ++y) {
            for (int x = 0; x < grid.width(); ++x) {
                int d = grid.get(x, y);
                if (Grid.isPart(d)) {
                    Part p = new Part(x, y, Grid.extractPart(d));
                    parts.add(p);
                    // Connect any labels that are near
                    for (int ly = y - 1; ly < Math.min(y + 2, labelsOnRow.size()); ++ly) {
                        for (Label l : labelsOnRow.get(ly)) {
                            if (x >= l.getX() - 1 && x <= l.getX() + l.getLength()) {
                                l.connect(p);
                                p.connect(l);
                            }
                        }
                    }
                }
            }
        }

        // Only keep part labels
        ArrayList<Label> labels = new ArrayList<>();
        labelsOnRow
                .forEach(row -> row.stream()
                        .filter(Label::isPartLabel)
                        .forEach(labels::add));

        return new Engine(parts, labels);
    }

    public Label[] getLabels() {
        return labels.toArray(Label[]::new);
    }

    public Part[] getParts() {
        return parts.toArray(Part[]::new);
    }
}