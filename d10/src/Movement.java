public record Movement(int x, int y, int distance) {
    public Movement(Position p) {
        this(p.x(), p.y(), 2);
    }
    public Movement(Position p, int distance) {
        this(p.x(), p.y(), distance);
    }
}
