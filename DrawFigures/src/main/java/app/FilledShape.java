package app;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

abstract class FilledShape extends Shape {
    protected Color fillColor;

    public FilledShape(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom);
        this.fillColor = fillColor;
    }
}