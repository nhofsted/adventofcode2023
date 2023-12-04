import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Ticket {
    private final HashSet<Integer> winning;
    private final HashSet<Integer> numbers;

    private Ticket(List<Integer> winning, List<Integer> numbers) {
        this.winning = new HashSet<>(winning);
        this.numbers = new HashSet<>(numbers);
    }

    public static Ticket parse(String line) {
        String[] card = line.split(":");
        String[] numbers = card[1].split("\\|");
        return new Ticket(parseNumbers(numbers[0]), parseNumbers(numbers[1]));
    }

    private static List<Integer> parseNumbers(String number) {
        return Arrays.stream(number.split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public int matches() {
        return (int) winning.stream()
                .map(numbers::contains)
                .filter(b -> b)
                .count();
    }

    public int points() {
        long matches = matches();
        if (matches > 0) {
            return (int) Math.pow(2, matches - 1);
        } else {
            return 0;
        }
    }
}