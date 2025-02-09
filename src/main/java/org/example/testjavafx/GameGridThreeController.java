package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class GameGridThreeController extends Level {
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

    private final int GRID_SIZE = 14; // Nombre de cases (14x14)

    private final int[][] mazeThree = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 8, 8, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 8, 8, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1 },
            { 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1 },
            { 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1 },
            { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 9, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1 }
    };

    public static Game game;

    @FXML
    public void initialize() {
        next = "/Victory.fxml";
        game = new Game();

        Game.maze = mazeThree;
        Game.level = this;

        int playerStartX = 12, playerStartY = 11;

        game.fill(GRID_SIZE, gameGrid, playerStartX, playerStartY,
                "/images/monstre/monstre6.png", "/images/monstre/monstre9.png", "/images/monstre/monstre10.png",
                "/images/monstre/monstre3.png"); // 🔴
        // Monstres
        // spécifiques
        // au
        // niveau 3
        game.startMonsterMovement();

        Player.game = game;
        Player.row = playerStartX;
        Player.col = playerStartY;
        Player.place(playerStartX, playerStartY);

        game.showHearts();
        Player.listen(playerPane);
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
// @FXML
// public void initialize() {
// System.out.println("GameGridTwoController chargé !");
//
// // Génération de la grille avec les images associées
// for (int row = 0; row < GRID_SIZE; row++) {
// for (int col = 0; col < GRID_SIZE; col++) {
// String imagePath;
// switch (mazeThree[row][col]) {
// case 1 -> imagePath = "/images/wall.png"; // Mur
// case 2 -> imagePath = "/images/door.png"; // Porte
// default -> imagePath = "/images/floor.png"; // Sol
// }
//
// URL imgURL = getClass().getResource(imagePath);
// if (imgURL == null) {
// System.err.println("❌ Image introuvable : " + imagePath);
// continue;
// }
//
// ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
// imageView.setFitWidth(TILE_SIZE);
// imageView.setFitHeight(TILE_SIZE);
// gameGrid.add(imageView, col, row);
// }
// }
//
// // Définir la position initiale du joueur
// playerImage.setLayoutX(playerCol * TILE_SIZE);
// playerImage.setLayoutY(playerRow * TILE_SIZE);
//
// System.out.println("✅ Position initiale : Col=" + playerCol + ", Row=" +
// playerRow);
// System.out.println("✅ Position en pixels : X=" + (playerCol * TILE_SIZE) + ",
// Y=" + (playerRow * TILE_SIZE));
//
// // Activation du clavier une fois la scène chargée
// playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
// if (newScene != null) {
// newScene.setOnKeyPressed(event -> {
// switch (event.getCode()) {
// case UP, W -> movePlayer(0, -1);
// case DOWN, S -> movePlayer(0, 1);
// case LEFT, A -> movePlayer(-1, 0);
// case RIGHT, D -> movePlayer(1, 0);
// default -> throw new IllegalArgumentException("Unexpected value: " +
// event.getCode());
// }
// });
// }
// });
// }
//
// private void movePlayer(int deltaX, int deltaY) {
// int newCol = playerCol + deltaX;
// int newRow = playerRow + deltaY;
//
// // Vérifier les limites et empêcher de traverser les murs
// if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
// if (mazeThree[newRow][newCol] != 1) { // Vérifie si ce n'est pas un mur
// playerCol = newCol;
// playerRow = newRow;
// playerImage.setLayoutX(playerCol * TILE_SIZE);
// playerImage.setLayoutY(playerRow * TILE_SIZE);
//
// // Vérifie si le joueur atteint une porte (2)
// if (mazeThree[newRow][newCol] == 2) {
// System.out.println("🚪 Porte atteinte !");
//
// // Vérifie si c'est la porte de retour ou d'avancement
// if (newRow == 13 && newCol == 11) { // Coordonnées de la porte vers
// GameGridOne
// System.out.println("🔄 Retour au niveau précédent...");
// loadPreviousLevel();
// } else {
// System.out.println("➡️ Passage au niveau suivant...");
// }
// }
// } else {
// System.out.println("⛔ Mur détecté ! Déplacement interdit.");
// }
// }
// }
//
// private void loadPreviousLevel() {
// try {
// FXMLLoader loader = new
// FXMLLoader(getClass().getResource("/GameGridTwo.fxml"));
// Pane previousRoot = loader.load();
//
// // Récupérer le contrôleur du niveau 2
// // GameGridTwoController controller = loader.getController();
// //
// //// Mettre le joueur à la bonne position sur GameGridTwo (ex: la porte de
// // retour)
// // controller.setPlayerPosition(1, 10); // ⚠️ Ajuste ces valeurs selon la
// bonne
// // position
//
// Stage stage = (Stage) playerPane.getScene().getWindow();
// Scene previousScene = new Scene(previousRoot);
// stage.setScene(previousScene);
// stage.show();
//
// System.out.println("✅ Retour au niveau 2 réussi avec nouvelle position !");
// } catch (IOException e) {
// e.printStackTrace();
// System.err.println("❌ Erreur lors du chargement de GameGridTwo.fxml !");
// }
// }
//
//
// @FXML
// public void initialize() {
// System.out.println("GameGridTwoController chargé !");
//
// // Génération de la grille avec les images associées
// for (int row = 0; row < GRID_SIZE; row++) {
// for (int col = 0; col < GRID_SIZE; col++) {
// String imagePath;
// switch (mazeThree[row][col]) {
// case 1 -> imagePath = "/images/wall.png"; // Mur
// case 2 -> imagePath = "/images/door.png"; // Porte
// default -> imagePath = "/images/floor.png"; // Sol
// }
//
// URL imgURL = getClass().getResource(imagePath);
// if (imgURL == null) {
// System.err.println("❌ Image introuvable : " + imagePath);
// continue;
// }
//
// ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
// imageView.setFitWidth(TILE_SIZE);
// imageView.setFitHeight(TILE_SIZE);
// gameGrid.add(imageView, col, row);
// }
// }
//
// // Définir la position initiale du joueur
// playerImage.setLayoutX(playerCol * TILE_SIZE);
// playerImage.setLayoutY(playerRow * TILE_SIZE);
//
// System.out.println("✅ Position initiale : Col=" + playerCol + ", Row=" +
// playerRow);
// System.out.println("✅ Position en pixels : X=" + (playerCol * TILE_SIZE) + ",
// Y=" + (playerRow * TILE_SIZE));
//
// // Activation du clavier une fois la scène chargée
// playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
// if (newScene != null) {
// newScene.setOnKeyPressed(event -> {
// switch (event.getCode()) {
// case UP, W -> movePlayer(0, -1);
// case DOWN, S -> movePlayer(0, 1);
// case LEFT, A -> movePlayer(-1, 0);
// case RIGHT, D -> movePlayer(1, 0);
// default -> throw new IllegalArgumentException("Unexpected value: " +
// event.getCode());
// }
// });
// }
// });
// }
//
// private void movePlayer(int deltaX, int deltaY) {
// int newCol = playerCol + deltaX;
// int newRow = playerRow + deltaY;
//
// // Vérifier les limites et empêcher de traverser les murs
// if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
// if (mazeThree[newRow][newCol] != 1) { // Vérifie si ce n'est pas un mur
// playerCol = newCol;
// playerRow = newRow;
// playerImage.setLayoutX(playerCol * TILE_SIZE);
// playerImage.setLayoutY(playerRow * TILE_SIZE);
//
// // Vérifie si le joueur atteint une porte (2)
// if (mazeThree[newRow][newCol] == 2) {
// System.out.println("🚪 Porte atteinte !");
//
// // Vérifie si c'est la porte de retour ou d'avancement
// if (newRow == 13 && newCol == 11) { // Coordonnées de la porte vers
// GameGridOne
// System.out.println("🔄 Retour au niveau précédent...");
// loadPreviousLevel();
// } else {
// System.out.println("➡️ Passage au niveau suivant...");
// }
// }
// } else {
// System.out.println("⛔ Mur détecté ! Déplacement interdit.");
// }
// }
// }
//
// private void loadPreviousLevel() {
// try {
// FXMLLoader loader = new
// FXMLLoader(getClass().getResource("/GameGridTwo.fxml"));
// Pane previousRoot = loader.load();
//
// // Récupérer le contrôleur du niveau 2
// // GameGridTwoController controller = loader.getController();
// //
// //// Mettre le joueur à la bonne position sur GameGridTwo (ex: la porte de
// // retour)
// // controller.setPlayerPosition(1, 10); // ⚠️ Ajuste ces valeurs selon la
// bonne
// // position
//
// Stage stage = (Stage) playerPane.getScene().getWindow();
// Scene previousScene = new Scene(previousRoot);
// stage.setScene(previousScene);
// stage.show();
//
// System.out.println("✅ Retour au niveau 2 réussi avec nouvelle position !");
// } catch (IOException e) {
// e.printStackTrace();
// System.err.println("❌ Erreur lors du chargement de GameGridTwo.fxml !");
// }
// }
//
// }
