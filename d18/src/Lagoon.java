import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Lagoon {
    private enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction from(char c) {
            return switch (c) {
                case 'U', '3' -> UP;
                case 'D', '1' -> DOWN;
                case 'L', '2' -> LEFT;
                case 'R', '0' -> RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    private record Dig(Direction direction, int amount) {}

    private record Location(long x, long y) {
        public Location next(Dig dig) {
            return switch (dig.direction) {
                case UP -> new Location(this.x , this.y - dig.amount);
                case DOWN -> new Location(this.x, this.y + dig.amount);
                case LEFT -> new Location(this.x - dig.amount, this.y);
                case RIGHT -> new Location(this.x + dig.amount, this.y);
            };
        }
    }

    private final List<Dig> digs;

    public Lagoon(Stream<String> lines) {
        digs = lines.map(s -> new Dig(Direction.from(s.charAt(0)), Integer.parseInt(s.substring(2, 4).trim()))).toList();
    }

    private Lagoon(List<Dig> digs) {
        this.digs = digs;
    }

    public static Lagoon fromColorCodes(Stream<String> lines) {
        return new Lagoon(lines.map(s -> {
            int start = s.indexOf('#') + 1;
            return new Dig(Direction.from(s.charAt(start + 5)), Integer.parseInt(s.substring(start, start + 5), 16));
        }).toList());
    }

    public int len() {
        return digs.stream()
                .mapToInt(Dig::amount)
                .sum();
    }

    private List<Location> coordinates() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(1, 1));
        for (Dig dig : digs) {
            locations.add(locations.getLast().next(dig));
        }
        return locations;
    }
    private double shoelaceArea(List<Location> v) {
        int n = v.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += v.get(i).x * v.get(i + 1).y - v.get(i + 1).x * v.get(i).y;
        }
        return Math.abs(a + v.get(n - 1).x * v.getFirst().y - v.getFirst().x * v.get(n - 1).y) / 2.0;
    }

    public long calculateArea() {
        double shoelaceArea = shoelaceArea(coordinates());
        double southAndEastEdges = len() / 2d + 1;
        return (long) (shoelaceArea + southAndEastEdges);
    }
}
