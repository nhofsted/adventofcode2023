import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Almanac {
    private static final Pattern almanacMapPattern = Pattern.compile("(\\w+)-to-(\\w+) map:");

    private final HashMap<String, Integer> features;
    private final ArrayList<ArrayList<Mapping>> maps;

    public Almanac(HashMap<String, Integer> features, ArrayList<ArrayList<Mapping>> maps) {
        this.features = features;
        this.maps = maps;
    }

    public static Long[] parseSeeds(BufferedReader in) throws IOException {
        Long[] retVal = Arrays.stream(in.readLine().split(" "))
                .skip(1)
                .map(Long::parseLong)
                .toArray(Long[]::new);
        in.readLine();
        return retVal;
    }

    public static Almanac parseMaps(BufferedReader in) throws IOException {
        ArrayList<ArrayList<Mapping>> maps = new ArrayList<>();
        HashMap<String, Integer> features = new HashMap<>();
        features.put("seed", 0);
        int index = 1;
        String line;
        while ((line = in.readLine()) != null) {
            ArrayList<Mapping> featureMap = new ArrayList<>();
            Matcher m = Parser.getMatchedMatcher(almanacMapPattern, line);
            features.put(m.group(2), index++);
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                String[] numbers = line.split(" ");
                featureMap.add(new Mapping(Long.parseLong(numbers[1]),
                        Long.parseLong(numbers[0]),
                        Long.parseLong(numbers[2])));
            }
            Collections.sort(featureMap);
            maps.add(featureMap);
        }
        return new Almanac(features, maps);
    }

    public Long[] mapSeed(Long seed) {
        Long[] mapping = new Long[features.size() + 1];
        int i = 0;
        mapping[i++] = seed;
        for (ArrayList<Mapping> mappings : maps) {
            int pos = Collections.binarySearch(mappings, new Mapping(seed, 0, 0));
            int closestMin = pos >= 0 ? pos : -(pos + 2);
            if (closestMin >= 0 && closestMin <= mappings.size()) {
                Mapping m = mappings.get(closestMin);
                if (seed >= m.source && seed < m.source + m.range) {
                    seed = m.destination + (seed - m.source);
                }
            }
            mapping[i++] = seed;
        }
        return mapping;
    }

    public int getPosition(String feature) {
        return features.get(feature);
    }

    private static record Mapping(long source, long destination, long range) implements Comparable<Mapping> {
        @Override
        public int compareTo(Mapping o) {
            return (int) Math.signum(source - o.source);
        }
    }
}