import java.util.Arrays;

public class History {
    private final long[] values;

    public History(String line) {
        values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
    }

    private History(long[] values) {
        this.values = values;
    }

    public History differences() {
        long[] diff = new long[values.length - 1];
        for (int i = 0; i < values.length - 1; i++) {
            diff[i] = values[i + 1] - values[i];
        }
        return new History(diff);
    }

    public boolean allZeroes() {
        return Arrays.stream(values).allMatch(v -> v == 0);
    }

    public long extrapolate(long toAdd) {
        return values[values.length - 1] + toAdd;
    }

    public long extrapolateBackwards(long toAdd) {
        return values[0] - toAdd;
    }
}
