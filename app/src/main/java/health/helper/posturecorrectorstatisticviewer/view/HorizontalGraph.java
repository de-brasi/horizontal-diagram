package health.helper.posturecorrectorstatisticviewer.view;

import health.helper.posturecorrectorstatisticviewer.utility_classes.GraphDesignElements;
import health.helper.posturecorrectorstatisticviewer.utility_classes.ColourScheme;
import health.helper.posturecorrectorstatisticviewer.utility_classes.CustomGraph;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

public class HorizontalGraph extends View {
    // Data
    private Map<Integer, Float> graphData =
            Map.of(1, 1F, 2, 2F, 3, 3F, 4, 4F, 5, 5F, 6, 6F, 7, 7F, 8, 8F, 9, 9F, 10, 10F);

    private final GraphDesignElements designElements = new GraphDesignElements(
            0.1F, 0.1F,
            0.1F, 0.5F);
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final ColourScheme colourScheme = new ColourScheme();
    private final CustomGraph customGraph = new CustomGraph(paint, colourScheme);

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
        // TODO: сделать пересчет толщин, когда полей слишком много ( >10 )
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        assert (graphData.size() <= 10);    // Иначе все начинает наслаиваться

        canvas.save();

        createWorkspace(canvas, customGraph);

        customGraph.setSettings(
                canvas, designElements, 1.5F, 1.5F,
                2F, 2F,
                5, graphData.size(), 1F
        );

        customGraph.printDiagramData(graphData);
        customGraph.printAxes();

        canvas.restore();
    }

    protected void changeAxis(@NonNull Canvas canvas,
                              float scaleX, float scaleY,
                              float xAxisLength, float yAxisLength) {
        canvas.translate(0F, canvas.getHeight());
        canvas.scale(scaleX / xAxisLength, -scaleY / yAxisLength);
    }

    protected void createWorkspace(@NonNull Canvas canvas, CustomGraph customGraph) {
        final float width = getWidth();
        final float height = getHeight();

        // Make background
        paint.setStrokeWidth(20);
        paint.setColor(Color.rgb(
                colourScheme.BACKGROUND.red,
                colourScheme.BACKGROUND.green,
                colourScheme.BACKGROUND.blue));
        canvas.drawRoundRect(0, 0, width, height, 75, 75, paint);

        final float STANDARD_SCALE_VALUE = 10F; // константа - максимальное отрисовываемое расстояние для min стороны

        float scaleX, scaleY;

        if (height > width) {   // Высота больше ширины - вводим шкалу в STANDARD_SCALE_VALUE для оси x
            scaleX = STANDARD_SCALE_VALUE;
            scaleY = (height / width) * STANDARD_SCALE_VALUE;
        } else {    // Ширина больше или равна высоте - вводим шкалу в STANDARD_SCALE_VALUE для оси y
            scaleY = STANDARD_SCALE_VALUE;
            scaleX = (width / height) * STANDARD_SCALE_VALUE;
        }

        customGraph.setWorkspaceSize(scaleX, scaleY);
        changeAxis(canvas, width, height, scaleX, scaleY);
    }
}
