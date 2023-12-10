public class StringifiedNumbers {
    private final StringifiedNumber[] numbers = new StringifiedNumber[9];

    public StringifiedNumbers(boolean reverse) {
        String[] strings = {
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine"
        };
        for (int i = 0; i < strings.length; i++) {
            numbers[i] = new StringifiedNumber(strings[i].toCharArray(), i + 1, reverse);
        }
    }

    public int testValue(char c) {
        for (StringifiedNumber number : numbers) {
            if (number.test(c)) {
                return number.getValue();
            }
        }
        return -1;
    }
}
