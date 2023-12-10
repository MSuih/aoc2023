import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Card {
    private final int id;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> scratchedNumbers;

    public Card(String s) {
        String[] split = s.split(":");
        id = Integer.parseInt(split[0].substring(split[0].lastIndexOf(' ') + 1));
        String[] numbers = split[1].split("\\|");
        winningNumbers = getNumbers(numbers[0]);
        scratchedNumbers = getNumbers(numbers[1]);
    }

    private Set<Integer> getNumbers(String s) {
        return Arrays.stream(s.trim().split(" +"))
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
    }

    public int getId() {
        return id;
    }

    public long getWins() {
        return scratchedNumbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }
}
