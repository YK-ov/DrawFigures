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
        gc.setFill(fillColor);
        gc.fillOval(Math.min(topLeft.getX(), rightBottom.getX()),  // copy from rectangle code but with stroke and fill for Oval
                Math.min(topLeft.getY(), rightBottom.getY()),
                Math.abs(topLeft.getX() - rightBottom.getX()),
                Math.abs(topLeft.getY() - rightBottom.getY()));

        gc.setStroke(strokeColor);
        gc.strokeOval(Math.min(topLeft.getX(), rightBottom.getX()),
                Math.min(topLeft.getY(), rightBottom.getY()),
                Math.abs(topLeft.getX() - rightBottom.getX()),
                Math.abs(topLeft.getY() - rightBottom.getY()));


    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "fillColor=" + fillColor +
                ", strokeColor=" + strokeColor +
                ", topLeft=" + topLeft +
                ", rightBottom=" + rightBottom +
                '}';
    }
}
