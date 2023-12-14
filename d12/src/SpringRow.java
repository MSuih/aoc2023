import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpringRow {
    public enum SpringStatus {
        WORKING, DAMAGED, UNKNOWN;

        public static SpringStatus from(int c) {
            return switch (c) {
                case '.' -> WORKING;
                case '#' -> DAMAGED;
                case '?' -> UNKNOWN;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    private final SpringStatus[] springs;
    private final int[] brokenSpringGroups;

    private record CacheLine(int start, int group) {}
    private final Map<CacheLine, Long> cachedValues = new HashMap<>();

    public SpringRow(String line) {
        String[] s = line.split(" ");
        springs = s[0].chars().mapToObj(SpringStatus::from).toArray(SpringStatus[]::new);
        brokenSpringGroups = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public SpringRow(SpringStatus[] unfoldedSprings, int[] unfoldedGroups) {
        this.springs = unfoldedSprings;
        this.brokenSpringGroups = unfoldedGroups;
    }

    public SpringRow unfold() {
        SpringStatus[] unfoldedSprings = new SpringStatus[springs.length * 5 + 4];
        int[] unfoldedGroups = new int[brokenSpringGroups.length * 5];
        for (int i = 0; i < 5; i++) {
            System.arraycopy(springs, 0, unfoldedSprings, springs.length * i + i, springs.length);
            if (i < 4) {
                unfoldedSprings[springs.length * (i + 1) + i] = SpringStatus.UNKNOWN;
            }
            System.arraycopy(brokenSpringGroups, 0, unfoldedGroups, brokenSpringGroups.length * i, brokenSpringGroups.length);
        }
        return new SpringRow(unfoldedSprings, unfoldedGroups);
    }

    public long waysToPosition() {
        return waysToPosition(0, 0);
    }

    private long waysToPosition(int start, int group) {
        CacheLine cacheLine = new CacheLine(start, group);
        if (cachedValues.containsKey(cacheLine)) {
            return cachedValues.get(cacheLine);
        }
        int maxStartingIndex = start + (springs.length - start) - Arrays.stream(brokenSpringGroups).skip(group).sum() - (brokenSpringGroups.length - 1 - group);
        int len = brokenSpringGroups[group];
        boolean last = group >= brokenSpringGroups.length - 1;
        long sum = 0;
        for (int i = start; i <= maxStartingIndex; i++) {
            if (fits(i, len)) {
                if (last) {
                    if (Arrays.stream(springs).skip(i + len).allMatch(s -> s != SpringStatus.DAMAGED)) {
                        sum++;
                    }
                } else if (springs[i + len] != SpringStatus.DAMAGED) {
                    sum += waysToPosition(i + len + 1, group + 1);
                }
            }
            if (springs[i] == SpringStatus.DAMAGED) {
                i += springs.length;
            }
        }
        cachedValues.put(cacheLine, sum);
        return sum;
    }

    private boolean fits(int start, int len) {
        for (int i = start; i < start + len; i++) {
            SpringStatus spring = springs[i];
            if (spring == SpringStatus.WORKING) {
                return false;
            }
        }
        return true;
    }
}
