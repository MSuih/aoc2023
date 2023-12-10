import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Card> sample = Files.readAllLines(Path.of("sample.txt"))
                .stream()
                .map(Card::new)
                .toList();
        List<Card> input = Files.readAllLines(Path.of("input.txt"))
                .stream()
                .map(Card::new)
                .toList();

        System.out.println(part1(sample));
        System.out.println(part1(input));

        System.out.println(part2(sample));
        System.out.println(part2(input));
    }

    public static long part1(List<Card> cards) {
        return cards.stream()
                .mapToLong(Card::getWins)
                .filter(l -> l > 0)
                .map(Main::points)
                .sum();
    }

    private static long points(long input) {
        long sum = 1;
        for (long i = 2; i <= input; i++) {
            sum *= 2;
        }
        return sum;
    }

public static long part2(List<Card> cards) {
    List<Card> totalCards = new ArrayList<>(cards);
    for (int i = 0; i < totalCards.size(); i++) {
        Card card = totalCards.get(i);
        long newCards = card.getWins();
        for (int j = 0; j < newCards; j++) {
            totalCards.add(cards.get(card.getId() + j));
        }
    }
    return totalCards.size();
}
}