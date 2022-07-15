package app.unedl.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import app.unedl.utils.*;

public class UnedlApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(UnedlApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("UNEDL Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBConenction.startDefaultConnection();
        launch();
    }
}