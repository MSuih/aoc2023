import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        part1("sample1.txt");
        part1("sample2.txt");
        part1("input.txt");
        //part2("sample3.txt");
        part2("input.txt");
    }

    private static void part1(String file) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file));
        String moves = lines.getFirst();
        DesertMap map = new DesertMap(lines.subList(2, lines.size()));

        System.out.println("Moves it takes on map " + map.stepsToEnd(moves));
    }

    private static void part2(String file) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file));
        String moves = lines.getFirst();
        DesertMap map = new DesertMap(lines.subList(2, lines.size()));

        System.out.println("Moves it takes on map " + map.fromAtoZ(moves));
    }
}