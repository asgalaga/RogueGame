package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label keysLabel; // Lien avec GridPane du FXML

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
    public static Game game;

    @FXML
    public void initialize() {
        next = "/GameGridThree.fxml";
        game = new Game();

        Game.maze = mazeTwo;
        Game.level = this;

        game.fill(GRID_SIZE, gameGrid);

        Player.game = game;
        Player.x = 1;
        Player.y = 10;
        Player.place(1, 10);

        game.showHearts();
        Player.listen(playerPane);
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
