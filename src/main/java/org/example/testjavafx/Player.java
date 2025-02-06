package org.example.testjavafx;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player {
    @FXML
    private Pane playerPane; // Pane pour le personnage

    @FXML
    private ImageView playerImage; // ImageView du personnage

    public static int x, y;
    public static boolean key = false;
    public static boolean dead = false;
    public static int life = 6;
}
