public enum Card {
    JOKER('J'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    private final char symbol;

    Card(char symbol) {
        this.symbol = symbol;
    }

    public static Card fromSymbol(char symbol, boolean jokers) {
        if (symbol == 'J') {
            return jokers ? JOKER : JACK;
        }
        for (Card card : Card.values()) {
            if (card.symbol == symbol) {
                return card;
            }
        }
        throw new IllegalArgumentException(String.valueOf(symbol));
    }

    public char getSymbol() {
        return symbol;
    }
}
