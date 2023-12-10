import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SequencedCollection;
import java.util.TreeSet;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> sample = Files.readAllLines(Path.of("sample.txt"));
        List<String> input = Files.readAllLines(Path.of("input.txt"));

        part1(sample);
        part1(input);

        part2(sample);
        part2(input);
    }

    private static void part1(List<String> sample) {
        long[] seeds =
                Arrays.stream(sample.getFirst()
                        .substring("seeds: ".length())
                        .split(" "))
                .mapToLong(Long::parseLong)
                .toArray();
        Mappings mappings = new Mappings(sample.subList(2, sample.size()));

        long lowestLocation = Arrays.stream(seeds)
                .map(mappings::map)
                .min()
                .orElseThrow();
        System.out.println("Lowest location is " + lowestLocation);
    }

    private record SeedRange(long start, long len) {}

    private static void part2(List<String> sample) {
        String[] split = sample.get(0)
                .substring("seeds: ".length())
                .split(" ");
        List<SeedRange> seedRanges = new ArrayList<>();
        Mappings mappings = new Mappings(sample.subList(2, sample.size()));
        for (int i = 0; i < split.length; i += 2) {
            seedRanges.add(new SeedRange(Long.parseLong(split[i]), Long.parseLong(split[i + 1])));
        }

        SequencedCollection<Long> results = new TreeSet<>();
        for (SeedRange seedRange : seedRanges) {
            long l = LongStream.range(seedRange.start, seedRange.start + seedRange.len)
                    .parallel()
                    .map(mappings::map)
                    .min()
                    .orElseThrow();
            results.add(l);
            System.out.println("Processed one range");
        }
        System.out.println("Part 2 answer is " + results.getFirst());
    }
}