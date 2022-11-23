package health.helper.posturecorrectorstatisticviewer.utility_classes;

import health.helper.posturecorrectorstatisticviewer.utility_classes.CustomColorRGB;

import java.util.ArrayList;

public class ColourScheme {
    public ColourScheme() {
        // TODO: constructor from brightness value or pastel color level
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

    // Все цвета можно рассматривать как некоторая точка в трехмерном кубе RGB
    // Некоторый абстрактный показатель яркость (brightness)
    // задает уравнение в этом кубе: r + g + b = brightness,
    // что обозначает, как близка задаваемая плоскость к точке (255, 255, 255)
    // - абсолютный белый цвет.
    // TODO: color generator
    // TODO: выдавать генераторам цвета согласно отображаемому значению -
    //  максимуму ошибок боллее агресисвные цвета, минимуму - более "поощряющие"
    private final int brightness = 0;

    final public ArrayList<CustomColorRGB> colorsSet;
    final public CustomColorRGB BACKGROUND =
            new CustomColorRGB(181, 144, 186);
}
