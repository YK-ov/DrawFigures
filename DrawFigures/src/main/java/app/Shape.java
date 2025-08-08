package app;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected Color strokeColor;
    protected Point2D topLeft;
    protected Point2D rightBottom;


    public Shape(Color strokeColor, Point2D topLeft, Point2D rightBottom) {
        this.strokeColor = strokeColor;
        this.topLeft = topLeft;
        this.rightBottom = rightBottom;
    }

    public abstract void draw(GraphicsContext gc);

}
