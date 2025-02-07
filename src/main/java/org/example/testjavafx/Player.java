package org.example.testjavafx;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

@SuppressWarnings("exports")
public class Player {
    public static int spriteX, spriteY;
    public static Game game;

    public static int x, y;
    public static int life = 3;
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
            // System.out.println(Game.maze[x][y]);
            switch (Game.maze[x][y]) {
                case 1:
                    break;
                case 2:
                    win();
                    break;
                case 3:
                    unlock(x, y);
                    break;
                case 4:
                    fight(x, y);
                    break;
                case 5:
                    heal(x, y);
                    break;
                default:
                    break;
            }
        }
    }

    public static void unlock(int x, int y) {
        key = true;
        System.out.println("Clef !");
        game.remove(x, y);
        game.showKeys();
    }

    public static void fight(int x, int y) {
        life -= 1;
        System.out.println("Monstre !");
        System.out.println("Ayoye ! HP = " + life);

        if (life < 1) {
            dead = true;
            System.out.println("Mort !");
        }

        game.remove(x, y);
        game.showHearts();
    }

    public static void heal(int x, int y) {
        if (Player.life < 9) {
            Player.life += 1;
            System.out.println("Potion ! HP = " + Player.life);
        } else {
            System.out.println("Pas soif! HP = " + Player.life);
        }
        game.remove(x, y);
        game.showHearts();
    }

    public static void win() {
        if (key) {
            win = true;
            key = false;
            System.out.println("🚪 Porte atteinte ! Passage au niveau suivant...");
            game.showKeys();
        } else {
            System.out.println("Vérouillé!");
        }
    }

    public static void move(int x, int y) {
        int currentCol = Game.level.playerX / Game.tileSize;
        int currentRow = Game.level.playerY / Game.tileSize;
        int newCol = currentCol + x;
        int newRow = currentRow + y;

        if (newRow >= 0 && newRow < Game.size && newCol >= 0 && newCol < Game.size) {
            if (Game.maze[newRow][newCol] != 1) {
                Game.level.playerX = newCol * Game.tileSize;
                Game.level.playerY = newRow * Game.tileSize;
                Game.level.getPlayerImage().setLayoutX(Game.level.playerX);
                Game.level.getPlayerImage().setLayoutY(Game.level.playerY);
            }

            Player.play(newRow, newCol);

            if (Player.win) {
                System.out.println("WINWINWIN");
                game.next(Game.level.getPlayerPane());
                Player.win = false;
            }
        } else {
            System.out.println("⛔ Reste dans le tableau !");
        }
    }

    public static void place(int x, int y) {

        Game.level.playerX = x * Game.tileSize;
        Game.level.playerY = y * Game.tileSize;
        Game.level.getPlayerImage().setLayoutX(Game.level.playerX);
        Game.level.getPlayerImage().setLayoutY(Game.level.playerY);

    }

    public static void listen(Pane playerPane) {
        // Activation du clavier une fois la scène chargée
        playerPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
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
