import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        int[] sample = descramble(Files.readString(Path.of("sample.txt")));
        System.out.println(Arrays.stream(sample).sum());
        int[] sample2 = descramble(Files.readString(Path.of("sample2.txt")));
        System.out.println(Arrays.stream(sample2).sum());
        int[] sample3 = descramble(Files.readString(Path.of("sample3.txt")));
        System.out.println(Arrays.toString(sample3));
        System.out.println(Arrays.stream(sample3).sum());
        int[] sample4 = descramble(Files.readString(Path.of("sample4.txt")));
        System.out.println(Arrays.toString(sample4));
        System.out.println(Arrays.stream(sample4).sum());
        int[] sample5 = descramble(Files.readString(Path.of("sample5.txt")));
        System.out.println(Arrays.toString(sample5));
        System.out.println(Arrays.stream(sample5).sum());
        int[] sample6 = descramble(Files.readString(Path.of("sample6.txt")));
        System.out.println(Arrays.toString(sample6));
        System.out.println(Arrays.stream(sample6).sum());
        int[] sample7 = descramble(Files.readString(Path.of("sample7.txt")));
        System.out.println(Arrays.toString(sample7));
        System.out.println(Arrays.stream(sample7).sum());


        int[] input = descramble(Files.readString(Path.of("input.txt")));
        System.out.println(Arrays.stream(input).sum());
    }

    public static int[] descramble(String lines) {
        CalibrationDescrambler descrambler = new CalibrationDescrambler();
        return descrambler.descramble(lines);
    }
}