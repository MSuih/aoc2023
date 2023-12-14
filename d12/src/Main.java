import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<SpringRow> sample1 = parse("sample1.txt");
        List<SpringRow> sample2 = parse("sample2.txt");
        List<SpringRow> input = parse("input.txt");
        
        System.out.println("Answer for part 1: " + sample1.stream().mapToLong(SpringRow::waysToPosition).sum());
        System.out.println("Answer for part 1: " + sample2.stream().mapToLong(SpringRow::waysToPosition).sum());
        System.out.println("Answer for part 1: " + input.stream().mapToLong(SpringRow::waysToPosition).sum());

        System.out.println("Answer for part 2: " + sample1.stream().map(SpringRow::unfold).mapToLong(SpringRow::waysToPosition).sum());
        System.out.println("Answer for part 2: " + input.stream().map(SpringRow::unfold).mapToLong(SpringRow::waysToPosition).sum());

    }

    private static List<SpringRow> parse(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        return lines.stream().map(SpringRow::new).toList();
    }
}