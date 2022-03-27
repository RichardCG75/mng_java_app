package app.unedl.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private BorderPane borderScene;

    @FXML
    private VBox leftArea;

    @FXML
    private TextArea textArea;

    public HelloController() {
    }

    @FXML
    protected void onAlumnoButtonClick() {
        textArea.setText("alumno vista");

        VBox layout1 = new VBox(50);
        layout1.getChildren().add(new Button("vista alumno!"));

        this.borderScene.setLeft(layout1);

        int a=0;

    }


    @FXML
    protected void onProfesorButtonClick() {
        textArea.setText("profe vista");

        VBox layout1 = new VBox(50);
        layout1.getChildren().add(new Button("vista profe!"));

        this.borderScene.setLeft(layout1);

        int a=0;
    }
}