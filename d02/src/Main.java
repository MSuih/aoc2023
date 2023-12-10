import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String sample = Files.readString(Path.of("sample1.txt"));
        String input = Files.readString(Path.of("input.txt"));

        int redLimit = 12;
        int greenLimit = 13;
        int blueLimit = 14;

        int samplePossibleGames = sample.lines()
                .map(Game::new)
                .filter(game -> game.underLimits(redLimit, greenLimit, blueLimit))
                .mapToInt(Game::getIndex)
                .sum();
        System.out.println("Part 1 example is " + samplePossibleGames);

        int inputSum = input.lines()
                .map(Game::new)
                .filter(game -> game.underLimits(redLimit, greenLimit, blueLimit))
                .mapToInt(Game::getIndex)
                .sum();
        System.out.println("Answer to Part 1 is " + inputSum);


        int sampleCubeSum = sample.lines()
                .map(Game::new)
                .mapToInt(game -> game.maxRed() * game.maxGreen() * game.maxBlue())
                .sum();
        System.out.println("Part 2 example is " + sampleCubeSum);

        int inputCubeSum = input.lines()
                .map(Game::new)
                .mapToInt(game -> game.maxRed() * game.maxGreen() * game.maxBlue())
                .sum();
        System.out.println("Answer to Part 2 is " + inputCubeSum);
    }
}