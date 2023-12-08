import java.util.Arrays;

public class Hand implements Comparable<Hand> {
    private final int cards;
    private final int bid;

    public Hand(String cards, int bid) {
        int[] charCards = cards.chars().toArray();
        this.cards = Arrays.stream(charCards)
                .reduce(0, (intCards, card) -> (intCards << 4) + cardNumber(card))
                + (getType(charCards) << 20);

        this.bid = bid;
    }

    public static boolean isFiveOfAKind(int[] c) {
        return c[0] == c[1] &&
                c[0] == c[2] &&
                c[0] == c[3] &&
                c[0] == c[4];
    }

    public static boolean isFourOfAKind(int[] c) {
        return c[1] == c[2] &&
                c[1] == c[3] &&
                (c[1] == c[0] || c[1] == c[4]);
    }

    public static boolean isFullHouse(int[] c) {
        return c[0] == c[1] &&
                c[3] == c[4] &&
                (c[2] == c[0] || c[2] == c[4]);
    }

    public static boolean isThreeOfAKind(int[] c) {
        return c[0] == c[1] && c[0] == c[2] ||
                c[0] == c[1] && c[0] == c[3] ||
                c[0] == c[1] && c[0] == c[4] ||
                c[0] == c[2] && c[0] == c[3] ||
                c[0] == c[2] && c[0] == c[4] ||
                c[0] == c[3] && c[0] == c[4] ||
                c[1] == c[2] && c[1] == c[3] ||
                c[1] == c[2] && c[1] == c[4] ||
                c[1] == c[3] && c[1] == c[4] ||
                c[2] == c[3] && c[2] == c[4];
    }

    public static boolean isTwoPair(int[] c) {
        return (c[0] == c[1] && (c[2] == c[3] || c[3] == c[4])) ||
                (c[1] == c[2] && c[3] == c[4]);
    }

    public static boolean isOnePair(int[] c) {
        return c[0] == c[1] ||
                c[1] == c[2] ||
                c[2] == c[3] ||
                c[3] == c[4];
    }

    protected int cardNumber(int charCard) {
        return switch (charCard) {
            case '2' -> 0;
            case '3' -> 1;
            case '4' -> 2;
            case '5' -> 3;
            case '6' -> 4;
            case '7' -> 5;
            case '8' -> 6;
            case '9' -> 7;
            case 'T' -> 8;
            case 'J' -> 9;
            case 'Q' -> 10;
            case 'K' -> 11;
            case 'A' -> 12;
            default -> throw new RuntimeException("Unexpected card " + charCard);
        };
    }

    protected int getType(int[] charCards) {
        Arrays.sort(charCards);
        if (isFiveOfAKind(charCards)) return 6;
        else if (isFourOfAKind(charCards)) return 5;
        else if (isFullHouse(charCards)) return 4;
        else if (isThreeOfAKind(charCards)) return 3;
        else if (isTwoPair(charCards)) return 2;
        else if (isOnePair(charCards)) return 1;
        else return 0;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand o) {
        return cards - o.cards;
    }
}