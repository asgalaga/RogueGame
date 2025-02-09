package org.example.testjavafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class MonsterTimeline {
    private final List<ImageView> monsters;
    private final int[][] maze;
    private final int size;
    private Timeline timeline;
    private final Random random = new Random();

    public MonsterTimeline(List<ImageView> monsters, int[][] maze, int size) {
        this.monsters = new ArrayList<>(monsters);
        this.maze = maze;
        this.size = size;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size &&
                (maze[row][col] == 0 || maze[row][col] == 4);
    }

    private boolean isPlayerAtPosition(int monsterRow, int monsterCol) {
        return (Player.row == monsterRow && Player.col == monsterCol);
    }

    private void removeMonsterFromGrid(ImageView monster, int currentRow, int currentCol) {
        System.out.println("Removing monster at position: " + currentRow + "," + currentCol);

        // 1. Mettre la case à 0 dans la matrice
        maze[currentRow][currentCol] = 0;

        // 2. Retirer visuellement le monstre
        GridPane parent = (GridPane) monster.getParent();
        if (parent != null) {
            parent.getChildren().remove(monster);
        }

        // 3. Retirer de la liste
        monsters.remove(monster);

        // 4. Gérer les points de vie
        Player.fight(currentRow, currentCol);

        // Debug
        System.out.println("Monster removed. Current maze value: " + maze[currentRow][currentCol]);
        Game.printMaze();
    }

    private void moveMonster(ImageView monster) {
        Integer currentRow = GridPane.getRowIndex(monster);
        Integer currentCol = GridPane.getColumnIndex(monster);

        if (currentRow == null || currentCol == null || !isValidPosition(currentRow, currentCol)) {
            // Si le monstre n'est pas à une position valide, le retirer de la liste
            monsters.remove(monster);
            return;
        }

        // Ne bouger que si la position actuelle a bien un monstre
        if (maze[currentRow][currentCol] != 4) {
            monsters.remove(monster);
            return;
        }

        int[][] directions = {
                { -1, 0 }, // Haut
                { 1, 0 }, // Bas
                { 0, -1 }, // Gauche
                { 0, 1 } // Droite
        };

        List<Point2D> validMoves = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];

            if (isValidPosition(newRow, newCol) && maze[newRow][newCol] == 0) {
                validMoves.add(new Point2D(newRow, newCol));
            }
        }

        if (!validMoves.isEmpty()) {
            Point2D newPos = validMoves.get(random.nextInt(validMoves.size()));
            int newRow = (int) newPos.getX();
            int newCol = (int) newPos.getY();

            if (isPlayerAtPosition(newRow, newCol)) {
                removeMonsterFromGrid(monster, currentRow, currentCol);
                return;
            }

            // Si pas de collision, déplacer le monstre
            maze[currentRow][currentCol] = 0;
            maze[newRow][newCol] = 4;
            GridPane.setRowIndex(monster, newRow);
            GridPane.setColumnIndex(monster, newCol);

            if (isPlayerAtPosition(newRow, newCol)) {
                removeMonsterFromGrid(monster, newRow, newCol);
            }
        }
    }

    public void startMonsterMovement() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            // Créer une copie et utiliser un Iterator pour éviter les
            // ConcurrentModificationException
            List<ImageView> currentMonsters = new ArrayList<>(monsters);
            for (ImageView monster : currentMonsters) {
                // Ne déplacer que les monstres qui sont encore dans la liste principale
                if (monsters.contains(monster)) {
                    moveMonster(monster);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopMonsterMovement() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }
}