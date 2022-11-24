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
    // TODO: запрет на чрезмерное сужение или подстраивать формат графика под это

    // ---Data---\
    private Map<Integer, Float> graphData =
            Map.of(1, 1F, 2, 2F, 3, 3F, 4, 4F, 5, 5F, 6, 6F, 7, 7F, 8, 8F, 9, 9F, 10, 10F);
    // ---Data---/

    // ---Private members---\
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final GraphDesignElements designElements = new GraphDesignElements(
            0.1F, 0.1F,
            0.1F, 0.5F);
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

        // TODO: сделать пересчет толщин, когда полей слишком много ( >10 )
        // Иначе все начинает наслаиваться
        assert (graphData.size() <= 10);

        canvas.save();

//        createWorkspace(canvas, 10F, 10F);
        createWorkspace(canvas, 10F);
        customGraph.setSettings(
                canvas, designElements, 1.5F, 1.5F,
                10F - 2.5F, 10F - 2.5F,
                5, graphData.size(), 1F
        );

        // TODO: (WARNING!) сделать разное количество рисок по каждой оси;
        //  отрисовывать согласно новому количеству рисок.
        // printAxes after diagram data according expected draw order
        customGraph.printDiagramData(graphData);
        customGraph.printAxes();

        canvas.restore();
    }

    protected void changeAxis(@NonNull Canvas canvas, float scale_parameter,
                              float xAxisLength, float yAxisLength) {
        // Масштабирование вьюхи как квадрат

        // Проблема - при экстремальной диформации освобождается
        // чрезмерно много места либо сверху,
        // либо справа (в зависимости от деформации)
        canvas.translate(0F, canvas.getHeight());
        canvas.scale(scale_parameter / xAxisLength,
                -scale_parameter / yAxisLength);
    }

    protected void changeAxis(@NonNull Canvas canvas,
                              float scaleX, float scaleY,
                              float xAxisLength, float yAxisLength) {
        // Масштабирование идет по каждой стороне отдельно
        // При использовании этой функции содержимое
        // вьюхи масштабируется так же как и формы

        // Проблема - плющит линии толщины осей при
        // перекосе масштабирования по одной из осей

        // TODO: что делаю: по каждой из осей будет длина не 10F,
        //  а пропорциональная абсолютной длине
        canvas.translate(0F, canvas.getHeight());
        canvas.scale(scaleX / xAxisLength,
                -scaleY / yAxisLength);
    }

    protected void createWorkspace(@NonNull Canvas canvas,
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

        paint.setColor(Color.rgb(
                colourScheme.BACKGROUND.red, colourScheme.BACKGROUND.green, colourScheme.BACKGROUND.blue
        ));
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(0, 0, width, height, 75, 75, paint);

//        changeAxis(canvas, scale_parameter, xAxisScale, yAxisScale);
        changeAxis(canvas, width, height, xAxisScale, yAxisScale);
    }

    protected void createWorkspace(@NonNull Canvas canvas,
                                   float minimumAxisScale) {
        // float minimumAxisScale: задает шкалу
        // для минимальной оси. Другая ось пересчитывается согласно шкале минимальной
        // (по итогу даже при масштабировании нарисованные фигуры не будут масштабироваться)

        // TODO: что делаю: одинкаовые единичные отрезки для каждой оси даже при масштабировании
        float width = getWidth();
        float height = getHeight();
        // what size chosen as standard
        float scale_parameter = Math.min(width, height);

        paint.setColor(Color.rgb(
                colourScheme.BACKGROUND.red, colourScheme.BACKGROUND.green, colourScheme.BACKGROUND.blue
        ));
        paint.setStrokeWidth(20);
        canvas.drawRoundRect(0, 0, width, height, 75, 75, paint);

//        changeAxis(canvas, scale_parameter, xAxisScale, yAxisScale);
        changeAxis(canvas, width, height, xAxisScale, yAxisScale);
    }
}
