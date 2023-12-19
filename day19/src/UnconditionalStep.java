public record UnconditionalStep(String action) implements Step {

    @Override
    public String evaluate(Part part) {
        return action;
    }

    @Override
    public PartRangeResult evaluate(PartRange partRange) {
        return new PartRangeResult(partRange, action, null);
    }
}