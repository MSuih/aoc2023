public class Mapper {
    private final long sourceStart;
    private final long destinationStart;
    private final long range;


    public Mapper(long sourceStart, long destinationStart, long range) {
        this.sourceStart = sourceStart;
        this.destinationStart = destinationStart;
        this.range = range;
    }

    public static Mapper parse(String s) {
        String[] numbers = s.split(" ");
        return new Mapper(Long.parseLong(numbers[1]), Long.parseLong(numbers[0]), Long.parseLong(numbers[2]));
    }

    public boolean isMapped(long original) {
        return original >= sourceStart && original < sourceStart + range;
    }

    public long map(long original) {
        long diff = original - sourceStart;
        return destinationStart + diff;
    }
}
