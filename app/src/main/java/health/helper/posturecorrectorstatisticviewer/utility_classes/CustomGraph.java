package health.helper.posturecorrectorstatisticviewer.utility_classes;

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
                            float xRightOffset_, float yUpOffset_,
                            int xScaleCount_, int yScaleCount_,
                            float printedRowThickness_) {
        workCanvas = canvas;

        // Class-container for printing info such as colours, line options etc
        visualSettings = visualSettings_;

        // That means how far are dot (0, 0) of graph
        // from dot (0, 0) of global origin
        xOffsetFromOrigin = xOffsetFromOrigin_;
        yOffsetFromOrigin = yOffsetFromOrigin_;

        xRightOffset = xRightOffset_;
        yUpOffset = yUpOffset_;

        xScaleCount = xScaleCount_;
        yScaleCount = yScaleCount_;

        printedRowThickness = printedRowThickness_;
    }

    public void setWorkspaceSize(float xSize, float ySize) {
        xMeasurement = xSize;
        yMeasurements = ySize;
    }

    public void printAxes() {
        paint.setColor(Color.rgb(
                colourScheme.AXIS_COLOR.red,
                colourScheme.AXIS_COLOR.green,
                colourScheme.AXIS_COLOR.blue));

        paint.setStrokeWidth(visualSettings.axisThickness);
        paint.setStyle(Paint.Style.FILL);

        printWithScaleAxisX(workCanvas, visualSettings,
                xOffsetFromOrigin, xMeasurement - xRightOffset,
                yOffsetFromOrigin, yOffsetFromOrigin,
                xScaleCount);
        printWithScaleAxisY(workCanvas, visualSettings,
                xOffsetFromOrigin, xOffsetFromOrigin,
                yOffsetFromOrigin, yMeasurements - yUpOffset,
                yScaleCount);
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
        float workspace_len = (xMeasurement - xOffsetFromOrigin - xRightOffset) * workspace_share;

        float distanceBetweenScales_y = (yMeasurements - yOffsetFromOrigin - yUpOffset) / (yScaleCount + 1);

        int curScale;
        float curValue;
        float curLength;
        float distanceFromAxisOX;

        float maxValue = HelpfulFunctions.getMaximumValueFromMap(graphData);

        int colorIdx = 0;
        CustomColorRGB curColor;
        for (Map.Entry<Integer, Float> entry: graphData.entrySet()) {
            // Choice color
            colorIdx = colorIdx % colourScheme.colorsSet.size();
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
                                     @NonNull GraphDesignElements visualSettings,
                                     float xStart, float xFinish,
                                     float yStart, float yFinish,
                                     int scaleCount) {
        canvas.drawLine(xStart, yStart, xFinish, yStart, paint);

        // Отметки вдоль оси X
        float axisSize = xFinish - xStart;
        float scaleStep = axisSize / (scaleCount + 1);

        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(
                    xStart + i * scaleStep, yStart,
                    visualSettings.xAxisDotRadius, paint);
        }
    }

    private void printWithScaleAxisY(@NonNull Canvas canvas,
                                     @NonNull GraphDesignElements visualSettings,
                                     float xStart, float xFinish,
                                     float yStart, float yFinish,
                                     int scaleCount) {
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

        canvas.drawCircle(
                xStart, yFinish,
                visualSettings.yAxisDotRadius * 1.3F, paint);
    }

    private Paint paint;
    private Canvas workCanvas;
    private GraphDesignElements visualSettings;
    private ColourScheme colourScheme;

    public boolean isTransposed;

    public float xRightOffset;
    public float yUpOffset;

    public float xMeasurement;
    public float yMeasurements;

    public float xOffsetFromOrigin;
    public float yOffsetFromOrigin;
    public int xScaleCount;
    public int yScaleCount;
    public float printedRowThickness;
    // PLus colour presets;
}
