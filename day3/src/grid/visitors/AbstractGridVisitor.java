package grid.visitors;

import grid.GridVisitor;

public abstract class AbstractGridVisitor implements GridVisitor {
    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int visit(int[] data) {
        return data[getSize() / 2];
    }

    @Override
    public void end() {
    }
}