package health.helper.posturecorrectorstatisticviewer.utility_classes;

import androidx.annotation.NonNull;

import java.util.Map;

public class HelpfulFunctions {
    public HelpfulFunctions() {}

    public static  <K, V extends Comparable<V>> V getMaximumValueFromMap(@NonNull Map<K, V> targetMap) {
        V maxValue = null;

        for (Map.Entry<K, V> entry: targetMap.entrySet()) {
            if (maxValue == null || entry.getValue().compareTo(maxValue) > 0) {
                maxValue = entry.getValue();
            }
        }

        return maxValue;
    }
}
