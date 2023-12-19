public record Range(int min, int max) {
    public long length() {
        return max - min;
    }
}