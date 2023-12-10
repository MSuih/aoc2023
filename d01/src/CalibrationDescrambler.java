public class CalibrationDescrambler {

    public int[] descramble(String input) {
        return input.lines()
                .mapToInt(this::descrambleLine)
                .toArray();
    }

    private int descrambleLine(String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        }
        char[] charArray = line.toCharArray();

        int first = getFirst(charArray);
        int last = getLast(charArray);
        if (first <= 0 || last <= 0) {
            throw new IllegalStateException("%s %s %s".formatted(line, first, last));
        }

        int answer = (first * 10) + last;
        if (answer < 11 || answer > 99) {
            throw new IllegalStateException(line + "->" + answer);
        }
        return answer;
    }

    private int getFirst(char[] charArray) {
        StringifiedNumbers stringifiedNumbers = new StringifiedNumbers(false);
        int first = -1;
        for (char c : charArray) {
            int i = test(c, stringifiedNumbers);
            if (i > 0) {
                first = i;
                break;
            }
        }
        return first;
    }

    private int getLast(char[] charArray) {
        int last = -1;
        StringifiedNumbers reverseStringifiedNumbers = new StringifiedNumbers(true);
        for (int idx = charArray.length - 1; idx >= 0; idx--) {
            int i = test(charArray[idx], reverseStringifiedNumbers);
            if (i > 0) {
                last = i;
                break;
            }
        }
        return last;
    }

    private int test(char c, StringifiedNumbers stringifiedNumbers) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        return stringifiedNumbers.testValue(c);
    }
}
