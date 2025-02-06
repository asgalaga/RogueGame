package org.example.testjavafx;

import java.net.URL;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameGridTwoController {

    @FXML
    private GridPane gameGrid; // Lien avec GridPane du FXML

    @FXML
    private Pane playerPane; // Pane pour le personnage

    @FXML
    private ImageView playerImage; // ImageView du personnage

    private int playerX = 58; // Position initiale en pixels
    private int playerY = 58;
    private final int TILE_SIZE = 58; // Taille d’une case
    private final int GRID_SIZE = 14; // Nombre de cases (12x12)

    private final int[][] mazeTwo = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1 },
            { 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 2, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    private int playerCol = 1; // Colonne initiale du joueur
    private int playerRow = 10; // Ligne initiale du joueur

    @FXML
    public void initialize() {
        System.out.println("GameGridTwoController chargé !");

        // Génération de la grille avec les images associées
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                String imagePath;
                switch (mazeTwo[row][col]) {
                    case 1 -> imagePath = "/images/wall.png"; // Mur
                    case 2 -> imagePath = "/images/door.png"; // Porte
                    default -> imagePath = "/images/floor.png"; // Sol
                }

                URL imgURL = getClass().getResource(imagePath);
                if (imgURL == null) {
                    System.err.println("❌ Image introuvable : " + imagePath);
                    continue;
                }

                ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                gameGrid.add(imageView, col, row);
            }
        }

        // Définir la position initiale du joueur
        playerImage.setLayoutX(playerCol * TILE_SIZE);
        playerImage.setLayoutY(playerRow * TILE_SIZE);

        System.out.println("✅ Position initiale : Col=" + playerCol + ", Row=" + playerRow);
        System.out.println("✅ Position en pixels : X=" + (playerCol * TILE_SIZE) + ", Y=" + (playerRow * TILE_SIZE));

        // Activation du clavier une fois la scène chargée
        playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case UP, W -> movePlayer(0, -1);
                        case DOWN, S -> movePlayer(0, 1);
                        case LEFT, A -> movePlayer(-1, 0);
                        case RIGHT, D -> movePlayer(1, 0);
                    }
                });
            }
        });
    }

    private void movePlayer(int deltaX, int deltaY) {
        int newCol = playerCol + deltaX;
        int newRow = playerRow + deltaY;

        // Vérifier les limites et empêcher de traverser les murs
        if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            if (mazeTwo[newRow][newCol] != 1) { // Vérifie si ce n'est pas un mur
                playerCol = newCol;
                playerRow = newRow;
                playerImage.setLayoutX(playerCol * TILE_SIZE);
                playerImage.setLayoutY(playerRow * TILE_SIZE);

                // Vérifie si le joueur atteint une porte (2)
                if (mazeTwo[newRow][newCol] == 2) {
                    System.out.println("🚪 Porte atteinte !");

                    // Vérifie si c'est la porte de retour ou d'avancement
                    if (newRow == 10 && newCol == 0) { // Coordonnées de la porte vers GameGridOne
                        System.out.println("🔄 Retour au niveau précédent...");
                        loadPreviousLevel();
                    } else {
                        System.out.println("➡️ Passage au niveau suivant...");
                        loadNextLevel();
                    }
                }
            } else {
                System.out.println("⛔ Mur détecté ! Déplacement interdit.");
            }
        }
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        playerImage.setLayoutX((playerCol) * TILE_SIZE);
        playerImage.setLayoutY((playerRow) * TILE_SIZE);
    }

    private void loadPreviousLevel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGridOne.fxml"));
            Pane previousRoot = loader.load();

            // Récupérer le contrôleur du niveau 1
            GameGridOneController controller = loader.getController();

            // Définir la position du joueur à la porte
            setPlayerPosition(12, 11); // Remplace ces valeurs par la position correcte

            Stage stage = (Stage) playerPane.getScene().getWindow();
            Scene previousScene = new Scene(previousRoot);
            stage.setScene(previousScene);
            stage.show();

            System.out.println("✅ Retour au niveau 1 réussi avec nouvelle position !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de GameGridOne.fxml !");
        }
    }

    private void loadNextLevel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameGridThree.fxml"));
            Pane nextRoot = loader.load();

            Stage stage = (Stage) playerPane.getScene().getWindow();
            Scene nextScene = new Scene(nextRoot);
            stage.setScene(nextScene);
            stage.show();

            System.out.println("✅ Chargement du niveau 3 réussi !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de GameGridThree.fxml !");
        }
    }
}
