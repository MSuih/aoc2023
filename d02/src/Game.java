import java.util.Arrays;
import java.util.function.ToIntFunction;

public class Game {
    private final int index;
    private final Turn[] turns;

    public Game(String s) {
        String[] split = s.split(":");
        index = Integer.parseInt(split[0].substring("Game ".length()));
        String[] turns = split[1].split(";");
        this.turns = new Turn[turns.length];
        for (int i = 0; i < turns.length; i++) {
            this.turns[i] = new Turn(turns[i]);
        }
    }

    public int getIndex() {
        return index;
    }

    public boolean underLimits(int redLimit, int greenLimit, int blueLimit) {
        return Arrays.stream(turns)
                .allMatch(turn -> turn.getRed() <= redLimit && turn.getGreen() <= greenLimit && turn.getBlue() <= blueLimit);
    }

    public int maxRed() {
        return max(Turn::getRed);
    }

    public int maxGreen() {
        return max(Turn::getGreen);
    }

    public int maxBlue() {
        return max(Turn::getBlue);
    }

    private int max(ToIntFunction<Turn> function) {
        return Arrays.stream(turns)
                .mapToInt(function)
                .max()
                .orElseThrow();
    }
}
