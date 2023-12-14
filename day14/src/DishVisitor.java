public abstract class DishVisitor {
    int nextFree = -1;

    void visit(int pos, char c, int direction) {
        switch (c) {
            case 'O':
                if (nextFree >= 0) {
                    setValue(nextFree, 'O');
                    setValue(pos, '.');
                    nextFree += direction;
                }
                break;
            case '#':
                nextFree = -1;
                break;
            case '.':
                if (nextFree < 0) {
                    nextFree = pos;
                }
                break;
        }
    }

    abstract void setValue(int pos, char c);
}