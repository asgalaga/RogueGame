package org.example.testjavafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("exports")
public class Game {
    public static int[][] maze;
    public static int size = 14; // Nombre de cases (14x14)
    public static List<Point2D> passages = new ArrayList<>();
    public static int tileSize = 58; // Taille d’une case
    public static int score = 0; // Taille d’une case
    public static Level level;

    public Game() {

    }

    public void start() {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        // Parent root = loader.load();
        //
        // Scene scene = new Scene(root, 890, 950);
        // primaryStage.setScene(scene);
        // primaryStage.show();
    }

    public void fill(int gridSize, GridPane gameGrid) {
        size = gridSize;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String imagePath = "";

                switch (maze[row][col]) {
                    case 0 -> Game.passages.add(new Point2D(row, col));
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
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                gameGrid.add(imageView, col, row);
            }
        }

        Collections.shuffle(passages);

        // clef
        put(3, "/images/key.png");

        // monstres
        for (int i = 0; i < 8; i++) {
            put(4, "/images/monstre1.png");
        }

        // potions
        for (int i = 0; i < 2; i++) {
            put(5, "/images/life.png");
        }
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

        level.getGameGrid().add(imageView, y, x);

        maze[(int) tile.getX()][(int) tile.getY()] = kind;
    }

    public void remove(int x, int y) {
        GridPane grid = level.getGameGrid();
        List<javafx.scene.Node> nodesToRemove = new ArrayList<>();

        for (javafx.scene.Node node : grid.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            columnIndex = (columnIndex == null) ? 0 : columnIndex;
            rowIndex = (rowIndex == null) ? 0 : rowIndex;

            if (columnIndex == y && rowIndex == x) {
                nodesToRemove.add(node);
            }
        }

        if (nodesToRemove.size() > 1) {
            grid.getChildren().remove(nodesToRemove.get(nodesToRemove.size() - 1));
            maze[x][y] = 0; // Reset maze position to empty
            passages.add(new Point2D(x, y));
            level.getGameGrid().requestLayout();
        }
    }

    public void next(Pane playerPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(level.next));
            Pane nextRoot = loader.load();

            Stage stage = (Stage) playerPane.getScene().getWindow();
            Scene nextScene = new Scene(nextRoot);
            stage.setScene(nextScene);
            stage.show();

            // System.out.println("✅ Chargement du niveau 2 réussi !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de GameGridTwo.fxml !");
        }
    }

    public void showHearts() {
        for (int i = 1; i < 8; i++) {
            if (i <= Player.life) {
                level.getHeart(i)
                        .setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/life.png")));

            } else {
                level.getHeart(i)
                        .setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/noLife.png")));

            }
        }
    }

    public void showKeys() {
        if (Player.key)
            level.getKeys().setText("1");
        else
            level.getKeys().setText("0");
        // .setImage(new
        // javafx.scene.image.Image(getClass().getResourceAsStream("/images/life.png")));
    }
}
