import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Pattern> sample = parse("sample1.txt");
        System.out.println("Initial reflections: " + part1(sample));
        List<Pattern> input = parse("input.txt");
        System.out.println("Initial reflections: " + part1(input));

        System.out.println("Reflections after smudge fix: " + part2(sample));
        System.out.println("Reflections after smudge fix: " + part2(input));

    }

    private static long part1(List<Pattern> parse) {
        return parse.stream()
                .mapToLong(p -> {
                    int i = p.horizontalReflection() * 100;
                    if (i < 0) {
                        i = p.verticalReflection();
                    }
                    return i;
                }).sum();
    }

    private static long part2(List<Pattern> parse) {
        return parse.stream()
                .flatMap(p ->
                    p.smudges()
                            .map(s -> {
                                int i = s.horizontalReflection() * 100;
                                if (i < 0) {
                                    i = s.verticalReflection();
                                }
                                return i;
                            })
                            .filter(i -> i > 0)
                            .findFirst().stream()
                ).mapToLong(Integer::longValue).sum();
    }

    private static List<Pattern> parse(String file) throws IOException {
        List<String> strings = Files.readAllLines(Path.of(file));
        int idx = 0;
        List<List<String>> lineGroups = new ArrayList<>();
        for (String string : strings) {
            if (string.isEmpty()) {
                idx++;
                continue;
            } else if (lineGroups.size() <= idx) {
                lineGroups.add(new ArrayList<>());
            }
            lineGroups.get(idx).add(string);
        }
        return lineGroups.stream().map(Pattern::new).toList();
    }
}