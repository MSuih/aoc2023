import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Contraption {
    private enum Item {
        SWNE_MIRROR, NWSE_MIRROR, HORIZONTAL_SPLITTER, VERTICAL_SPLITTER;

        public static Item parse(int c) {
            return switch (c) {
                case '/' -> SWNE_MIRROR;
                case '\\' -> NWSE_MIRROR;
                case '-' -> HORIZONTAL_SPLITTER;
                case '|' -> VERTICAL_SPLITTER;
                case '.' -> null;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }

        public Stream<BeamPath> simulate(Location location, Direction direction) {
            BeamPath toNorth = new BeamPath(new Location(location.x, location.y - 1), Direction.NORTH);
            BeamPath toSouth = new BeamPath(new Location(location.x, location.y + 1), Direction.SOUTH);
            BeamPath toWest = new BeamPath(new Location(location.x - 1, location.y), Direction.WEST);
            BeamPath toEast = new BeamPath(new Location(location.x + 1, location.y), Direction.EAST);

            return switch (this) {
                case SWNE_MIRROR -> switch (direction) {
                    case NORTH -> Stream.of(toEast);
                    case SOUTH -> Stream.of(toWest);
                    case EAST -> Stream.of(toNorth);
                    case WEST -> Stream.of(toSouth);
                };
                case NWSE_MIRROR ->  switch (direction) {
                    case NORTH -> Stream.of(toWest);
                    case SOUTH -> Stream.of(toEast);
                    case EAST -> Stream.of(toSouth);
                    case WEST -> Stream.of(toNorth);
                };
                case HORIZONTAL_SPLITTER -> {
                    if (direction.isHorizontal()) {
                        yield Stream.of(direction == Direction.EAST ? toEast : toWest);
                    }
                    yield Stream.of(toWest, toEast);
                }
                case VERTICAL_SPLITTER -> {
                    if (direction.isHorizontal()) {
                        yield Stream.of(toNorth, toSouth);
                    }
                    yield Stream.of(direction == Direction.NORTH ? toNorth : toSouth);
                }
            };
        }
    }

    private final Item[][] items;

    public Contraption(List<String> lines) {
        items = lines.stream()
                .map(s -> s.chars()
                        .mapToObj(Item::parse)
                        .toArray(Item[]::new))
                .toArray(Item[][]::new);
    }

    private record Location(int x, int y) {
    }

    private enum Direction {
        NORTH, SOUTH, EAST, WEST;

        public boolean isHorizontal() {
            return this == EAST || this == WEST;
        }
    }

    private record BeamPath(Location location, Direction direction) {
        public BeamPath getNext() {
            return switch (direction) {
                case NORTH -> new BeamPath(new Location(location.x, location.y - 1), direction);
                case SOUTH -> new BeamPath(new Location(location.x, location.y + 1), direction);
                case EAST -> new BeamPath(new Location(location.x + 1, location.y), direction);
                case WEST -> new BeamPath(new Location(location.x - 1, location.y), direction);
            };
        }
    }

    public int simulateOneBeam() {
        return simulateBeam(new BeamPath(new Location(0, 0), Direction.EAST));
    }

    public int mostEnergyPossible() {
        Stream<BeamPath> fromLeft = IntStream.range(0, items.length)
                .mapToObj(i -> new BeamPath(new Location(0, i), Direction.EAST));
        Stream<BeamPath> fromRight = IntStream.range(0, items.length)
                .mapToObj(i -> new BeamPath(new Location(items[i].length - 1, i), Direction.WEST));

        Stream<BeamPath> fromTop = IntStream.range(0, items[0].length)
                .mapToObj(i -> new BeamPath(new Location(i, 0), Direction.SOUTH));
        Stream<BeamPath> fromBottom = IntStream.range(0, items[0].length)
                .mapToObj(i -> new BeamPath(new Location(i, items.length - 1), Direction.NORTH));

        Stream<BeamPath> horizontal = Stream.concat(fromLeft, fromRight);
        Stream<BeamPath> vertical = Stream.concat(fromTop, fromBottom);

        return Stream.concat(horizontal, vertical)
                .mapToInt(this::simulateBeam).max()
                .orElseThrow();
    }

    private int simulateBeam(BeamPath origin) {
        Deque<BeamPath> beams = new ArrayDeque<>();
        Set<BeamPath> visitedLocations = new HashSet<>();
        beams.add(origin);
        while (!beams.isEmpty()) {
            BeamPath current = beams.removeFirst();
            if (!visitedLocations.add(current)) {
                continue;
            }
            Item item = items[current.location.y][current.location.x];
            if (item != null) {
                item.simulate(current.location, current.direction).filter(b -> withinBounds(b.location)).forEach(beams::addFirst);
            } else {
                BeamPath next = current.getNext();
                if (withinBounds(next.location)) {
                    beams.add(next);
                }
            }
        }
        return (int) visitedLocations.stream().map(BeamPath::location).distinct().count();
    }

    private boolean withinBounds(Location location) {
        int x = location.x;
        int y = location.y;
        return x >= 0 && y >= 0 && y < items.length && x < items[y].length;
    }
}
