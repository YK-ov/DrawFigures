package app;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape{

    private Point2D pointOne;
    private Point2D pointTwo;

    public Line(Point2D pointOne, Point2D pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
    }

    @Override
    public void draw(GraphicsContext  gc) {

    }

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
}
