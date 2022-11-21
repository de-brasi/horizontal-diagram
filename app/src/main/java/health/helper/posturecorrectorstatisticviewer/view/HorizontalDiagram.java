package health.helper.posturecorrectorstatisticviewer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HorizontalDiagram extends View {

    // ---Private members---\
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Map<Integer, Float> graph_data =
            Map.of(1, 1F, 2, 2F, 3, 3F);
    private final GraphDesignElements designElements =
            new GraphDesignElements(0.1F);
    private GraphSettings graphSettings;
    // ---Private members---/

    public HorizontalDiagram(Context context) {
        super(context);

    }

    public HorizontalDiagram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalDiagram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float asxisStrokeWidth = 0.07F;
        // отступ начала координат в нормальной системе координат от края холста
        float axisX_offset = 0.5F;
        // отступ начала координат в нормальной системе координат от края холста
        float axisY_offset = -2F;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(asxisStrokeWidth);

        canvas.save();
        // ---Diagram printing start---


        // LEGACY
//        float xMax = 10F;   // axis X size
//        float xMin = 0F;
//        float yMax = 10F;   // axis Y size
//        float yMin = 0F;
//
//        float xEndingOffset = 1F;
//        float yEndingOffset = 1F;
//
//        float width = getWidth();
//        float height = getHeight();
//        float scale_parameter = Math.min(width, height); // what size chosen as standard
//
//        changeAxis(canvas, scale_parameter, xMin, xMax, yMin, yMax, axisX_offset, axisY_offset);
//
//        // axis X and Y
//        printWithScale_AxisX(
//                canvas, xMin, xMax, yMin, yMax,
//                xEndingOffset, asxisStrokeWidth, 3);
//        printWithScale_AxisY(
//                canvas, xMin, xMax, yMin, yMax,
//                yEndingOffset, asxisStrokeWidth, 3);
//
//        // Graph data printing
//        printDiagramData(
//                canvas, graph_data,
//                0F, 0F,
//                xMax - xMin - xEndingOffset, yMax - yMin - yEndingOffset,
//                graph_data.size());

        // NEW VERSION
        createWorkspace(canvas, 10F, 10F);


        // ---Diagram printing end---
        canvas.restore();
    }

    // /---------------------LEGACY------------------\
    protected void changeAxis(@NonNull Canvas canvas, float scale_parameter,
                              float xMin, float xMax, float yMin, float yMax,
                              float xOffset, float yOffset) {
        canvas.scale(scale_parameter / (xMax - xMin),
                -scale_parameter / (yMax - yMin));
        canvas.translate(-xMin + xOffset, -yMax + yOffset);
    }

    protected void printWithScale_AxisX(@NonNull Canvas canvas,
                                        float xMin, float xMax,
                                        float yMin, float yMax,
                                        float endingOffset,
                                        float thickness,
                                        int scaleCount) {
        paint.setColor(Color.BLACK);
        canvas.drawLine(xMin, yMin, xMax - endingOffset, yMin, paint);

        // Отметки вдоль оси X
        float axisSize = (xMax - endingOffset) - xMin;
        float scaleStep = axisSize / (scaleCount + 1);
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(i * scaleStep, 0F, thickness, paint);
        }

        // Окончание - стрелка для оси
        // Для оси X нужна стрелка
    }

    protected void printWithScale_AxisY(@NonNull Canvas canvas, float xMin, float xMax,
                                        float yMin, float yMax,
                                        float endingOffset,
                                        float thickness,
                                        int scaleCount) {
        paint.setColor(Color.BLACK);
        canvas.drawLine(xMin, yMin, xMin, yMax - endingOffset, paint);

        // Отметки вдоль оси Y
        float axisSize = (yMax - endingOffset) - yMin;
        float scaleStep = axisSize / (scaleCount + 1);
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(0F, i * scaleStep, thickness, paint);
        }

        // Окончание - стрелка для оси
        // Для оси Y нужна просто черточка/точка
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xMin, axisSize, thickness * 2.5F, paint);
    }
    // \---------------------LEGACY------------------/

    protected void changeAxis(@NonNull Canvas canvas, float scale_parameter,
                              float xAxisLength, float yAxisLength) {

        // Version for class variant
        canvas.scale(scale_parameter / xAxisLength,
                -scale_parameter / yAxisLength);
    }

    protected void printWithScale_AxisX(@NonNull Canvas canvas,
                                        float xStart, float xFinish,
                                        float yStart, float yFinish,
                                        float thickness,
                                        int scaleCount) {
        // Version for class variant
        paint.setColor(Color.BLACK);
        canvas.drawLine(xStart, yStart, xFinish, yStart, paint);

        // Отметки вдоль оси X
        float axisSize = xFinish - xStart;
        float scaleStep = axisSize / (scaleCount + 1);
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(i * scaleStep, 0F, thickness, paint);
        }

        // Окончание - стрелка для оси
        // TODO: Для оси X нужна стрелка
    }

    protected void printWithScale_AxisY(@NonNull Canvas canvas,
                                        float xStart, float xFinish,
                                        float yStart, float yFinish,
                                        float thickness,
                                        int scaleCount) {
        // Version for class variant
        paint.setColor(Color.BLACK);
        canvas.drawLine(xStart, yStart, xFinish, yFinish, paint);

        // Отметки вдоль оси Y
        float axisSize = yFinish - yStart;
        float scaleStep = axisSize / (scaleCount + 1);
        paint.setColor(Color.BLACK);
        for (int i = 1; i <= scaleCount; i++) {
            canvas.drawCircle(0F, i * scaleStep, thickness, paint);
        }

        // Окончание - стрелка для оси
        // Для оси Y нужна просто черточка/точка
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xStart, axisSize, thickness * 2.5F, paint);
    }

    protected void printDiagramData(Canvas canvas, Map<Integer, Float> graph_data,
                                    float coordinateAxisZeroDot_x, float coordinateAxisZeroDot_y,
                                    float axisX_length, float axisY_length,
                                    int scaleCount_y) {
        // Исходя из личных предпочтений о "гармонии и красоте"
        // выбирается значение workspace_share (0 < workspace_share <= (axsiMax - axisMin)) -
        // это значение обозначает долю области от общего пространства,
        // которую может занимать горизонтальная полоска.
        // Максимальное значение в данных диаграмы заполняет workspace_share на 100%,
        // остальные значения длины полоски при отрисовке из данных пересчитываются
        // относительно макимума по формуле: (val / max_val) * workspace_share

        float workspace_share = 0.8F;
        float workspace_len = axisX_length * workspace_share;

        float distanceBetweenScales_y = axisY_length / (scaleCount_y + 1);

        int curScale;
        float curValue;
        float curLength;
        float distanceFromAxisOX;
        paint.setColor(Color.RED);

        float maxValue = getMaximumValueFromMap(graph_data);

        for (Map.Entry<Integer, Float> entry: graph_data.entrySet()) {
            curScale = entry.getKey();
            curValue = entry.getValue();

            curLength = (curValue / maxValue) * workspace_len;
            distanceFromAxisOX = distanceBetweenScales_y * curScale;

            canvas.drawLine(
                    coordinateAxisZeroDot_x,
                    coordinateAxisZeroDot_y + distanceFromAxisOX,
                    coordinateAxisZeroDot_x + curLength,
                    coordinateAxisZeroDot_y + distanceFromAxisOX,
                    paint
            );
        }
    }

    protected  <K, V extends Comparable<V>> V getMaximumValueFromMap(Map<K, V> targetMap) {
        V maxValue = null;

        for (Map.Entry<K, V> entry: targetMap.entrySet()) {
            if (maxValue == null || entry.getValue().compareTo(maxValue) > 0) {
                maxValue = entry.getValue();
            }
        }

        return maxValue;
    }

    protected void createWorkspace(Canvas canvas,
                                   float xAxisScale,
                                   float yAxisScale) {
        // float xAxisScale, float yAxisScale: задает макисмальное
        // значение сетки разметки по каждой из осей холста
        // (если xAxisScale = 1, то при рисовании линии куда либо
        //      в x > 1 линия уползет за экран)
        float width = getWidth();
        float height = getHeight();
        // what size chosen as standard
        float scale_parameter = Math.min(width, height);

        changeAxis(canvas, scale_parameter, xAxisScale, yAxisScale);
    }

    protected class GraphSettings {
        // TODO: void construcor, make setter for only one class allocate
        public GraphSettings(@NonNull Canvas canvas,
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

            printedRowThickness = printedRowThickness_;
        }

        private void printAxes() {
            printWithScale_AxisX(
                    workCanvas,
                    xOffsetFromOrigin, xOffsetFromOrigin + yAxisLength,
                    yOffsetFromOrigin, yOffsetFromOrigin + yAxisLength,
                    visualSettings.axisThickness, xScaleCount
            );
            printWithScale_AxisY(
                    workCanvas,
                    xOffsetFromOrigin, xOffsetFromOrigin + yAxisLength,
                    yOffsetFromOrigin, yOffsetFromOrigin + yAxisLength,
                    visualSettings.axisThickness, xScaleCount
            );
        }

        private void changeCoordinateSystemToGraph() {
            // Must be braced with Canvas save and restore methods!
            workCanvas.translate(xOffsetFromOrigin, yOffsetFromOrigin);
        }

        final private Canvas workCanvas;
        final private GraphDesignElements visualSettings;
        final public float xOffsetFromOrigin;
        final public float yOffsetFromOrigin;
        final public float xAxisLength;
        final public float yAxisLength;
        final public int xScaleCount;
        final public int yScaleCount;
        final public float printedRowThickness;
        // PLus colour presets;
    }

    protected class GraphDesignElements {
        public GraphDesignElements(float axisThickness_) {
            axisThickness = axisThickness_;
        }

        final public float axisThickness;
    }
}
