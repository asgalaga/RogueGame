package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class GameGridOneController extends Level {
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
    private Label keysLabel; // Lien avec GridPane du FXML
    @FXML
    private Label experience;
    @FXML
    private Label experienceNeed;

    public final int TILE_SIZE = 58; // Taille d’une case
    public final int GRID_SIZE = 14; // Nombre de cases (14x14)

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
    public static Game game;

    @FXML
    public void initialize() {
        next = "/GameGridTwo.fxml";
        game = new Game();

        // On associe la matrice et le niveau
        Game.maze = mazeOne;
        Game.level = this;

        // Position de départ du joueur en cases (ligne, colonne)
        int playerStartRow = 1;
        int playerStartCol = 1;

        // On remplace ici playerStartX, playerStartY par nos row, col.
        // (Si la signature de fill(...) est (int gridSize, GridPane, int px, int py,
        // ...)
        // alors renommez px, py en row, col dans la méthode fill aussi.)
        game.fill(
                GRID_SIZE,
                gameGrid,
                playerStartRow, // <-- Au lieu de playerStartX
                playerStartCol, // <-- Au lieu de playerStartY
                "/images/monstre/monstre1.png",
                "/images/monstre/monstre2.png",
                "/images/monstre/monstre12.png");

        // On lance le mouvement des monstres
        game.startMonsterMovement();

        // On met à jour le "Player"
        Player.game = game;
        // On conserve la position en cases
        Player.row = playerStartRow;
        Player.col = playerStartCol;

        // On place l'ImageView du joueur en pixels,
        // mais SANS stocker la position en pixels nulle part ailleurs.
        Player.place(Player.row, Player.col);

        // Afficher la vie, la clé, etc.
        game.showHearts();
        Player.listen(playerPane);
    }

    public void setPlayerStartPosition(int startRow, int startCol) {
        Player.row = startRow;
        Player.col = startCol;
        Player.place(startRow, startCol);
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

    public Label getExperience() {
        return experience;
    }

    public Label getExperienceNeed() {
        return experienceNeed;
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
