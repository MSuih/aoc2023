import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

public class Schematic {
    private final char[][] chars;

    public Schematic(List<String> strings) {
        chars = new char[strings.size()][];
        for (int i = 0; i < strings.size(); i++) {
            chars[i] = strings.get(i).toCharArray();
        }
    }

    private void checkSurroundingNumbers(int x, int y, int[][] result, int value) {
        markNumber(x - 1, y - 1, result, value);
        markNumber(x - 1, y, result, value);
        markNumber(x - 1, y + 1, result, value);
        markNumber(x, y - 1, result, value);
        markNumber(x, y + 1, result, value);
        markNumber(x + 1, y - 1, result, value);
        markNumber(x + 1, y, result, value);
        markNumber(x + 1, y + 1, result, value);
    }

    public static boolean isNumber(int c) {
        return c >= '0' && c <= '9';
    }

    public boolean isNumber(int x, int y) {
        if (x < 0 || y < 0)
            return false;
        if (x >= chars.length || y >= chars[x].length)
            return false;
        return isNumber(chars[x][y]);
    }

    public void markNumber(int x, int y, int[][] result, int mark) {
        if (x < 0 || y < 0)
            return;
        if (x >= chars.length || y >= chars[x].length)
            return;
        if (isNumber(chars[x][y]))
            result[x][y] = mark;
    }

    private boolean[][] symbols(IntPredicate validSymbol) {
        boolean[][] symbols = new boolean[chars.length][chars[0].length];
        for (int x = 0; x < chars.length; x++) {
            for (int y = 0; y < chars[x].length; y++) {
                if (validSymbol.test(chars[x][y])) {
                    symbols[x][y] = true;
                }
            }
        }
        return symbols;
    }

    private int[][] numbersConnectedToSymbol(IntPredicate validSymbol) {
        boolean[][] symbols = symbols(validSymbol);
        int[][] numbersNextToChar = new int[symbols.length][symbols[0].length];
        int currentSymbol = 1;
        for (int x = 0; x < chars.length; x++) {
            for (int y = 0; y < chars[x].length; y++) {
                if (symbols[x][y]) {
                    checkSurroundingNumbers(x, y, numbersNextToChar, currentSymbol++);
                }
            }
        }
        expandNumbers(numbersNextToChar);
        return numbersNextToChar;
    }

    private void expandNumbers(int[][] numbers) {
        for (int x = 0; x < chars.length; x++) {
            for (int y = 0; y < chars[x].length; y++) {
                int[] row = numbers[x];
                if (row[y] > 0) {
                    int num = row[y];
                    for (int by = y - 1; by >= 0 && isNumber(x, by); by--) {
                        row[by] = num;
                    }
                    for (int fy = y + 1; fy < row.length && isNumber(x, fy); fy++) {
                        row[fy] = num;
                    }
                }
            }
        }
    }

    public List<Integer> partNumbers() {
        var list = new ArrayList<Integer>();
        int[][] numberConnectedToSymbol = numbersConnectedToSymbol(((IntPredicate) Schematic::isNumber).negate().and(i -> i != '.'));
        for (int x = 0; x < chars.length; x++) {
            int i = 0;
            for (int y = 0; y < chars[x].length; y++) {
                if (isNumber(x, y) && numberConnectedToSymbol[x][y] > 0) {
                    i = i * 10 + (chars[x][y] - '0');
                } else if (i > 0) {
                    list.add(i);
                    i = 0;
                }
            }
            if (i > 0) {
                list.add(i);
            }
        }
        return list;
    }

    public List<Integer> gearRatios() {
        int[][] possibleGearValues = numbersConnectedToSymbol(i -> i == '*');
        Map<Integer, List<Integer>> map = gearValues(possibleGearValues);
        return map.values().stream()
                .filter(l -> l.size() == 2)
                .map(l -> l.stream().mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b))
                .toList();
    }

    private Map<Integer, List<Integer>> gearValues(int[][] possibleGearValues) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int x = 0; x < possibleGearValues.length; x++) {
            int[] row = possibleGearValues[x];
            for (int y = 0; y < row.length; y++) {
                int mark = row[y];
                if (mark > 0) {
                    int sum = 0;
                    do {
                        sum = sum * 10 + (chars[x][y] - '0');
                    } while (++y < row.length && row[y] > 0);
                    map.computeIfAbsent(mark, i -> new ArrayList<>()).add(sum);
                }
            }
        }
        return map;
    }
}
