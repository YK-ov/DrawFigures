package app;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.MouseEvent;
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
    private EventHandler<MouseEvent> linePressedHandler;
    private EventHandler<MouseEvent> lineDraggedHandler;
    private EventHandler<MouseEvent> lineReleasedHandler;


@FXML
    private void initialize() {
    lineButton.setSelected(true);

    if (lineButton.isSelected()) {  // because line at first is always selected (always true)
                linePressedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        canvas.getGraphicsContext2D().beginPath();
                        canvas.getGraphicsContext2D().moveTo(mouseEvent.getX(), mouseEvent.getY());
                        canvas.getGraphicsContext2D().setStroke(strokeColorPicker.getValue());
                        //canvas.getGraphicsContext2D().fill();
                        canvas.getGraphicsContext2D().stroke();
                    }
                };


                lineDraggedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());  // - for free drawing, not straight line
                        //canvas.getGraphicsContext2D().closePath();
                        //canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                        //canvas.getGraphicsContext2D().stroke();
                    }
                };


                lineReleasedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                        //canvas.getGraphicsContext2D().fill();
                        canvas.getGraphicsContext2D().stroke();
                        canvas.getGraphicsContext2D().closePath();
                    }
                };
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, linePressedHandler);
                canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, lineDraggedHandler);
                canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, lineReleasedHandler);

                System.out.println("Line selected");

            }




    //GraphicsContext gc = canvas.getGraphicsContext2D(); // from the fx:id



    lineButton.setSelected(true);
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
                linePressedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        canvas.getGraphicsContext2D().beginPath();
                        canvas.getGraphicsContext2D().moveTo(mouseEvent.getX(), mouseEvent.getY());
                        canvas.getGraphicsContext2D().setStroke(strokeColorPicker.getValue());
                        //canvas.getGraphicsContext2D().fill();
                        canvas.getGraphicsContext2D().stroke();
                    }
                };


                lineDraggedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());  // - for free drawing, not straight line
                        //canvas.getGraphicsContext2D().closePath();
                        //canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                        //canvas.getGraphicsContext2D().stroke();
                    }
                };


                lineReleasedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        canvas.getGraphicsContext2D().lineTo(mouseEvent.getX(), mouseEvent.getY());
                        //canvas.getGraphicsContext2D().fill();
                        canvas.getGraphicsContext2D().stroke();
                        canvas.getGraphicsContext2D().closePath();
                    }
                };
                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, linePressedHandler);
                canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, lineDraggedHandler);
                canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, lineReleasedHandler);

                System.out.println("Line selected");

            }
        });

        ellipseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (linePressedHandler != null) {
                    System.out.println("pressed not null");
                    canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, linePressedHandler);  // for cleaning (necessary)
                    linePressedHandler = null;
                }
                if (lineDraggedHandler != null) {
                    System.out.println("dragged not null");
                    canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, lineDraggedHandler);
                    lineDraggedHandler = null;
                }
                if (lineReleasedHandler != null) {
                    System.out.println("released not null");
                    canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, lineReleasedHandler);
                    lineReleasedHandler = null;
                }

                System.out.println("Ellipse selected");
            }
        });
        rectangleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (linePressedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, linePressedHandler);  // for cleaning (necessary)
                    linePressedHandler = null;
                }
                if (lineDraggedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, lineDraggedHandler);
                    lineDraggedHandler = null;
                }
                if (lineReleasedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, lineReleasedHandler);
                    lineReleasedHandler = null;
                }

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
