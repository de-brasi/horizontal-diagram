package health.helper.posturecorrectorstatisticviewer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

public class HorizontalGraph extends View {

    // ---Data---\
    private Map<Integer, Float> graphData =
            Map.of(1, 1F, 2, 0F, 3, 3F);
    // ---Data---/

    // ---Private members---\
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final GraphDesignElements designElements =
            new GraphDesignElements(
                    0.1F, 0.1F,
                    0.1F, 0.5F
            );
    private CustomGraph customGraph = new CustomGraph();
    // ---Private members---/

    public HorizontalGraph(Context context) {
        super(context);

    }

    public HorizontalGraph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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


        createWorkspace(canvas, 10F, 10F);
        customGraph.setSettings(
                canvas, designElements, 0.5F, 0.5F,
                10F - 1F, 10F - 1F,
                0, graphData.size(), 1F
        );

        // printAxes after diagram data according expected draw order
        customGraph.printDiagramData(graphData);
        customGraph.printAxes();


        // ---Diagram printing end---
        canvas.restore();
    }

    protected void changeAxis(@NonNull Canvas canvas, float scale_parameter,
                              float xAxisLength, float yAxisLength) {
        canvas.translate(0F, canvas.getHeight());
        canvas.scale(scale_parameter / xAxisLength,
                -scale_parameter / yAxisLength);
    }

    protected  <K, V extends Comparable<V>> V getMaximumValueFromMap(@NonNull Map<K, V> targetMap) {
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

    protected class CustomGraph {
        public CustomGraph() {}

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

            // TODO: ending of data-row

            paint.setStrokeWidth(visualSettings.dataRowThickness);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);

            float workspace_share = designElements.workspaceValue;
            float workspace_len = xAxisLength * workspace_share;

            float distanceBetweenScales_y = yAxisLength / (yScaleCount + 1);

            int curScale;
            float curValue;
            float curLength;
            float distanceFromAxisOX;

            float maxValue = getMaximumValueFromMap(graphData);

            for (Map.Entry<Integer, Float> entry: graphData.entrySet()) {
                curScale = entry.getKey();
                curValue = entry.getValue();

                curLength = (curValue / maxValue) * workspace_len;
                curLength = Math.max(curLength, designElements.defaultValueRowLength);
                distanceFromAxisOX = distanceBetweenScales_y * curScale;

                workCanvas.drawRoundRect(
                        xOffsetFromOrigin, yOffsetFromOrigin + distanceFromAxisOX + designElements.dataRowThickness / 2,
                        xOffsetFromOrigin + curLength, yOffsetFromOrigin + distanceFromAxisOX - designElements.dataRowThickness / 2,
                        0.1F, 0.1F,
                        paint
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

        private Canvas workCanvas;
        private GraphDesignElements visualSettings;
        public float xOffsetFromOrigin;
        public float yOffsetFromOrigin;
        public float xAxisLength;
        public float yAxisLength;
        public int xScaleCount;
        public int yScaleCount;
        public float printedRowThickness;
        // PLus colour presets;
    }

    protected class GraphDesignElements {
        public GraphDesignElements(
                float axisThickness_,
                float xAxisDotRadius_,
                float yAxisDotRadius_,
                float dataRowThickness_) {
            axisThickness = axisThickness_;
            xAxisDotRadius = xAxisDotRadius_;
            yAxisDotRadius = yAxisDotRadius_;
            dataRowThickness = dataRowThickness_;
        }

        final public float axisThickness;

        final public float xAxisDotRadius;
        final public float yAxisDotRadius;

        final public float dataRowThickness;

        final public float defaultValueRowLength = 0.3F;
        final public float workspaceValue = 0.8F;
    }
}
