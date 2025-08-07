package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.util.ArrayList;

public class AppController {
@FXML  // copy from view.fxml in the controller where the "fx:id" references are
    private Canvas canvas;

@FXML
    private Button newButton;
@FXML
    private Button openButton;
@FXML
    private Button saveButton;
@FXML
    private RadioButton lineButton;
@FXML
    private ToggleGroup Tool;
@FXML
    private RadioButton ellipseButton;
@FXML
    private RadioButton rectangleButton;
@FXML
    private ColorPicker strokeColorPicker;
@FXML
    private ColorPicker fillColorPicker;

    private Point2D pointOne;
    private Point2D pointTwo;
    private ArrayList<Point2D> points = new ArrayList<>();
    private Line currentLine;
    private Pane pane;


@FXML
    private void initialize() {

    //GraphicsContext gc = canvas.getGraphicsContext2D(); // from the fx:id


    handleButtons();
}

  /*
    public void ToolChangeListener(){
        Tool.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selected = (RadioButton) newValue;
                System.out.println(selected.getText() + " selected");

                currentLine = null;
            }

        });
    }

 */

    public void handleButtons() {
        lineButton.setOnAction(new EventHandler<ActionEvent>() {  // could be done with lambda expression (or statement?)
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.setOnMouseClicked(null);

                    canvas.setOnMouseClicked(event -> {
                        if (currentLine == null) {
                            pointOne = new Point2D(event.getX(), event.getY());
                            pointTwo = new Point2D(event.getX() + 50, event.getY());

                            currentLine = new Line(pointOne, pointTwo);
                            canvas.getGraphicsContext2D().setStroke(strokeColorPicker.getValue());
                            canvas.getGraphicsContext2D().strokeLine(currentLine.getPointOne().getX(), currentLine.getPointOne().getY(), currentLine.getPointTwo().getX(), currentLine.getPointTwo().getY());

                        } else {
                            currentLine = null;
                        }
                    });

                    canvas.setOnMouseMoved(event -> {
                        if (currentLine != null) {
                            Point2D endPoint = new Point2D(event.getX(), event.getY());

                            currentLine.setPointTwo(endPoint);
                        }
                    });
                    currentLine = null;

                    System.out.println("Line selected");

                }
        });

        ellipseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                canvas.setOnMouseClicked(null);  // to clear previous setOnAction from line (absolutely necessary)

                System.out.println("Ellipse selected");
            }
        });
        rectangleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvas.setOnMouseClicked(null);

                System.out.println("Rectangle selected");
            }
        });


    }



/*
    public void changeColor(ActionEvent event) {

        Color color = fillColorPicker.getValue();
    }
*/




}
