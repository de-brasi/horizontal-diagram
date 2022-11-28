package health.helper.posturecorrectorstatisticviewer.utility_classes;

import java.util.ArrayList;

public class ColourScheme {
    public ColourScheme() {
        colorsSet = new ArrayList<>();

        colorsSet.add(new CustomColorRGB(220, 32, 101));

        colorsSet.add(new CustomColorRGB(32, 145, 220));

        colorsSet.add(new CustomColorRGB(220, 32, 220));

        colorsSet.add(new CustomColorRGB(58, 79, 149));

        colorsSet.add(new CustomColorRGB(149, 58, 92));

        colorsSet.add(new CustomColorRGB(58, 149, 134));

        colorsSet.add(new CustomColorRGB(223, 113, 73));

        colorsSet.add(new CustomColorRGB(53, 202, 202));

        colorsSet.add(new CustomColorRGB(92, 73, 222));

        colorsSet.add(new CustomColorRGB(46, 210, 123));

    }

    public ArrayList<CustomColorRGB> colorsSet;
    public CustomColorRGB BACKGROUND =
            new CustomColorRGB(181, 144, 186);
    public CustomColorRGB AXIS_COLOR =
            new CustomColorRGB(100, 100, 100);
}
