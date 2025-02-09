package org.example.testjavafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VictoryController {
    @FXML
    private VBox victoryPane;
    @FXML
    private Button playButton, quitButton;

    @FXML
    public void initialize() {
        System.out.println("🎉 VictoryController chargé !");
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {
        try {
            // Réinitialiser toutes les variables statiques
            resetGameState();

            // Charger la fenêtre du jeu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGridOne.fxml"));
            Parent gameRoot = loader.load();

            // Changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 1000, 920));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du redémarrage du jeu !");
        }
    }

    @FXML
    private void closeApplication() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        System.exit(0); // Quitter complètement l'application
    }

    private void resetGameState() {
        // Réinitialiser les variables du joueur
        Player.life = 5;
        Player.key = false;
        Player.dead = false;
        Player.win = false;
        Player.row = 1;
        Player.col = 1;
        Player.game = null; // S'assurer que l'ancienne instance est supprimée

        // Réinitialiser l'état du jeu
        Game.resetGame();

        // Créer une nouvelle instance de Game
        Player.game = new Game();

        System.out.println("✅ État du jeu réinitialisé");
    }

    @FXML
    private void handleQuitButton() {
        System.out.println("❌ Quitter le jeu");
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}