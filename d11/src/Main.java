import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GalaxyMap sample1 = getGalaxyMap("sample.txt", 2);
        System.out.println(sample1.shortestDistanceSums());
        GalaxyMap input = getGalaxyMap("input.txt", 2);
        System.out.println(input.shortestDistanceSums());

        GalaxyMap sample1p2 = getGalaxyMap("sample.txt", 1000000);
        System.out.println(sample1p2.shortestDistanceSums());
        GalaxyMap inputp2 = getGalaxyMap("input.txt", 1000000);
        System.out.println(inputp2.shortestDistanceSums());
    }

    private static GalaxyMap getGalaxyMap(String filename, int expansion) throws IOException {
        List<String> strings = Files.readAllLines(Path.of(filename));
        return new GalaxyMap(strings, expansion);
    }
}