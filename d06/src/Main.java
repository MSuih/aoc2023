import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Race> sampleRaces = parseRaces("sample.txt");
        List<Race> inputRaces = parseRaces("input.txt");
        long p1s = part1(sampleRaces);
        long p1i = part1(inputRaces);

        Race sampleRace = parseRace("sample.txt");
        Race inputRace = parseRace("input.txt");
        long p2s = part2(sampleRace);
        long p2i = part2(inputRace);

        System.out.println(p1s + " | " + p1i);
        System.out.println(p2s + " | " + p2i);
    }

    private static List<Race> parseRaces(String file) throws IOException {
        List<String> sample = Files.readAllLines(Path.of(file));
        String[] times = sample.get(0).substring(10).trim().split(" +");
        String[] distances = sample.get(1).substring(10).trim().split(" +");
        if (times.length != distances.length) {
            throw new IllegalStateException();
        }
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i])));
        }
        return races;
    }

    private static Race parseRace(String file) throws IOException {
        StringBuffer time = new StringBuffer();
        StringBuffer distance = new StringBuffer();
        try (Reader reader = new BufferedReader(new FileReader(Path.of(file).toFile()))) {
            StringBuffer buffer = time;
            for (int c = reader.read(); c > 0; c = reader.read()) {
                if (c >= '0' && c <= '9') {
                    buffer.append((char) c);
                } else if (c == '\n') {
                    buffer = distance;
                }
            }
        }
        return new Race(Long.parseLong(time.toString()), Long.parseLong(distance.toString()));
    }

    private static long part1(List<Race> races) {
        return races.stream()
                .mapToLong(Main::part2)
                .reduce(1, (l1, l2) -> l1 * l2);
    }

    private static long part2(Race race) {
        return LongStream.range(1, race.duration())
                .map(pushTime -> (race.duration() - pushTime) * pushTime)
                .filter(distance -> distance > race.record())
                .count();
    }
}