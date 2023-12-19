public record PartRange(Range x, Range m, Range a, Range s) {

    public long combinations() {
        return x.length() * m.length() * a.length() * s.length();
    }

    public PartRange[] split(char category, int compare) {
        Range toSplit = switch (category) {
            case 'x' -> x;
            case 'm' -> m;
            case 'a' -> a;
            case 's' -> s;
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };

        if (compare < toSplit.min()) return new PartRange[]{null, this};
        else if (compare >= toSplit.max()) return new PartRange[]{this, null};

        Range r1 = new Range(toSplit.min(), compare);
        Range r2 = new Range(compare, toSplit.max());

        return switch (category) {
            case 'x' -> new PartRange[]{new PartRange(r1, m, a, s), new PartRange(r2, m, a, s)};
            case 'm' -> new PartRange[]{new PartRange(x, r1, a, s), new PartRange(x, r2, a, s)};
            case 'a' -> new PartRange[]{new PartRange(x, m, r1, s), new PartRange(x, m, r2, s)};
            case 's' -> new PartRange[]{new PartRange(x, m, a, r1), new PartRange(x, m, a, r2)};
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }
}
