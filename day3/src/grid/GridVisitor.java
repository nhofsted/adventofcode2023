package grid;

public interface GridVisitor {
    int getSize();

    int visit(int[] data);

    void end();
}