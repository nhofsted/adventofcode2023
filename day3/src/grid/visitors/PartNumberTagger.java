package grid.visitors;

import grid.Grid;

public class PartNumberTagger extends AbstractGridVisitor {
    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public int visit(int[] data) {
        // Implement this without branches for speed and fun
        int maskedHigh = (data[0] & Grid.PART | data[2] & Grid.PART);       // Are we adjacent to an engine part?
        return data[1] | maskedHigh;                                        // If adjacent, tag us.
    }
}