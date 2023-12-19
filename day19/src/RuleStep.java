import java.util.Arrays;

record RuleStep(char category, Operation op, int compare, String target) implements Step {

    @Override
    public String evaluate(Part part) {
        int left = part.getCategory(category);
        if (switch (op) {
            case LESS_THAN -> left < compare;
            case GREATER_THAN -> left > compare;
        }) return target;

        return null;
    }

    @Override
    public PartRangeResult evaluate(PartRange partRange) {
        PartRange[] parts;
        switch (op) {
            case LESS_THAN -> {
                parts = partRange.split(category, compare);
                return new PartRangeResult(parts[0], target, parts[1]);
            }
            case GREATER_THAN -> {
                parts = partRange.split(category, compare + 1);
                return new PartRangeResult(parts[1], target, parts[0]);
            }
        }
        return null;
    }

    enum Operation {
        LESS_THAN('<'), GREATER_THAN('>');

        private final char op;

        Operation(char op) {
            this.op = op;
        }

        public static Operation forChar(char op) {
            return Arrays.stream(Operation.values()).filter(o -> o.op == op).findAny().orElseThrow(IllegalArgumentException::new);
        }
    }
}