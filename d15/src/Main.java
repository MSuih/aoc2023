import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        int sample1 = Arrays.stream(Files.readString(Path.of("sample1.txt")).split(",")).mapToInt(LensBoxes::hash).sum();
        int sample2 = Arrays.stream(Files.readString(Path.of("sample2.txt")).split(",")).mapToInt(LensBoxes::hash).sum();
        int input = Arrays.stream(Files.readString(Path.of("input.txt")).split(",")).mapToInt(LensBoxes::hash).sum();
        System.out.println("Result of sample 1 is " + sample1);
        System.out.println("Result of sample 2 is " + sample2);
        System.out.println("Result of input is " + input);
    }

    private static void part2() throws IOException {
        System.out.println("Focusing power in sample is " + processCommands("sample2.txt"));
        System.out.println("Focusing power in input is " + processCommands("input.txt"));
    }

    private static long processCommands(String file) throws IOException {
        LensBoxes boxes = new LensBoxes();
        for (String command : Files.readString(Path.of(file)).split(",")) {
            if (command.endsWith("-")) {
                String label = command.substring(0, command.length() - 1);
                boxes.take(label);
            } else {
                String label = command.substring(0, command.length() - 2);
                int value = command.charAt(command.length() - 1) - '0';
                boxes.add(label, value);
            }
        }
        return boxes.calculateFocusValue();
    }

}