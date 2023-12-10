import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<History> sample = parse("sample.txt");
        part1(sample);
        part2(sample);
        List<History> input = parse("input.txt");
        part1(input);
        part2(input);
    }

    private static List<History> parse(String file) throws IOException {
        try (var stream = Files.lines(Path.of(file))) {
            return stream.map(History::new).toList();
        }
    }

    private static void part1(List<History> histories) {
        long sum = 0;
        for (History history : histories) {
            List<History> differences = new ArrayList<>();

            while (!history.allZeroes()) {
                differences.addFirst(history);
                history = history.differences();
            }

            long l = 0;
            for (History difference : differences) {
                l = difference.extrapolate(l);
            }
            sum += l;
        }
        System.out.println("Sum of extrapolations is " + sum);
    }

    private static void part2(List<History> histories) {
        long sum = 0;
        for (History history : histories) {
            List<History> differences = new ArrayList<>();

            while (!history.allZeroes()) {
                differences.addFirst(history);
                history = history.differences();
            }

            long l = 0;
            for (History difference : differences) {
                l = difference.extrapolateBackwards(l);
            }
            sum += l;
        }
        System.out.println("Sum of extrapolations is " + sum);
    }
}