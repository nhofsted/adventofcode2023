import java.util.Arrays;
import java.util.stream.IntStream;

public class JokerHand extends Hand {

    public JokerHand(String cards, int bid) {
        super(cards, bid);
    }

    @Override
    protected int getType(int[] charCards) {
        return IntStream.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
                .boxed()
                .map(i -> replaceJokers(charCards, i))
                .map(super::getType)
                .reduce(Integer::max)
                .orElse(0);
    }

    private int[] replaceJokers(int[] charCards, int replacement) {
        int[] retVal = Arrays.copyOf(charCards, charCards.length);
        IntStream.range(0, retVal.length)
                .forEach(i -> retVal[i] = (retVal[i] == 'J') ? replacement : retVal[i]);
        return retVal;
    }

    @Override
    protected int cardNumber(int charCard) {
        return switch (charCard) {
            case 'J' -> 0;
            case '2' -> 1;
            case '3' -> 2;
            case '4' -> 3;
            case '5' -> 4;
            case '6' -> 5;
            case '7' -> 6;
            case '8' -> 7;
            case '9' -> 8;
            case 'T' -> 9;
            case 'Q' -> 10;
            case 'K' -> 11;
            case 'A' -> 12;
            default -> throw new RuntimeException("Unexpected card " + charCard);
        };
    }
}