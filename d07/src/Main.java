import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    private record Game(Hand hand, int value) implements Comparable<Game> {
        @Override
        public int compareTo(Game o) {
            return this.hand.compareTo(o.hand);
        }
    }

    public static void main(String[] args) throws IOException {
        List<Game> sample = parse("sample.txt", false);
        List<Game> input = parse("input.txt", false);

        part1(sample);
        part1(input);

        List<Game> samplep2 = parse("sample.txt", true);
        List<Game> inputp2 = parse("input.txt", true);

        part1(samplep2);
        part1(inputp2);

    }

    private static void part1(List<Game> games) {
        long sum = 0;
        for (int i = 0; i < games.size(); i++) {
            sum += (i + 1L) * games.get(i).value;
        }
        System.out.println("Games won you " + sum);
    }

    private static List<Game> parse(String file, boolean jokers) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file));
        List<Game> games = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            Hand hand = new Hand(split[0].toCharArray(), jokers);
            int value = Integer.parseInt(split[1]);
            games.add(new Game(hand, value));
        }
        games.sort(Comparator.naturalOrder());
        return games;
    }

}