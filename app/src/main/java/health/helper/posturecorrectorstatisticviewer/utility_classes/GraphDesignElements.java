package health.helper.posturecorrectorstatisticviewer.utility_classes;

public class GraphDesignElements {
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

    final public float defaultValueRowLength = 0.4F;
    final public float workspaceValue = 0.8F;
}
