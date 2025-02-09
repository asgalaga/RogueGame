package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class Level {

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
    @FXML
    private Label levelLabel;
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

    public int playerX = 58; // Position initiale en pixels
    public int playerY = 58;
    public final int TILE_SIZE = 58; // Taille d’une case
    public final int GRID_SIZE = 14; // Nombre de cases (14x14)

    public String next = "";

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

    public void setExperience(Label experience) {
        this.experience = experience;
    }

    public Label getExperienceNeed() {
        return experienceNeed;
    }

    public void setExperienceNeed(Label experienceNeed) {
        this.experienceNeed = experienceNeed;
    }

    public Label getLevelLabel() {
        return levelLabel;
    }

    public void setLevelLabel(Label levelLabel) {
        this.levelLabel = levelLabel;
    }

    public ImageView getHelm() {
        return helm;
    }

    public void setHelm(ImageView helm) {
        this.helm = helm;
    }

    public ImageView getChest() {
        return chest;
    }

    public void setChest(ImageView chest) {
        this.chest = chest;
    }

    public ImageView getWeapon() {
        return weapon;
    }

    public void setWeapon(ImageView weapon) {
        this.weapon = weapon;
    }

    public ImageView getBotte() {
        return botte;
    }

    public void setBotte(ImageView botte) {
        this.botte = botte;
    }

    public ImageView getGlove() {
        return glove;
    }

    public void setGlove(ImageView glove) {
        this.glove = glove;
    }

    public ImageView getShield() {
        return shield;
    }

    public void setShield(ImageView shield) {
        this.shield = shield;
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
