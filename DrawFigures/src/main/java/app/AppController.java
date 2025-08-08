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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private EventHandler<MouseEvent> newButtonPressedHandler;

    private Point2D rectangleStart;
    private Point2D lineStart;
    private Point2D ellipseStart;
    private File selectedFile;

    private List<Shape> figures = new ArrayList<>();


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

        handleDrawButtons();
        handleMenuButtons();
    }

    public void handleDrawButtons() {
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
                        figures.add(line);
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
                        figures.add(ellipse);
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
                        figures.add(rectangle);
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

    public String escapeSpecialCharacters(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        String escapedText = text.replaceAll("\\R", " ");
        if (escapedText.contains(",") || escapedText.contains("\"") || escapedText.contains("'")) {
            escapedText = escapedText.replace("\"", "\"\"");
            escapedText = "\"" + escapedText + "\"";
        }
        return escapedText;
    }

    public void handleMenuButtons() {
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearHandlers();  // just to be sure even though not needed (not sure)

                canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                if (lineButton.isSelected()) {
                    clearHandlers();  // to check
                    System.out.println("Line selected still");
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
                }

                if (ellipseButton.isSelected()) {
                    clearHandlers();
                    System.out.println("Ellipse selected still");

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
                            figures.add(ellipse);
                        }
                    };
                    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, ellipseButtonPressedHandler);
                    canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, ellipseButtonReleasedHandler);
                }

                if (rectangleButton.isSelected()) {
                    clearHandlers();
                    System.out.println("Rectangle selected still");

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
                            figures.add(rectangle);
                        }
                    };
                    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, rectangleButtonPressedHandler);
                    canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, rectangleButtonReleasedHandler);
                }

            }
        });

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open a CSV file");
                FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
                fileChooser.getExtensionFilters().add(csvFilter);

                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                //fileChooser.setInitialDirectory(new File("/C:/Users/User/Desktop"));  // first / before C and : after C along with Users and User are absolutely necessary
                selectedFile = fileChooser.showOpenDialog(canvas.getScene().getWindow());

                String line = "";

                if (selectedFile != null) {
                    System.out.println(selectedFile.getPath() + " path");
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(selectedFile.getPath()));
                        while ((line = br.readLine()) != null) {
                            fromCsvLine(line);

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }


            }

        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedFile == null) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open a CSV file");
                    FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
                    fileChooser.getExtensionFilters().add(csvFilter);

                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    //fileChooser.setInitialDirectory(new File("/C:/Users/User/Desktop"));  // first / before C and : after C along with Users and User are absolutely necessary
                    selectedFile = fileChooser.showOpenDialog(canvas.getScene().getWindow());
                } else {
                    try (PrintWriter writer = new PrintWriter(selectedFile)) {
                        for (int i = 0; i < figures.size(); i++) {
                            writer.println(figures.get(i).toString() + ",");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Saved to " + selectedFile.getPath());
                }


            }


        });


    }

    public void fromCsvLine(String line) {
        String[] parts = line.split(",");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts[0].contains("Line")) {
            parts[0] = parts[0].replace("Line{strokeColor=", "");
            Color strokeLineColor = Color.web(parts[0]);
            parts[1] = parts[1].replace("topLeft=Point2D [x = ", "");
            parts[2] = parts[2].replace("y = ", "");
            parts[2] = parts[2].replace("]", "");
            parts[3] = parts[3].replace("rightBottom=Point2D [x = ", "");
            parts[4] = parts[4].replace("y = ", " ");
            parts[4] = parts[4].replace("]}", "");

            double firstCordXDouble = Double.parseDouble(parts[1]);
            double firstCordYDouble = Double.parseDouble(parts[2]);
            double secondCordXDouble = Double.parseDouble(parts[3]);
            double secondCordYDouble = Double.parseDouble(parts[4]);
            Point2D firstLineCoordinate  = new Point2D(firstCordXDouble, firstCordYDouble);
            Point2D secondLineCoordinate = new Point2D(secondCordXDouble, secondCordYDouble);

            Line newLine = new Line(strokeLineColor, firstLineCoordinate, secondLineCoordinate);
            newLine.draw(canvas.getGraphicsContext2D());
            figures.add(newLine);
        }

        if (parts[0].contains("Ellipse")) {
            parts[0] = parts[0].replace("Ellipse{fillColor=", "");
            Color fillEllipseColor = Color.web(parts[0]);
            parts[1] = parts[1].replace("strokeColor=", "");
            Color strokeEllipseColor = Color.web(parts[1]);
            parts[2] = parts[2].replace("topLeft=Point2D [x = " , "");
            parts[3] = parts[3].replace("y = ", "");
            parts[3] = parts[3].replace("]", "");
            parts[4] = parts[4].replace("rightBottom=Point2D [x = ", "");
            parts[5] = parts[5].replace("y = ", " ");
            parts[5] = parts[5].replace("]}", "");

            double firstCordXDouble = Double.parseDouble(parts[2]);
            double firstCordYDouble = Double.parseDouble(parts[3]);
            double secondCordXDouble = Double.parseDouble(parts[4]);
            double secondCordYDouble = Double.parseDouble(parts[5]);

            Point2D firstEllipseCoordinate  = new Point2D(firstCordXDouble, firstCordYDouble);
            Point2D secondEllipseCoordinate = new Point2D(secondCordXDouble, secondCordYDouble);

            Ellipse newEllipse = new Ellipse(strokeEllipseColor, firstEllipseCoordinate, secondEllipseCoordinate, fillEllipseColor);
            newEllipse.draw(canvas.getGraphicsContext2D());
            figures.add(newEllipse);
        }

        if (parts[0].contains("Rectangle")) {
            for (int i = 0; i < parts.length; i++) {
                System.out.println(parts[i] + " from parts" + i);
            }
            parts[0] = parts[0].replace("Rectangle{fillColor=", "");
            Color fillRectangleColor = Color.web(parts[0]);
            parts[1] = parts[1].replace("strokeColor=", "");
            Color strokeRectangleColor = Color.web(parts[1]);
            parts[2] = parts[2].replace("topLeft=Point2D [x = " , "");
            parts[3] = parts[3].replace("y = ", "");
            parts[3] = parts[3].replace("]", "");
            parts[4] = parts[4].replace("rightBottom=Point2D [x = ", "");
            parts[5] = parts[5].replace("y = ", " ");
            parts[5] = parts[5].replace("]}", "");

            double firstCordXDouble = Double.parseDouble(parts[2]);
            double firstCordYDouble = Double.parseDouble(parts[3]);
            double secondCordXDouble = Double.parseDouble(parts[4]);
            double secondCordYDouble = Double.parseDouble(parts[5]);

            Point2D firstRectangleCoordinate  = new Point2D(firstCordXDouble, firstCordYDouble);
            Point2D secondRectangleCoordinate = new Point2D(secondCordXDouble, secondCordYDouble);

            Rectangle newRectangle = new Rectangle(strokeRectangleColor, firstRectangleCoordinate, secondRectangleCoordinate, fillRectangleColor);
            newRectangle.draw(canvas.getGraphicsContext2D());
            figures.add(newRectangle);
        }

    }


}



