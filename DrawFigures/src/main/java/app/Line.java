package app;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape{

    public Line(Color strokeColor, Point2D topLeft, Point2D rightBottom) {
        super(strokeColor, topLeft, rightBottom);
    }


    @Override
    public void draw(GraphicsContext  gc) {
        gc.setStroke(strokeColor);
        gc.strokeLine(topLeft.getX(), topLeft.getY(), rightBottom.getX(), rightBottom.getY());
    }
/*
    public Point2D getPointOne() {
        return pointOne;
    }

    public Point2D getPointTwo() {
        return pointTwo;
    }

    public void setPointOne(Point2D pointOne) {
        this.pointOne = pointOne;
    }

    public void setPointTwo(Point2D pointTwo) {
        this.pointTwo = pointTwo;
    }
*/
}

