import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Tile[][] sample1 = readMap("sample1.txt");
        Tile[][] sample2 = readMap("sample2.txt");
        Tile[][] input = readMap("input.txt");

        part1(sample1);
        part1(sample2);
        part1(input);

        Tile[][] sample3 = readMap("sample3.txt");
        Tile[][] sample4 = readMap("sample4.txt");
        Tile[][] sample5 = readMap("sample5.txt");

        part2(sample1, "sample1");
        part2(sample2, "sample2");
        part2(sample3, "sample3");
        part2(sample4, "sample4");
        part2(sample5, "sample5");
        part2(input, "input");
    }

    public static Tile[][] readMap(String file) throws IOException {
        List<String> rows = Files.readAllLines(Path.of(file));
        Tile[][] tiles = new Tile[rows.get(1).length()][rows.size()];
        for (int r = 0; r < rows.size(); r++) {
            char[] chars = rows.get(r).toCharArray();
            for (int c = 0; c < chars.length; c++) {
                tiles[c][r] = Tile.from(chars[c]);
            }
        }
        return tiles;
    }

    public static void part1(Tile[][] tiles) {
        int[][] distances = new int[tiles.length][tiles[0].length];
        Position start = startPosition(tiles);
        for (int[] row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distances[start.x()][start.y()] = 0;

        List<Movement> movements = surroundingTiles(start).stream()
                .filter(p -> withinBounds(tiles, p.x(), p.y()))
                .flatMap(p -> {
                    List<Position> list = tiles[p.x()][p.y()].nextPositions(p.x(), p.y()).toList();
                    if (list.contains(start)) {
                        distances[p.x()][p.y()] = 1;
                        return list.stream();
                    }
                    return Stream.empty();
                })
                .filter(p -> !p.equals(start))
                .map(Movement::new)
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < movements.size(); i++) {
            Movement movement = movements.get(i);
            int x = movement.x();
            int y = movement.y();
            if (!withinBounds(tiles, x, y)) {
                continue;
            }
            if (movement.distance() >= distances[x][y]) {
                continue;
            }
            distances[x][y] = movement.distance();
            tiles[x][y].nextPositions(x, y)
                    .map(p -> new Movement(p, movement.distance() + 1))
                    .forEach(movements::add);
        }
        int max = Arrays.stream(distances)
                .flatMapToInt(Arrays::stream)
                .filter(i -> i != Integer.MAX_VALUE)
                .max().orElseThrow();
        System.out.println("Maximum distance is " + max + " tiles");
    }

    public static boolean withinBounds(Tile[][] tiles, int x, int y) {
        return x >= 0 && y >= 0 && x < tiles.length && y < tiles[x].length;
    }

    public static Position startPosition(Tile[][] tiles) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (tiles[x][y] == Tile.START) {
                    return new Position(x, y);
                }
            }
        }
        throw new IllegalStateException();
    }

    public static List<Position> surroundingTiles(Position position) {
        int x = position.x();
        int y = position.y();
        return List.of(
                new Position(x - 1, y),
                new Position(x + 1, y),
                new Position(x, y - 1),
                new Position(x, y + 1));
    }

    public static void part2(Tile[][] tiles, String name) throws IOException {
        Position start = startPosition(tiles);
        List<Position> path = surroundingTiles(start).stream()
                .filter(p -> withinBounds(tiles, p.x(), p.y()))
                .filter(p -> tiles[p.x()][p.y()].nextPositions(p.x(), p.y()).anyMatch(start::equals))
                .limit(1)
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < path.size(); i++) {
            Position position = path.get(i);
            int x = position.x();
            int y = position.y();
            tiles[x][y].nextPositions(position)
                    .filter(p -> !path.contains(p))
                    .filter(p -> !start.equals(p))
                    .forEach(path::add);
        }
        Shape shape = toShape(start, path);

        int sum = 0;
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Position p = new Position(x, y);
                if (!path.contains(p) && !p.equals(start) && shape.contains(p.x(), p.y())) {
                    sum++;
                }
            }
        }
        System.out.println(name + " contains " + sum + " tiles");

        drawToImage(tiles.length, tiles[0].length, name, shape);
    }

    private static void drawToImage(int x, int y, String name, Shape shape) throws IOException {
        int scale = name.equals("input") ? 8: 100;
        BufferedImage image = new BufferedImage(x  * scale, y * scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.scale(scale, scale);
        graphics.translate(0.5, 0.5);
        graphics.setColor(new Color(0, 200, 0));
        graphics.fill(shape);
        graphics.setStroke(new BasicStroke(0.9f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        graphics.setColor(new Color(0, 0, 200));
        graphics.draw(shape);
        ImageIO.write(image, "png", new File(name + ".png"));
    }

    private static Shape toShape(Position start, List<Position> positions) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.x(), start.y());
        positions.forEach(p -> path.lineTo(p.x(), p.y()));
        path.closePath();

        return new Area(path);
    }
}