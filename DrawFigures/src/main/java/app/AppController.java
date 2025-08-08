package app;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
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
import java.util.List;

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

    private EventHandler<MouseEvent> linePressedHandler;
    private EventHandler<MouseEvent> lineDraggedHandler;  // only for free drawing (which is not used in main project at all)
    private EventHandler<MouseEvent> lineReleasedHandler;
    private EventHandler<MouseEvent> rectangleButtonPressedHandler;
    private EventHandler<MouseEvent> rectangleButtonReleasedHandler;
    private EventHandler<MouseEvent> ellipseButtonPressedHandler;
    private EventHandler<MouseEvent> ellipseButtonReleasedHandler;

    private Point2D rectangleStart;
    private Point2D lineStart;
    private Point2D ellipseStart;


@FXML
    private void initialize() {
    lineButton.setSelected(true);

    if (lineButton.isSelected()) {  // because lineButton is always selected at the start of the program (always true)
        linePressedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Point2D firstPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                lineStart = firstPoint;
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
                Point2D lastPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());

                Line line = new Line(strokeColorPicker.getValue(), lineStart, lastPoint);
                line.draw(canvas.getGraphicsContext2D());
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, linePressedHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, lineDraggedHandler);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, lineReleasedHandler);

        System.out.println("Line selected");
    }

        //GraphicsContext gc = canvas.getGraphicsContext2D(); // from the fx:id

        handleButtons();
}

            public void handleButtons() {
                lineButton.setOnAction(new EventHandler<ActionEvent>() {  // could be done with lambda expression (or statement?)
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        clearHandlers();

                        linePressedHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Point2D firstPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                                lineStart = firstPoint;
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
                                Point2D lastPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());

                                Line line = new Line(strokeColorPicker.getValue(), lineStart, lastPoint);
                                line.draw(canvas.getGraphicsContext2D());
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
                        clearHandlers();
                        ellipseButtonPressedHandler = new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Point2D firstPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                                ellipseStart = firstPoint;
                            }
                        };
                        ellipseButtonReleasedHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Point2D lastPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                                Ellipse ellipse = new Ellipse(strokeColorPicker.getValue(), ellipseStart, lastPoint, fillColorPicker.getValue());
                                ellipse.draw(canvas.getGraphicsContext2D());
                            }
                        };
                        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, ellipseButtonPressedHandler);
                        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, ellipseButtonReleasedHandler);

                        System.out.println("Ellipse selected");
                    }
                });

                rectangleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        clearHandlers();

                        rectangleButtonPressedHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Point2D firstClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                                rectangleStart = firstClick;
                            }
                        };

                        rectangleButtonReleasedHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                Point2D secondClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                                Rectangle rectangle = new Rectangle(strokeColorPicker.getValue(), rectangleStart, secondClick, fillColorPicker.getValue());
                                rectangle.draw(canvas.getGraphicsContext2D());
                            }
                        };
                        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, rectangleButtonPressedHandler);
                        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, rectangleButtonReleasedHandler);

                        System.out.println("Rectangle selected");
                    }
                });


            }

            public void clearHandlers() {
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

                if (rectangleButtonPressedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, rectangleButtonPressedHandler);
                    rectangleButtonPressedHandler = null;
                }
                if (rectangleButtonReleasedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, rectangleButtonReleasedHandler);
                    rectangleButtonReleasedHandler = null;
                }

                if (ellipseButtonPressedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, ellipseButtonPressedHandler);
                    ellipseButtonPressedHandler = null;
                }
                if (ellipseButtonReleasedHandler != null) {
                    canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, ellipseButtonReleasedHandler);
                    ellipseButtonReleasedHandler = null;
                }

            }

}

