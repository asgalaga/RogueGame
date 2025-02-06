package org.example.testjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("exports")
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Cursed Run");

        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 890, 950);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
