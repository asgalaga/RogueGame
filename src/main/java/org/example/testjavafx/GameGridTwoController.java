package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class GameGridTwoController extends Level {
    @FXML
    private GridPane gameGrid; // Lien avec GridPane du FXML

    @FXML
    private Pane playerPane; // Pane pour le personnage

    @FXML
    private ImageView playerImage; // ImageView du personnage

    @FXML
    private ImageView heart1; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart2; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart3; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart4; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart5; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart6; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart7; // Lien avec GridPane du FXML
    @FXML
    private ImageView heart8; // Lien avec GridPane du FXML
    @FXML
    private Label experience;
    @FXML
    private Label experienceNeed;
    @FXML
    private Label keysLabel; // Lien avec GridPane du FXML
    @FXML
    private ImageView helm;
    @FXML
    private ImageView chest;
    @FXML
    private ImageView weapon;
    @FXML
    private ImageView botte;
    @FXML
    private ImageView glove;
    @FXML
    private ImageView shield;

    private final int GRID_SIZE = 14; // Nombre de cases (12x12)

    private final int[][] mazeTwo = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 9, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1 },
            { 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 6, 9, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };
    public static Game game;

    @FXML
    public void initialize() {
        // Définir le prochain niveau
        next = "/GameGridThree.fxml";

        // S'assurer que tout est propre avant de commencer
        if (game != null && Game.level != null) {
            game = null;
            Game.level = null;
        }

        // Créer une nouvelle instance de Game
        game = new Game();

        // Initialiser le niveau
        Game.maze = mazeTwo.clone(); // Faire une copie de la matrice
        Game.level = this;

        // Position initiale du joueur
        int playerStartX = 10, playerStartY = 1;

        // Remplir le niveau
        game.fill(GRID_SIZE, gameGrid, playerStartX, playerStartY,
                "/images/monstre/monstre4.png",
                "/images/monstre/monstre5.png",
                "/images/monstre/monstre8.png");

        System.out.println("✅ Niveau 2 chargé, état initial :");
        afficherMaze();

        // Démarrer le mouvement des monstres
        game.startMonsterMovement();

        // Configurer le joueur
        Player.game = game;
        Player.row = playerStartX;
        Player.col = playerStartY;
        Player.place(playerStartX, playerStartY);

        restoreEquippedItems();
        // Mettre à jour l'interface
        game.showHearts();
        Player.listen(playerPane);
    }

    public void restoreEquippedItems() {
        System.out.println("🔄 Restauration des équipements du joueur...");

        try {
            helm.setImage(new Image(getClass().getResourceAsStream(Player.helmImage)));
            chest.setImage(new Image(getClass().getResourceAsStream(Player.chestImage)));
            weapon.setImage(new Image(getClass().getResourceAsStream(Player.weaponImage)));
            botte.setImage(new Image(getClass().getResourceAsStream(Player.botteImage)));
            glove.setImage(new Image(getClass().getResourceAsStream(Player.gloveImage)));
            shield.setImage(new Image(getClass().getResourceAsStream(Player.shieldImage)));

            System.out.println("✅ Équipements restaurés !");
        } catch (Exception e) {
            System.err.println("❌ ERREUR : Impossible de restaurer les équipements !");
            e.printStackTrace();
        }
    }

    public void setPlayerStartPosition(int startRow, int startCol) {
        Player.row = startRow;
        Player.col = startCol;
        Player.place(startRow, startCol);
    }

    private void afficherMaze() {
        System.out.println("État de la matrice :");
        for (int i = 0; i < mazeTwo.length; i++) {
            for (int j = 0; j < mazeTwo[i].length; j++) {
                System.out.print(mazeTwo[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Label getExperience() {
        return experience;
    }

    public Label getExperienceNeed() {
        return experienceNeed;
    }

    public GridPane getGameGrid() {
        return gameGrid;
    }

    public Pane getPlayerPane() {
        return playerPane;
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }

    public Label getKeys() {
        return keysLabel;
    }

    public ImageView getHeart(int index) {
        switch (index) {
            case 1:
                return heart1;
            case 2:
                return heart2;
            case 3:
                return heart3;
            case 4:
                return heart4;
            case 5:
                return heart5;
            case 6:
                return heart6;
            case 7:
                return heart7;
            case 8:
                return heart8;
            default:
                return null;
        }
    }
}
