package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameGridOneController {

    @FXML
    private GridPane gameGrid; // Lien avec GridPane du FXML

    @FXML
    private Pane playerPane; // Pane pour le personnage

    @FXML
    private ImageView playerImage; // ImageView du personnage

    @FXML
    private ImageView monstre1; // ImageView du personnage

    private int playerX = 58; // Position initiale en pixels
    private int playerY = 58;
    private final int TILE_SIZE = 58; // Taille d’une case
    private final int GRID_SIZE = 14; // Nombre de cases (14x14)

    private final int[][] mazeOne = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1 },
            { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
            { 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }

    };

    @FXML
    public void initialize() {

        Game game = new Game();
        // System.out.println("GameGridOneController chargé !");

        // List<Point2D> freeTiles = new ArrayList<>();

        Game.maze = mazeOne;
        // Génération de la grille avec les images associées
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                String imagePath = "";

                switch (mazeOne[row][col]) {
                    case 0 -> Game.passages.add(new Point2D(col, row));
                    case 1 -> imagePath = "/images/wall.png"; // Mur
                    case 2 -> imagePath = "/images/door.png"; // Porte
                    default -> imagePath = "/images/floor.png"; // Sol
                }

                URL imgURL = getClass().getResource(imagePath);
                if (imgURL == null) {
                    // System.err.println("❌ Image introuvable : " + imagePath);
                    continue;
                }

                ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                gameGrid.add(imageView, col, row);
            }
        }

        game.fill(gameGrid);

        // Activation du clavier une fois la scène chargée
        playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case UP, W -> movePlayer(0, -1);
                        case DOWN, S -> movePlayer(0, 1);
                        case LEFT, A -> movePlayer(-1, 0);
                        case RIGHT, D -> movePlayer(1, 0);
                        default -> System.out.println("Unexpected value: " + event.getCode());
                    }
                });
            }
        });
    }

    public void setPlayerPosition(int col, int row) {
        this.playerX = col * TILE_SIZE;
        this.playerY = row * TILE_SIZE;

        // Mettre à jour l'affichage du personnage
        playerImage.setLayoutX(playerX);
        playerImage.setLayoutY(playerY);

        System.out.println("📍 Position du joueur définie à : " + col + ", " + row);
    }

    private void movePlayer(int deltaX, int deltaY) {
        int currentCol = playerX / TILE_SIZE;
        int currentRow = playerY / TILE_SIZE;
        int newCol = currentCol + deltaX;
        int newRow = currentRow + deltaY;

        // Vérifier les limites et éviter les murs
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            if (mazeOne[newRow][newCol] != 1) { // Vérifie si ce n'est pas un mur
                playerX = newCol * TILE_SIZE;
                playerY = newRow * TILE_SIZE;
                playerImage.setLayoutX(playerX);
                playerImage.setLayoutY(playerY);

                System.out.println("✅ Déplacement : " + newCol + ", " + newRow);

                if (mazeOne[newRow][newCol] == 2) {
                    System.out.println("🚪 Porte atteinte ! Passage au niveau suivant...");
                    loadNextLevel();
                }
            } else {
                System.out.println("⛔ Mur détecté !");
            }
        }
    }

    private void loadNextLevel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGridTwo.fxml"));
            Pane nextRoot = loader.load();

            Stage stage = (Stage) playerPane.getScene().getWindow();
            Scene nextScene = new Scene(nextRoot);
            stage.setScene(nextScene);
            stage.show();

            System.out.println("✅ Chargement du niveau 2 réussi !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de GameGridTwo.fxml !");
        }
    }
}
