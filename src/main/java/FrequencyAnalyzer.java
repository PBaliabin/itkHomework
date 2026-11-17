import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrequencyAnalyzer {
    public <T> Map<T, Long> frequency(T[] items){
        Map<T, Long> frequency = new LinkedHashMap<>();
        Arrays.stream(items).forEach(item -> frequency.put(item, frequency.getOrDefault(item, 0L) + 1));
        return frequency;
    }
}
