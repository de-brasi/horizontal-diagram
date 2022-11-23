package health.helper.posturecorrectorstatisticviewer.utility_classes;

import health.helper.posturecorrectorstatisticviewer.utility_classes.HelpfulFunctions;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.Map;

public class CustomGraph {
    public CustomGraph(Paint paint_, ColourScheme colourScheme_) {
        paint = paint_;
        colourScheme = colourScheme_;
    }

    public void setSettings(@NonNull Canvas canvas,
                            GraphDesignElements visualSettings_,
                            float xOffsetFromOrigin_, float yOffsetFromOrigin_,
                            float xAxisLength_, float yAxisLength_,
                            int xScaleCount_, int yScaleCount_,
                            float printedRowThickness_) {
        // Set canvas that will used under class purpose
        workCanvas = canvas;

        // Class-container for printing info such as colours, line options etc
        visualSettings = visualSettings_;

        // That means how far are dot (0, 0) of graph
        // from dot (0, 0) of global origin
        xOffsetFromOrigin = xOffsetFromOrigin_;
        yOffsetFromOrigin = yOffsetFromOrigin_;

        // Lengths of visible graph's axes
        xAxisLength = xAxisLength_;
        yAxisLength = yAxisLength_;

        xScaleCount = xScaleCount_;
        yScaleCount = yScaleCount_;

        // The thickness of the figures denoting the value of the graph
        printedRowThickness = printedRowThickness_;
    }

    public void printAxes() {
        printWithScaleAxisX(
                workCanvas,
                xOffsetFromOrigin, xOffsetFromOrigin + xAxisLength,
                yOffsetFromOrigin, yOffsetFromOrigin,
                xScaleCount, visualSettings
        );
        printWithScaleAxisY(
                workCanvas,
                xOffsetFromOrigin, xOffsetFromOrigin,
                yOffsetFromOrigin, yOffsetFromOrigin + yAxisLength,
                yScaleCount, visualSettings
        );
    }

    public void changeCoordinateSystemToGraph() {
        // Must be braced with Canvas save and restore methods!
        workCanvas.translate(xOffsetFromOrigin, yOffsetFromOrigin);
    }

    public void printDiagramData(Map<Integer, Float> graphData) {
        // Исходя из личных предпочтений о "гармонии и красоте"
        // выбирается значение workspace_share (0 < workspace_share <= (axsiMax - axisMin)) -
        // это значение обозначает долю области от общего пространства,
        // которую может занимать горизонтальная полоска.
        // Максимальное значение в данных диаграмы заполняет workspace_share на 100%,
        // остальные значения длины полоски при отрисовке из данных пересчитываются
        // относительно макимума по формуле: (val / max_val) * workspace_share

        paint.setStrokeWidth(visualSettings.dataRowThickness);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        float workspace_share = visualSettings.workspaceValue;
        float workspace_len = xAxisLength * workspace_share;

        float distanceBetweenScales_y = yAxisLength / (yScaleCount + 1);

        int curScale;
        float curValue;
        float curLength;
        float distanceFromAxisOX;

        float maxValue = HelpfulFunctions.getMaximumValueFromMap(graphData);

        int colorIdx = 0;
        CustomColorRGB curColor;
        for (Map.Entry<Integer, Float> entry: graphData.entrySet()) {
            // Choice color
            curColor = colourScheme.colorsSet.get(colorIdx);
            paint.setColor(Color.rgb(curColor.red, curColor.green, curColor.blue));
            ++colorIdx;

            curScale = entry.getKey();
            curValue = entry.getValue();

            curLength = (curValue / maxValue) * workspace_len;
            // Если значение длины нулевое, то отрисовать маленький отрезок
            curLength = Math.max(curLength, visualSettings.defaultValueRowLength);
            distanceFromAxisOX = distanceBetweenScales_y * curScale;

            workCanvas.drawRoundRect(
                    xOffsetFromOrigin,
                    yOffsetFromOrigin +
                            distanceFromAxisOX + visualSettings.dataRowThickness / 2,
                    xOffsetFromOrigin + curLength,
                    yOffsetFromOrigin +
                            distanceFromAxisOX - visualSettings.dataRowThickness / 2,
                    0.1F, 0.1F, paint
            );
        }
    }

    private void printWithScaleAxisX(@NonNull Canvas canvas,
                                     float xStart, float xFinish,
                                     float yStart, float yFinish,
                                     int scaleCount,
                                     @NonNull GraphDesignElements visualSettings) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(visualSettings.axisThickness);

        canvas.drawLine(xStart, yStart, xFinish, yStart, paint);

        // Отметки вдоль оси X
        float axisSize = xFinish - xStart;
        float scaleStep = axisSize / (scaleCount + 1);

        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(
                    xStart + i * scaleStep, yStart,
                    visualSettings.xAxisDotRadius, paint);
        }

        // Окончание - стрелка для оси
        // TODO: Для оси X нужна стрелка
    }

    private void printWithScaleAxisY(@NonNull Canvas canvas,
                                     float xStart, float xFinish,
                                     float yStart, float yFinish,
                                     int scaleCount,
                                     @NonNull GraphDesignElements visualSettings) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(visualSettings.axisThickness);

        // (0, 0) dot
        canvas.drawCircle(xStart, yStart, visualSettings.yAxisDotRadius, paint);

        // Axis Y
        canvas.drawLine(xStart, yStart, xFinish, yFinish, paint);

        // Scale in Y
        float axisSize = yFinish - yStart;
        float scaleStep = axisSize / (scaleCount + 1);

        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(
                    xStart, yStart + i * scaleStep,
                    visualSettings.yAxisDotRadius, paint);
        }

        // Окончание - стрелка для оси
        // Для оси Y нужна просто черточка/точка
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(
                xStart, yFinish,
                visualSettings.yAxisDotRadius * 1.3F, paint);
    }

    private Paint paint;
    private Canvas workCanvas;
    private GraphDesignElements visualSettings;
    private ColourScheme colourScheme;

    public float xOffsetFromOrigin;
    public float yOffsetFromOrigin;
    public float xAxisLength;
    public float yAxisLength;
    public int xScaleCount;
    public int yScaleCount;
    public float printedRowThickness;
    // PLus colour presets;
}
