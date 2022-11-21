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
            Map.of(1, 2F, 2, 3F, 4, 4F);
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
        float axisX_offset = 0.5F;  // отступ начала координат в нормальной системе координат
        float axisY_offset = -2F;   // отступ начала координат в нормальной системе координат

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(asxisStrokeWidth);

        canvas.save();

        // ---Diagram printing start---
        float xMax = 10F;   // axis X size
        float xMin = 0F;
        float yMax = 10F;   // axis Y size
        float yMin = 0F;

        float width = getWidth();
        float height = getHeight();
        float scale_parameter = Math.min(width, height); // what size chosen as standard

        changeAxis(canvas, scale_parameter, xMin, xMax, yMin, yMax, axisX_offset, axisY_offset);

        // axis X and Y
        printWithScale_AxisX(
                canvas, xMin, xMax, yMin, yMax,
                1F, asxisStrokeWidth, 4);
        printWithScale_AxisY(
                canvas, xMin, xMax, yMin, yMax,
                1F, asxisStrokeWidth, 4);

        // Graph data printing
        printDiagramData(canvas, graph_data, xMax, xMin, yMax, yMin);

        // ---Diagram printing end---

        canvas.restore();
    }

    protected void changeAxis(@NonNull Canvas canvas, float scale_parameter,
                              float xMin, float xMax, float yMin, float yMax,
                              float xOffset, float yOffset) {
        canvas.scale(scale_parameter / (xMax - xMin),
                -scale_parameter / (yMax - yMin));
        canvas.translate(-xMin + xOffset, -yMax + yOffset);
    }

    protected void printWithScale_AxisX(@NonNull Canvas canvas, float xMin, float xMax,
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

    protected void printDiagramData(Canvas canvas, Map<Integer, Float> graph_data,
                                    float xMax, float xMin,
                                    float yMax, float yMin) {

    }
}
