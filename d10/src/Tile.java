import java.util.stream.Stream;

public enum Tile {
    VERTICAL_PIPE('|'),
    HORIZONTAL_PIPE('-'),
    NE_BEND('L'),
    NW_BEND('J'),
    SW_BEND('7'),
    SE_BEND('F'),
    GROUND('.'),
    START('S');

    private final char c;

    Tile(char c) {
        this.c = c;
    }

    public static Tile from(char c) {
        for (Tile value : Tile.values()) {
            if (value.c == c) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.valueOf(c));
    }

    public Stream<Position> nextPositions(Position p) {
        return nextPositions(p.x(), p.y());
    }

    public Stream<Position> nextPositions(int x, int y) {
        return switch (this) {
            case VERTICAL_PIPE -> Stream.of(new Position(x, y - 1), new Position(x, y + 1));
            case HORIZONTAL_PIPE -> Stream.of(new Position(x - 1, y), new Position(x + 1, y));
            case NE_BEND -> Stream.of(new Position(x, y - 1), new Position(x + 1, y));
            case NW_BEND -> Stream.of(new Position(x - 1, y), new Position(x, y - 1));
            case SW_BEND -> Stream.of(new Position(x - 1, y), new Position(x, y + 1));
            case SE_BEND -> Stream.of(new Position(x + 1, y), new Position(x, y + 1));
            case GROUND, START -> Stream.empty();
        };
    }
}
