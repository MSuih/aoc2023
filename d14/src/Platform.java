import java.util.Arrays;
import java.util.List;

public class Platform {

    public enum Rock {
        STATIC, ROLLING;

        public static Rock parse(int c) {
            return switch (c) {
                case '#' -> STATIC;
                case 'O' -> ROLLING;
                case '.' -> null;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    private final Rock[][] rocks;

    public Platform(List<String> lines) {
        rocks = lines.stream()
                .map(line -> line.chars()
                        .mapToObj(Rock::parse)
                        .toArray(Rock[]::new))
                .toArray(Rock[][]::new);
    }

    public Platform(Rock[][] rocks) {
        this.rocks = rocks;
    }

    public Platform copy() {
        Rock[][] newRocks = new Rock[this.rocks.length][];
        for (int i = 0; i < this.rocks.length; i++) {
            newRocks[i] = new Rock[rocks[i].length];
            System.arraycopy(this.rocks[i], 0, newRocks[i], 0, this.rocks.length);
        }
        return new Platform(newRocks);
    }

    public long calculateStrain() {
        long total = 0;
        for (int i = 0; i < rocks.length; i++) {
            long count = Arrays.stream(rocks[i])
                    .filter(r -> r == Rock.ROLLING)
                    .count();
            total += count * (rocks.length - i);
        }
        return total;
    }

    public void rollNorth() {
        for (int row = 1; row < rocks.length; row++) {
            Rock[] rockRow = rocks[row];
            for (int column = 0; column < rockRow.length; column++) {
                Rock rock = rockRow[column];
                if (rock != Rock.ROLLING) {
                    continue;
                }
                int topmostFree = row;
                for (int i = row - 1; i >= 0; i--) {
                    if (rocks[i][column] == null) {
                        topmostFree = i;
                    } else {
                        break;
                    }
                }
                if (topmostFree != row) {
                    rocks[row][column] = null;
                    rocks[topmostFree][column] = rock;
                }
            }
        }
    }

    public void rollWest() {
        for (int column = 1; column < rocks[0].length; column++) {
            for (int row = 0; row < rocks.length; row++) {
                Rock rock = rocks[row][column];
                if (rock != Rock.ROLLING) {
                    continue;
                }
                int leftmostFree = column;
                for (int i = column - 1; i >= 0; i--) {
                    if (rocks[row][i] == null) {
                        leftmostFree = i;
                    } else {
                        break;
                    }
                }
                if (leftmostFree != column) {
                    rocks[row][column] = null;
                    rocks[row][leftmostFree] = rock;
                }
            }
        }
    }

    public void rollSouth() {
        for (int row = rocks.length - 2; row >= 0; row--) {
            Rock[] rockRow = rocks[row];
            for (int column = 0; column < rockRow.length; column++) {
                Rock rock = rockRow[column];
                if (rock != Rock.ROLLING) {
                    continue;
                }
                int bottommostFree = row;
                for (int i = row + 1; i < rocks.length; i++) {
                    if (rocks[i][column] == null) {
                        bottommostFree = i;
                    } else {
                        break;
                    }
                }
                if (bottommostFree != row) {
                    rocks[row][column] = null;
                    rocks[bottommostFree][column] = rock;
                }
            }
        }
    }

    public void rollEast() {
        for (int column = rocks[0].length - 2; column >= 0; column--) {
            for (int row = 0; row < rocks.length; row++) {
                Rock rock = rocks[row][column];
                if (rock != Rock.ROLLING) {
                    continue;
                }
                int rightmostFree = column;
                for (int i = column + 1; i < rocks[0].length; i++) {
                    if (rocks[row][i] == null) {
                        rightmostFree = i;
                    } else {
                        break;
                    }
                }
                if (rightmostFree != column) {
                    rocks[row][column] = null;
                    rocks[row][rightmostFree] = rock;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return Arrays.deepEquals(rocks, platform.rocks);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(rocks);
    }
}
