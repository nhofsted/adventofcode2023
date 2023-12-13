import java.util.function.IntBinaryOperator;

public class ArrayUtil {

    public static int[] combineArrays(int[] a1, int[] a2, IntBinaryOperator f) {
        int[] r = new int[a1.length];
        for (int i = 0; i < a1.length; ++i) {
            r[i] = f.applyAsInt(a1[i], a2[i]);
        }
        return r;
    }

    public static void reverseCharArray(char[] array) {
        int length = array.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = temp;
        }
    }

    public static void reverseIntArray(int[] array) {
        int length = array.length;
        for (int i = 0; i < length / 2; i++) {
            int temp = array[i];
            array[i] = array[length - i - 1];
            array[length - i - 1] = temp;
        }
    }
}
