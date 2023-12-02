import java.util.function.IntBinaryOperator;

public class ArrayUtil {

    protected static int[] combineArrays(int[] a1, int[] a2, IntBinaryOperator f) {
        int[] r = new int[a1.length];
        for (int i = 0; i < a1.length; ++i) {
            r[i] = f.applyAsInt(a1[i], a2[i]);
        }
        return r;
    }
}
