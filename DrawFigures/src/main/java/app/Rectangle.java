package app;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends FilledShape {


    public Rectangle(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom, fillColor);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.fillRect(Math.min(topLeft.getX(), rightBottom.getX()),
        Math.min(topLeft.getY(), rightBottom.getY()),
        Math.abs(topLeft.getX() - rightBottom.getX()),
        Math.abs(topLeft.getY() - rightBottom.getY()));

        gc.setStroke(strokeColor);
        gc.strokeRect(Math.min(topLeft.getX(), rightBottom.getX()),
                Math.min(topLeft.getY(), rightBottom.getY()),
                Math.abs(topLeft.getX() - rightBottom.getX()),
                Math.abs(topLeft.getY() - rightBottom.getY()));

    }

}
