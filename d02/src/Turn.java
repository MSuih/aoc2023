public class Turn {
    private int red, green, blue;
    public Turn(String input) {
        for (String s : input.split(",")) {
            int value = Integer.parseInt(s.substring(1, s.indexOf(' ', 1)));
            if (s.endsWith("red")) {
                red = value;
            } else if (s.endsWith("green")) {
                green = value;
            } else if (s.endsWith("blue")) {
                blue = value;
            } else {
                throw new IllegalArgumentException(input);
            }
        }
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
