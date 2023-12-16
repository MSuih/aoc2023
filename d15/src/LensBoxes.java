import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class LensBoxes {
    private static final int BOX_COUNT = 256;

    private record Lens(String label, int value) {}
    private final Map<Integer, List<Lens>> boxes = new HashMap<>();

    public LensBoxes() {
        for (int i = 0; i < BOX_COUNT; i++) {
            boxes.put(i, new ArrayList<>());
        }
    }

    public void take(String label) {
        int box = LensBoxes.hash(label);
        List<Lens> lenses = boxes.get(box);
        lenses.removeIf(lens -> lens.label.equals(label));
    }

    public void add(String label, int value) {
        int box = LensBoxes.hash(label);
        List<Lens> lenses = boxes.get(box);
        Lens newLens = new Lens(label, value);
        for (ListIterator<Lens> iterator = lenses.listIterator(); iterator.hasNext(); ) {
            Lens lens = iterator.next();
            if (lens.label.equals(label)) {
                iterator.remove();
                iterator.add(newLens);
                return;
            }
        }
        lenses.add(newLens);
    }

    public static int hash(String line) {
        int val = 0;
        for (char c : line.toCharArray()) {
            val += c;
            val *= 17;
            val %= BOX_COUNT;
        }
        return val;
    }

    public long calculateFocusValue() {
        long val = 0;
        for (int box = 0; box < BOX_COUNT; box++) {
            List<Lens> lenses = boxes.get(box);
            for (int lens = 0; lens < lenses.size(); lens++) {
                val += (box + 1L) * (lens + 1L) * lenses.get(lens).value;
            }
        }
        return val;
    }
}
