package math;

public class MathUtil {
    public static long gcd(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return number1 + number2;
        } else {
            long absNumber1 = Math.abs(number1);
            long absNumber2 = Math.abs(number2);
            long largerValue = Math.max(absNumber1, absNumber2);
            long smallerValue = Math.min(absNumber1, absNumber2);
            return gcd(largerValue % smallerValue, smallerValue);
        }
    }

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0)
            return 0;
        else {
            return Math.abs(number1 * number2) / gcd(number1, number2);
        }
    }
}
