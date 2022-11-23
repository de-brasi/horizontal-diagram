package health.helper.posturecorrectorstatisticviewer.utility_classes;

import java.util.List;
import java.util.Vector;

public class ColourScheme {
    public ColourScheme() {
        // TODO: constructor from brightness value or pastel color level
        colorsSet = new Vector<>();

        colorsSet.add(List.of(220, 32, 101));

        colorsSet.add(List.of(32, 145, 220));

        colorsSet.add(List.of(220, 32, 220));

        colorsSet.add(List.of(58, 79, 149));

        colorsSet.add(List.of(149, 58, 92));

        colorsSet.add(List.of(58, 149, 134));

        colorsSet.add(List.of(223, 113, 73));

        colorsSet.add(List.of(53, 202, 202));

        colorsSet.add(List.of(92, 73, 222));

        colorsSet.add(List.of(46, 210, 123));

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

    final public Vector<List<Integer>> colorsSet;
}
