import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> sample = Files.readAllLines(Path.of("sample.txt"));
        List<String> input = Files.readAllLines(Path.of("input.txt"));

        Schematic sampleSchematic = new Schematic(sample);
        Schematic inputSchematic = new Schematic(input);

        System.out.println("Part 1 example is " + sampleSchematic.partNumbers()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());

        System.out.println("Part 1 solution is " + inputSchematic.partNumbers()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());

        System.out.println("Part 2 example is " + sampleSchematic.gearRatios()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());

        System.out.println("Part 2 solution is " + inputSchematic.gearRatios()
                .stream()
                .mapToInt(Integer::intValue)
                .sum());
    }
}