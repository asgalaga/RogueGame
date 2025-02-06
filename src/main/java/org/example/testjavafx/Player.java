package org.example.testjavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class Player {
    public static int spriteX, spriteY;
    public static Game game;

    public static int x, y;
    public static int life = 6;
    public static boolean key = false;
    public static boolean dead = false;
    public static boolean win = false;

    public static void play(int x, int y) {
        if (x >= 0 && x < Game.size && y >= 0 && y < Game.size) {
            // if (Game.maze[x][y] != 1) {
            // spriteX = x * Game.tileSize;
            // spriteY = y * Game.tileSize;
            // playerImage.setLayoutX(spriteX);
            // playerImage.setLayoutY(spriteY);
            // }
            switch (Game.maze[x][y]) {
                case 1:
                    break;
                case 2:
                    win();
                    break;
                case 3:
                    unlock();
                    break;
                case 4:
                    fight();
                    break;
                case 5:
                    heal();
                    break;
                default:
                    break;
            }
        }
    }

    public static void unlock() {
        key = true;
        System.out.println("Clef !");
    }

    public static void fight() {
        life -= 1;
        System.out.println("Monstre !");
        System.out.println("Ayoye ! HP = " + life);
        if (life < 1) {
            dead = true;
            System.out.println("Mort !");
        }
    }

    public static void heal() {
        if (Player.life < 9) {
            Player.life += 1;
            System.out.println("Potion ! HP = " + Player.life);
        } else {
            System.out.println("Pas soif! HP = " + Player.life);
        }
    }

    public static void win() {
        if (key) {
            win = true;
            key = false;
            System.out.println("🚪 Porte atteinte ! Passage au niveau suivant...");
        } else {
            System.out.println("Vérouillé!");
        }
    }

    public static void move(int x, int y) {
        int currentCol = Game.level1.playerX / Game.tileSize;
        int currentRow = Game.level1.playerY / Game.tileSize;
        int newCol = currentCol + x;
        int newRow = currentRow + y;

        // Vérifier les limites et éviter les murs
        if (newRow >= 0 && newRow < Game.size && newCol >= 0 && newCol < Game.size) {
            if (Game.maze[newRow][newCol] != 1) { // Vérifie si ce n'est pas un mur
                Game.level1.playerX = newCol * Game.tileSize;
                Game.level1.playerY = newRow * Game.tileSize;
                Game.level1.getPlayerImage().setLayoutX(Game.level1.playerX);
                Game.level1.getPlayerImage().setLayoutY(Game.level1.playerY);
            }

            Player.play(newRow, newCol);

            if (Player.win) {
                game.next(Game.level1.getPlayerPane());
            }
        } else {
            System.out.println("⛔ Mur détecté !");
        }
    }

    public static void listen(Pane playerPane) {
        // Activation du clavier une fois la scène chargée
        playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    // GameGridOneController controller = new GameGridOneController();
                    switch (event.getCode()) {
                        case UP, W -> move(0, -1);
                        case DOWN, S -> move(0, 1);
                        case LEFT, A -> move(-1, 0);
                        case RIGHT, D -> move(1, 0);
                        case ESCAPE -> Platform.exit();
                        default -> System.out.println("Unexpected value: " + event.getCode());
                    }
                });
            }
        });
    }
}
