package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    @FXML
    private Button btnJouer;

    @FXML
    private Button btnSortir;

    @FXML
    private void initialize() {
        // Vérifier si les boutons existent bien
        if (btnJouer != null) {
            btnJouer.setOnAction(this::handlePlayButton);
        }
        if (btnSortir != null) {
            btnSortir.setOnAction(e -> closeApplication());
        }
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {
        try {
            // Charger la fenêtre du jeu (GameGridOne.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGridOne.fxml"));
            Parent gameRoot = loader.load();

            // Changer la scène actuelle pour afficher le jeu
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 1000, 920));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeApplication() {
        Stage stage = (Stage) btnSortir.getScene().getWindow();
        stage.close();
        System.exit(0); // Quitter complètement l'application
    }
}
