import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortSystem {
    private final static Pattern workflowPattern = Pattern.compile("([a-z]+)\\{([^}]*)}");
    private final static Pattern rulePattern = Pattern.compile("(([xmas])([><])([0-9]+):)?(A|R|[a-z]+)");
    HashMap<String, Step[]> workflows;

    private SortSystem(HashMap<String, Step[]> workflows) {
        this.workflows = workflows;
    }

    public static SortSystem parse(BufferedReader in) throws IOException {
        HashMap<String, Step[]> workflows = new HashMap<>();

        String workflow;
        while (!(workflow = in.readLine()).isEmpty()) {
            Matcher matchedWorkflow = Parser.getMatchedMatcher(workflowPattern, workflow);
            Step[] rules = Arrays.stream(matchedWorkflow.group(2).split(","))
                    .map(rule -> Parser.getMatchedMatcher(rulePattern, rule))
                    .map(matchedRule -> {
                        if (matchedRule.group(1) == null) return (Step) new UnconditionalStep(matchedRule.group(5));
                        return new RuleStep(matchedRule.group(2).charAt(0), RuleStep.Operation.forChar(matchedRule.group(3).charAt(0)), Integer.parseInt(matchedRule.group(4)), matchedRule.group(5));
                    })
                    .toArray(Step[]::new);
            workflows.put(matchedWorkflow.group(1), rules);
        }

        return new SortSystem(workflows);
    }

    long process(Part part) {
        String workflow = "in";
        workflow:
        while (true) {
            Step[] steps = workflows.get(workflow);
            for (Step step : steps) {
                String target = step.evaluate(part);
                if (target != null) {
                    switch (target) {
                        case "A":
                            return part.rating();
                        case "R":
                            return 0;
                        default: {
                            workflow = target;
                            continue workflow;
                        }
                    }
                }
            }
        }
    }

    public long acceptedCombinations(PartRange partRange) {
        return acceptedCombinations("in", partRange).stream()
                .map(PartRange::combinations)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private ArrayList<PartRange> acceptedCombinations(String workflow, PartRange partRange) {
        ArrayList<PartRange> result = new ArrayList<>();
        Step[] steps = workflows.get(workflow);
        PartRange toEvaluate = partRange;
        for (Step step : steps) {
            Step.PartRangeResult evaluation = step.evaluate(toEvaluate);
            String target = evaluation.targetMatched();
            PartRange matched = evaluation.matched();
            if (matched != null) {
                switch (target) {
                    case "A":
                        result.add(matched);
                    case "R":
                        break;
                    default: {
                        result.addAll(acceptedCombinations(target, matched));
                    }
                }
            }
            toEvaluate = evaluation.unmatched();
            if (toEvaluate == null) {
                return result;
            }
        }
        return result;
    }
}