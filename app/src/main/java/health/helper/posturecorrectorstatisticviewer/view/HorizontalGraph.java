package health.helper.posturecorrectorstatisticviewer.view;

import health.helper.posturecorrectorstatisticviewer.utility_classes.GraphDesignElements;
import health.helper.posturecorrectorstatisticviewer.utility_classes.ColourScheme;
import health.helper.posturecorrectorstatisticviewer.utility_classes.CustomGraph;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class HorizontalGraph extends View {

    // ---Data---\
    private Map<Integer, Float> graphData =
            Map.of(1, 5F, 2, 5F, 3, 5F, 4, 5F, 5, 5F, 6, 5F, 7, 5F, 8, 5F, 9, 5F, 10, 5F);
    // ---Data---/

    // ---Private members---\
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final GraphDesignElements designElements =
            new GraphDesignElements(
                    0.1F, 0.1F,
                    0.1F, 0.5F
            );
//    private CustomGraph customGraph = new CustomGraph(paint);
    private ColourScheme colourScheme = new ColourScheme();
    private CustomGraph customGraph = new CustomGraph(paint, colourScheme);
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
        paint.setAntiAlias(true);

        // Иначе все начинает наслаиваться
        // TODO: сделать пересчет толщин, когда полей слишком много ( >10 )
        assert (graphData.size() <= 10);

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
}
