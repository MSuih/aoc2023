import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Contraption sample1 = new Contraption(Files.readAllLines(Path.of("sample1.txt")));
        Contraption input = new Contraption(Files.readAllLines(Path.of("input.txt")));

        System.out.println("Squares energized in sample: " + sample1.simulateOneBeam());
        System.out.println("Squares energized in input: " + input.simulateOneBeam());

        System.out.println("Most energy in sample: " + sample1.mostEnergyPossible());
        System.out.println("Most energy in input: " + input.mostEnergyPossible());
    }
}