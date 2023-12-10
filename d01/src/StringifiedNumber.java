public class StringifiedNumber {
    private final char[] chars;
    private final int value;

    private int position = 0;

    public StringifiedNumber(char[] chars, int value, boolean reverse) {
        this.chars = reverse ? reverse(chars) : chars;
        this.value = value;
    }

    private char[] reverse(char[] o) {
        char[] n = new char[o.length];
        for (int i = 0; i < o.length; i++) {
            n[i] = o[o.length - 1 - i];
        }
        return n;
    }

    public boolean test(char c) {
        if (c != chars[position]) {
            position = c == chars[0]? 1 : 0;
            return false;
        }
        position++;
        if (position == chars.length) {
            position = 0;
            return true;
        }
        return false;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringifiedNumber{" + new String(chars) + ", " + position + '}';
    }
}
