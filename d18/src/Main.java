import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Lagoon sample = part1("sample.txt");
        Lagoon input = part1("input.txt");
        System.out.println(sample.calculateArea());
        System.out.println(input.calculateArea());

        Lagoon sampleP2 = part2("sample.txt");
        Lagoon inputP2 = part2("input.txt");
        System.out.println(sampleP2.calculateArea());
        System.out.println(inputP2.calculateArea());
    }

    public static Lagoon part1(String file) throws IOException {
        try (var lines = Files.lines(Path.of(file))) {
            return new Lagoon(lines);
        }
    }

    public static Lagoon part2(String file) throws IOException {
        try (var lines = Files.lines(Path.of(file))) {
            return Lagoon.fromColorCodes(lines);
        }
    }
}