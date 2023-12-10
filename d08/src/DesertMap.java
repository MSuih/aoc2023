import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesertMap {
    private record Movement(String left, String right) {}

    private final Map<String, Movement> nodes;

    public DesertMap(List<String> lines) {
        nodes = new HashMap<>();
        for (String line : lines) {
            String name = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);
            nodes.put(name, new Movement(left, right));
        }
    }

    public int stepsToEnd(String input) {
        Movement current = nodes.get("AAA");
        int moves = 0;
        while (true) {
            for (char c : input.toCharArray()) {
                moves++;
                String next = c == 'L' ? current.left : current.right;
                if (next.equals("ZZZ")) {
                    return moves;
                }
                current = nodes.get(next);
            }
        }
    }

    public long fromAtoZ(String input) {
        String[] startingNodes = nodes.keySet().stream().filter(s -> s.endsWith("A")).toArray(String[]::new);
        List<Long> pathLengths = new ArrayList<>();
        for (String start : startingNodes) {
            String current = start;
            int idx = 0;
            long toFirstExit = 0;
            do {
                Movement movement = nodes.get(current);
                current = input.charAt(idx++) == 'L' ? movement.left : movement.right;
                idx %= input.length();
                toFirstExit++;
            } while (!current.endsWith("Z"));

            pathLengths.add(toFirstExit);
        }
        return pathLengths.stream()
                .mapToLong(Long::longValue)
                .reduce(DesertMap::lcm)
                .orElseThrow();
    }
    
    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }
}
