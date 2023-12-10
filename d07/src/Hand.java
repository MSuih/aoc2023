import java.util.Arrays;

public class Hand implements Comparable<Hand> {
    private final Card[] cards = new Card[5];
    private final Type type;

    public Hand(char[] symbols, boolean jokers) {
        for (int i = 0; i < symbols.length; i++) {
            cards[i] = Card.fromSymbol(symbols[i], jokers);
        }
        type = Type.fromCards(cards);
    }

    @Override
    public int compareTo(Hand o) {
        int hand = this.type.compareTo(o.type);
        if (hand != 0) {
            return hand;
        }
        for (int i = 0; i < cards.length; i++) {
            int card = this.cards[i].compareTo(o.cards[i]);
            if (card != 0) {
                return card;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Cards{" + Arrays.stream(cards).mapToInt(Card::getSymbol).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append) +  "}";
    }
}
