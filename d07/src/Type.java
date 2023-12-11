import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public enum Type {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_KIND, FULL_HOUSE, FOUR_OF_KIND, FIVE_OF_KIND;

    private static Type fromNumber(int i) {
        return switch (i) {
            case 1 -> HIGH_CARD;
            case 2 -> ONE_PAIR;
            case 3 -> THREE_OF_KIND;
            case 4 -> FOUR_OF_KIND;
            case 5 -> FIVE_OF_KIND;
            default -> throw new IllegalArgumentException("Unexpected value: " + i);
        };
    }

    public static Type fromCards(Card[] cards) {
        Map<Type, Integer> foundTypes = new EnumMap<>(Type.class);
        boolean[] skip = new boolean[5];
        int jokerCount = 0;
        for (int i = 0; i < cards.length; i++) {
            Card first = cards[i];
            int same = 1;
            if (first == Card.JOKER) {
                jokerCount++;
                continue;
            }
            if (skip[i]) {
                continue;
            }
            for (int j = i + 1; j < cards.length; j++) {
                if (skip[i]) {
                    continue;
                }
                Card second = cards[j];
                if (first == second) {
                    skip[j] = true;
                    same++;
                } else if (second == Card.JOKER) {
                    skip[j] = true;
                }
            }
            Type type = fromNumber(same);
            foundTypes.put(type, foundTypes.getOrDefault(type, 0) + 1);
        }
        Type type = foundTypes.keySet().stream()
                .max(Comparator.naturalOrder())
                .orElse(FIVE_OF_KIND);

        if (jokerCount > 0) {
            if (type == FOUR_OF_KIND) {
                return FIVE_OF_KIND;
            }
            if (type == THREE_OF_KIND) {
                return jokerCount > 1 ? FIVE_OF_KIND : FOUR_OF_KIND;
            }
            if (type == ONE_PAIR && foundTypes.get(ONE_PAIR) > 1) {
                return FULL_HOUSE;
            }
            if (type == ONE_PAIR) {
                return switch (jokerCount) {
                    case 1 -> THREE_OF_KIND;
                    case 2 -> FOUR_OF_KIND;
                    case 3 -> FIVE_OF_KIND;
                    default -> throw new IllegalStateException();
                };
            }
            return switch (jokerCount) {
                case 1 -> ONE_PAIR;
                case 2 -> THREE_OF_KIND;
                case 3 -> FOUR_OF_KIND;
                case 4, 5 -> FIVE_OF_KIND;
                default -> throw new IllegalStateException();
            };
        } else {
            if (foundTypes.containsKey(THREE_OF_KIND) && foundTypes.containsKey(ONE_PAIR)) {
                return FULL_HOUSE;
            }
            if (foundTypes.containsKey(ONE_PAIR) && foundTypes.get(ONE_PAIR) == 2) {
                return TWO_PAIR;
            }
        }
        return type;
    }
}
