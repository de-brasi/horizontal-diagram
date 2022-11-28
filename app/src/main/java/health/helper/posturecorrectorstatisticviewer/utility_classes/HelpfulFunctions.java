package health.helper.posturecorrectorstatisticviewer.utility_classes;

import android.graphics.Color;

import androidx.annotation.NonNull;

import java.util.ArrayList;
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

    @NonNull
    public static ArrayList<CustomColorRGB>  parseStringToColors(@NonNull String colors) {
        String[] parsedByStrings = colors.split(" ");

        ArrayList<CustomColorRGB> parsedColors = new ArrayList<>();

        for (String color: parsedByStrings) {
            String clean = color.substring(1);
            Color curColor = Color.valueOf(Integer.parseInt(clean, 16));
            parsedColors.add(new CustomColorRGB(curColor));
        }
        return parsedColors;
    }
}
