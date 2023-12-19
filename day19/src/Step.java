public interface Step {
    String evaluate(Part part);

    PartRangeResult evaluate(PartRange partRange);

    record PartRangeResult(PartRange matched, String targetMatched, PartRange unmatched) {
    }
}