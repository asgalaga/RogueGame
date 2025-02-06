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
    public static GridPane grid;
    public static int[][] maze;
    public static List<Point2D> passages = new ArrayList<>();
    public static int tileSize = 58; // Taille d’une case
    public static Player player;

    public void fill(GridPane gameGrid) {
        grid = gameGrid;

        Collections.shuffle(passages);

        // clef
        put(3, "/images/key.png");

        // monstres
        for (int i = 0; i < 4; i++) {
            put(4, "/images/monstre1.png");
        }

        // potions
        for (int i = 0; i < 2; i++) {
            put(5, "/images/life.png");
        }

        // System.out.println(maze);
    }

    public void put(int kind, String image) {
        Point2D tile = passages.get(0);
        passages.remove(0);

        String imagePath = image;
        URL imgURL = getClass().getResource(imagePath);

        ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);

        int x = (int) tile.getX();
        int y = (int) tile.getY();

        grid.add(imageView, y, x);

        maze[(int) tile.getX()][(int) tile.getY()] = kind;
    }
}
