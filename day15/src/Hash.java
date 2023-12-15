public class Hash {
    int value = 0;

    public static int digest(String input) {
        Hash h = new Hash();
        input.chars().forEach(h::add);
        return h.getDigest();
    }

    void add(int c) {
        value += c;
        value *= 17;
        value %= 256;
    }

    public int getDigest() {
        return value;
    }
}