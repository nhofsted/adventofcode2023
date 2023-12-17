import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class SortedHeapSet<T> {
    private final HashMap<T, Integer> position = new HashMap<>();
    private final ArrayList<T> heap = new ArrayList<>();
    private final Comparator<T> comparator;

    public SortedHeapSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T add(T element) {
        if (position.containsKey(element)) return update(element);

        heap.add(element);
        int current = heap.size() - 1;
        position.put(element, current);

        while (comparator.compare(heap.get(current), heap.get(parent(current))) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        return element;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public T pollFirst() {
        T popped = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            heap.set(0, last);
            position.put(last, 0);
            minHeapify(0);
        }
        position.remove(popped);
        return popped;
    }

    private T update(T element) {
        T removed = remove(element);
        add(element);
        return removed;
    }

    private T remove(T element) {
        Integer pos = position.get(element);
        if (pos == null) return null;
        position.remove(element);
        T end = heap.remove(heap.size() - 1);
        if (pos == heap.size()) return end;
        T removed = heap.set(pos, end);
        position.put(end, pos);
        minHeapify(pos);
        return removed;
    }

    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    private int rightChild(int pos) {
        return 2 * (pos + 1);
    }

    private boolean isLeaf(int pos) {
        return 2 * pos + 1 >= heap.size();
    }

    private void swap(int posA, int posB) {
        T a = heap.get(posA);
        T b = heap.get(posB);
        heap.set(posA, b);
        position.put(b, posA);
        heap.set(posB, a);
        position.put(a, posB);
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int swapPos;
            if (rightChild(pos) < heap.size())
                swapPos = comparator.compare(heap.get(leftChild(pos)), heap.get(rightChild(pos))) < 0 ? leftChild(pos) : rightChild(pos);
            else
                swapPos = leftChild(pos);

            if (comparator.compare(heap.get(pos), heap.get(swapPos)) > 0) {
                swap(pos, swapPos);
                minHeapify(swapPos);
            }
        }
    }
}