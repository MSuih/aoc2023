import java.util.ArrayList;
import java.util.List;

public class Mappings {
    private final List<Mapper> seedToSoil = new ArrayList<>();
    private final List<Mapper> soilToFertilizer = new ArrayList<>();

    private final List<Mapper> fertilizerToWater = new ArrayList<>();
    private final List<Mapper> waterToLight = new ArrayList<>();
    private final List<Mapper> lightToTemperature = new ArrayList<>();
    private final List<Mapper> temperatureToHumidity = new ArrayList<>();
    private final List<Mapper> humidityToLocation = new ArrayList<>();
    
    public Mappings(List<String> input) {
        List<Mapper> current = null;
        for (String line : input) {
            if (current == null) {
                current = switch (line) {
                    case "seed-to-soil map:" -> seedToSoil;
                    case "soil-to-fertilizer map:" -> soilToFertilizer;
                    case "fertilizer-to-water map:" -> fertilizerToWater;
                    case "water-to-light map:" -> waterToLight;
                    case "light-to-temperature map:" -> lightToTemperature;
                    case "temperature-to-humidity map:" -> temperatureToHumidity;
                    case "humidity-to-location map:" -> humidityToLocation;
                    default -> throw new IllegalArgumentException();
                };
            } else if (line.isEmpty()) {
                    current = null;
            } else {
                current.add(Mapper.parse(line));
            }
        }
    }

    @FunctionalInterface
    private interface ListMapper {
        long apply(List<Mapper> mappers, long l);
    }
    public long map(long seed) {
        ListMapper listMapper = (list, value) -> list.stream()
                .filter(l -> l.isMapped(value))
                .mapToLong(l -> l.map(value))
                .findAny()
                .orElse(value);
        long soil = listMapper.apply(seedToSoil, seed);
        long fertilizer = listMapper.apply(soilToFertilizer, soil);
        long water = listMapper.apply(fertilizerToWater, fertilizer);
        long light = listMapper.apply(waterToLight, water);
        long temperature = listMapper.apply(lightToTemperature, light);
        long humidity = listMapper.apply(temperatureToHumidity, temperature);
        return listMapper.apply(humidityToLocation, humidity);
    }
}
