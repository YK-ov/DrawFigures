package app;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends FilledShape {


    public Ellipse(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom, fillColor);
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
