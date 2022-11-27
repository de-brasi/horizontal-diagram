package health.helper.posturecorrectorstatisticviewer.utility_classes;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class CustomColorRGB {
    public CustomColorRGB(int redVal, int greenVal, int blueVal) {
        red = redVal;
        green = greenVal;
        blue = blueVal;
    }

    public CustomColorRGB(@NonNull Color sourceColor) {
        red = sourceColor.red();
        green = sourceColor.green();
        blue = sourceColor.blue();
    }

    public final float red;
    public final float green;
    public final float blue;
}
