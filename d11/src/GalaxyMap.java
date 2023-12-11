import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class GalaxyMap {
    private record Location(long x, long y) {}
    private final List<Location> galaxies;

    public GalaxyMap(List<String> input, int expandedSize) {
        galaxies = new ArrayList<>();

        Set<Integer> emptyRows = IntStream.range(0, input.size()).boxed().collect(Collectors.toSet());
        Set<Integer> emptyColumns = IntStream.range(0, input.getFirst().length()).boxed().collect(Collectors.toSet());
        for (int row = 0; row < input.size(); row++) {
            char[] chars = input.get(row).toCharArray();
            for (int column = 0; column < chars.length; column++) {
                if (chars[column] == '#') {
                    galaxies.add(new Location(column, row));
                    emptyRows.remove(row);
                    emptyColumns.remove(column);
                }
            }
        }
        expandEmpty(emptyRows, emptyColumns, expandedSize);
    }

    private void expandEmpty(Set<Integer> emptyRows, Set<Integer> emptyColumns, int expandedSize) {
        expandedSize -= 1;
        int expandedRows = 0;
        for (Integer emptyRow : emptyRows) {
            for (ListIterator<Location> iterator = galaxies.listIterator(); iterator.hasNext(); ) {
                Location galaxy = iterator.next();
                if (galaxy.y > emptyRow + expandedRows) {
                    iterator.remove();
                    iterator.add(new Location(galaxy.x, galaxy.y + expandedSize));
                }
            }
            expandedRows += expandedSize;
        }
        int expandedColumns = 0;
        for (Integer emptyColumn : emptyColumns) {
            for (ListIterator<Location> iterator = galaxies.listIterator(); iterator.hasNext(); ) {
                Location galaxy = iterator.next();
                if (galaxy.x > emptyColumn + expandedColumns) {
                    iterator.remove();
                    iterator.add(new Location(galaxy.x + expandedSize, galaxy.y));
                }
            }
            expandedColumns += expandedSize;
        }
    }

    public long shortestDistanceSums() {
        return LongStream.range(0, galaxies.size() - 1)
                .flatMap(start -> IntStream.range((int) (start + 1), galaxies.size())
                        .mapToLong(end -> {
                            Location startLoc = galaxies.get((int) start);
                            Location endLoc = galaxies.get(end);
                            return Math.abs(endLoc.x - startLoc.x) + Math.abs(endLoc.y - startLoc.y);
                        }))
                .sum();
    }
}
