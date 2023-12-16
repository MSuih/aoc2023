import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        Platform sample1 = parse("sample1.txt");
        sample1.rollNorth();
        System.out.println("Strain on cables is " + sample1.calculateStrain());
        Platform input = parse("input.txt");
        input.rollNorth();
        System.out.println("Strain on cables is " + input.calculateStrain());
    }

    private static void part2() throws IOException {
        Platform sample1 = parse("sample1.txt");
        Platform input = parse("input.txt");
        cycle(sample1);
        cycle(input);
        System.out.println("Strain on cables is " + sample1.calculateStrain());
        System.out.println("Strain on cables is " + input.calculateStrain());
    }

    private static void cycle(Platform sample1) {
        Set<Platform> previousCycles = new HashSet<>();
        int cycleCount = 0;
        int cycleStart = -1;
        int spinCycles = 1_000_000_000;
        for (int current = 1; current <= spinCycles; current++) {
            sample1.rollNorth();
            sample1.rollWest();
            sample1.rollSouth();
            sample1.rollEast();
            if (cycleCount == 0 && !previousCycles.add(sample1.copy())) {
                // Got back into previous state, this is a cycle
                cycleStart = current;
                previousCycles.clear();
                previousCycles.add(sample1);
                cycleCount++;
            } else if (cycleCount == 1 && !previousCycles.add(sample1.copy())) {
                // Got back into previous state again. Now we can calculate length of cycle and skip next n cycles
                cycleCount++;
                int cycleLength = current - cycleStart;
                while (current < spinCycles - cycleLength) {
                    current += cycleLength;
                }
            }
        }
    }

    public static Platform parse(String file) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file));
        return new Platform(lines);
    }
}