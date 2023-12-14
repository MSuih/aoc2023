import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pattern {
    private final boolean[][] values;

    private final int row, column;

    public Pattern(List<String> lines) {
        values = new boolean[lines.size()][];
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            boolean[] value = new boolean[line.length()];
            char[] chars = line.toCharArray();
            for (int column = 0; column < chars.length; column++) {
                value[column] = chars[column] == '#';
            }
            values[row] = value;
        }
        row = -1;
        column = -1;
    }

    private Pattern(boolean[][] values, int row, int column) {
        this.values = values;
        this.row = row;
        this.column = column;
    }

    private Pattern copy(int row, int column) {
        boolean[][] values = new boolean[this.values.length][this.values[0].length];
        for (int i = 0; i < values.length; i++) {
            System.arraycopy(this.values[i], 0, values[i], 0, this.values[i].length);
        }
        return new Pattern(values, row, column);
    }

    public int horizontalReflection() {
        outer:
        for (int row = 0; row < values.length - 1; row++) {
            if (row == this.row) {
                continue;
            }
            for (int i = 0; i < Math.min(row + 1, values.length - row - 1); i++) {
                if (!Arrays.equals(values[row - i], values[row + i + 1])) {
                    continue outer;
                }
            }
            return row + 1;
        }
        return -1;
    }

    public int verticalReflection() {
        outer:
        for (int column = 0; column < values[0].length - 1; column++) {
            if (column == this.column) {
                continue;
            }
            for (int i = 0; i < Math.min(column + 1, values[0].length - column - 1); i++) {
                for (boolean[] value : values) {
                    if (value[column - i] != value[column + i + 1]) {
                        continue outer;
                    }
                }
            }
            return column + 1;
        }
        return -1;
    }

    public Stream<Pattern> smudges() {
        int reflectionRow = this.horizontalReflection() - 1;
        int reflectionColumn = this.verticalReflection() - 1;
        return IntStream.range(0, values.length)
                .boxed()
                .flatMap(row -> IntStream.range(0, values[row].length)
                        .mapToObj(column -> {
                            Pattern copy = this.copy(reflectionRow, reflectionColumn);
                            copy.values[row][column] = !copy.values[row][column];
                            return copy;
                        }));
    }
}
