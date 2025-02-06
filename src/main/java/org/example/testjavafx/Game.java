package org.example.testjavafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

@SuppressWarnings("exports")
public class Game {
    public static int[][] maze;
    public static List<Point2D> passages = new ArrayList<>();
    public static int tileSize = 58; // Taille d’une case

    public void fill(GridPane gameGrid) {
        Collections.shuffle(passages);

        // Monstres
        for (int i = 0; i < 4; i++) {
            Point2D tile = passages.get(i);
            passages.remove(i);
            String imagePath = "/images/monstre1.png";
            URL imgURL = getClass().getResource(imagePath);
            ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
            imageView.setFitWidth(tileSize);
            imageView.setFitHeight(tileSize);
            gameGrid.add(imageView, (int) tile.getX(), (int) tile.getY());
        }

        for (int i = 0; i < 2; i++) {
            Point2D tile = passages.get(i);
            passages.remove(i);
            String imagePath = "/images/life.png";
            URL imgURL = getClass().getResource(imagePath);
            ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
            imageView.setFitWidth(tileSize);
            imageView.setFitHeight(tileSize);
            gameGrid.add(imageView, (int) tile.getX(), (int) tile.getY());

        }
    }
}
