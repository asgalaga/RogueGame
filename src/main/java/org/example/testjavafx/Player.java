package org.example.testjavafx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

@SuppressWarnings("exports")
public class Player {

    public static Game game;

    // Position du joueur en cases (ligne, colonne)
    public static int row, col;

    public static int life = 5;
    public static int maxLife = 5;
    public static boolean key = false;
    public static boolean dead = false;
    public static boolean win = false;
    public static int playerLevel = 1; // On utilise playerLevel au lieu de level pour éviter la confusion
    public static int xp = 0;
    public static int xpToNextLevel = 100;

    /**
     * Vérifie l'objet contenu dans la case (row, col) et agit en conséquence
     */
    public static void play(int row, int col) {
        if (row >= 0 && row < Game.size && col >= 0 && col < Game.size) {
            switch (Game.maze[row][col]) {
                case 1:
                    break;
                case 2:
                    checkVictory();
                    break;
                case 3:
                    unlock(row, col);
                    break;
                case 4:
                    fight(row, col);
                    break;
                case 5:
                    heal(row, col);
                    break;
                case 6:
                    back();
                    break;
                case 7:
                    System.out.println("💗 Tentative d'amélioration de la vie...");
                    upgradeLife(row, col);
                    break;
                default:
                    System.out.println("⚠️ Valeur non gérée : ");
                    break;
            }
        }
    }

    /**
     * Récupère la clé
     */
    public static void unlock(int row, int col) {
        key = true;
        System.out.println("Clef !");
        game.remove(row, col);
        game.showKeys();
    }

    public static void upgradeLife(int row, int col) {
        if (maxLife < 8) { // On vérifie qu'on n'a pas atteint le maximum absolu
            maxLife += 1; // On augmente la vie maximale de 1 à chaque fois
            life = maxLife; // On restaure toute la vie
            System.out.println("💗 Vie maximale augmentée ! HP = " + life + "/" + maxLife);
            game.remove(row, col);
            game.showHearts();
        }
    }

    /**
     * Combat un monstre : on perd 1 PV, on supprime la case monstre.
     * Si PV < 1, Game Over.
     */
    public static void fight(int row, int col) {
        // Réduire les points de vie
        life -= 1;
        System.out.println("Monstre !");
        System.out.println("Ayoye ! HP = " + life);

        gainXP(50);

        // Force la mise à jour de la matrice
        Game.maze[row][col] = 0;

        if (life < 1) {
            dead = true;
            System.out.println("Mort !");

            // Charger GameOver.fxml
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(Player.class.getResource("/GameOver.fxml"));
                    Pane gameOverRoot = loader.load();

                    // Récupérer la fenêtre actuelle
                    Stage stage = (Stage) Stage.getWindows().stream()
                            .filter(Window::isShowing)
                            .findFirst()
                            .orElse(null);

                    if (stage == null) {
                        System.err.println("❌ ERREUR : Impossible de récupérer la fenêtre active !");
                        return;
                    }

                    // Mettre à jour la scène avec GameOver.fxml
                    Scene gameOverScene = new Scene(gameOverRoot);
                    stage.setScene(gameOverScene);
                    stage.show();

                    System.out.println("✅ GameOver.fxml affiché avec succès !");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("❌ Erreur lors du chargement de GameOver.fxml !");
                }
            });
        }

        // Retire le monstre de la grille
        game.remove(row, col);
        game.showHearts();
    }

    /**
     * Récupère une potion (PV +1 si < maxLife)
     */
    public static void heal(int row, int col) {
        if (Player.life < Player.maxLife) {
            Player.life += 1;
            System.out.println("Potion ! HP = " + Player.life + "/" + Player.maxLife);
        } else {
            System.out.println("Pas soif! HP = " + Player.life + "/" + Player.maxLife);
        }
        game.remove(row, col);
        game.showHearts();
    }

    public static void gainXP(int amount) {
        xp += amount;
        System.out.println("📈 +" + amount + " XP | " + xp + "/" + xpToNextLevel);

        // Vérifier si on monte de niveau
        while (xp >= xpToNextLevel) {
            levelUp();
        }
        game.showExperience(); // Mettre à jour l'affichage
    }

    private static void levelUp() {
        playerLevel++;
        xp -= xpToNextLevel;
        xpToNextLevel = playerLevel * 100;
        System.out.println("🎉 Niveau suivant ! Niveau " + playerLevel + " atteint !");
        // Important : mettre à jour l'affichage après avoir modifié le niveau
        game.showExperience();
    }

    /**
     * Vérifie si on peut ouvrir la porte de fin. Nécessite la clé.
     */
    private static void checkVictory() {
        if (!key) {
            System.out.println("⛔ La porte est verrouillée ! Il faut trouver la clé !");
            return;
        }

        // Le joueur a la clé, on peut procéder
        key = false;
        game.showKeys();

        // Si c'est la fin (dernier niveau)
        if (Game.level instanceof GameGridThreeController) {
            System.out.println("🎉 Fin du jeu ! Affichage de Victory.fxml !");
            showVictoryScreen();
        } else {
            System.out.println("🚪 Passage au niveau suivant !");
            game.next(Game.level.getPlayerPane());
        }
    }

    /**
     * Permet d'utiliser la porte finale, si on a la clé.
     */
    public static void win() {
        if (key) {
            key = false;
            System.out.println("🚪 Porte atteinte !");
            game.showKeys();
            checkVictory();
        } else {
            System.out.println("⛔ La porte est verrouillée !");
        }
    }

    /**
     * Affiche l'écran de victoire
     */
    private static void showVictoryScreen() {
        System.out.println("🟢 Chargement de Victory.fxml...");
        try {
            FXMLLoader loader = new FXMLLoader(Player.class.getResource("/Victory.fxml"));
            Pane victoryRoot = loader.load();

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) Stage.getWindows().stream()
                    .filter(Window::isShowing)
                    .findFirst()
                    .orElse(null);

            if (stage == null) {
                System.err.println("❌ ERREUR : Impossible de récupérer la fenêtre active !");
                return;
            }

            Scene victoryScene = new Scene(victoryRoot);
            stage.setScene(victoryScene);
            stage.show();

            System.out.println("✅ Victory.fxml affiché avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement de Victory.fxml !");
        }
    }

    /**
     * Déplace le joueur de dCol/dRow cases (ex: move(0,1) = descendre)
     */
    public static void move(int dCol, int dRow) {
        // Calcule la nouvelle position en cases
        int newRow = row + dRow;
        int newCol = col + dCol;

        // Vérifier limites du labyrinthe
        if (newRow < 0 || newRow >= Game.size || newCol < 0 || newCol >= Game.size) {
            System.out.println("⛔ Mouvement hors limites");
            return;
        }

        // Vérifier si c'est un mur
        if (Game.maze[newRow][newCol] == 1) {
            System.out.println("⛔ Impossible de traverser un mur");
            return;
        }

        // Déplacement réussi : on met à jour row / col
        row = newRow;
        col = newCol;
        Game.printMaze();
        // Affiche le joueur en pixels
        Game.level.getPlayerImage().setLayoutX(col * Game.tileSize);
        Game.level.getPlayerImage().setLayoutY(row * Game.tileSize);

        // Vérifier les interactions
        play(row, col);

        // Si on doit passer au niveau suivant
        if (Player.win) {
            System.out.println("🎉 Niveau terminé !");
            game.next(Game.level.getPlayerPane());
            Player.win = false;
        }
    }

    /**
     * Place le joueur directement à la case (row, col),
     * puis met à jour l'affichage en pixels.
     */
    public static void place(int row, int col) {
        // Mémoriser la position en cases
        Player.row = row;
        Player.col = col;

        // Mise à jour en pixels
        Game.level.getPlayerImage().setLayoutX(col * Game.tileSize);
        Game.level.getPlayerImage().setLayoutY(row * Game.tileSize);
    }

    /**
     * Active l'écoute du clavier pour bouger le joueur
     */
    public static void listen(Pane playerPane) {
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

    /**
     * Gestion de la porte de retour vers le niveau précédent
     */
    public static void back() {
        System.out.println("🔄 Porte de retour atteinte ! Vérification...");

        if (Game.level instanceof GameGridTwoController) {
            System.out.println("⬅️ Retour à GameGridOne.fxml !");
            loadPreviousLevel("/GameGridOne.fxml", 11, 12); // Spawn en (12,11) en revenant au niveau 1
        } else if (Game.level instanceof GameGridThreeController) {
            System.out.println("⬅️ Retour à GameGridTwo.fxml !");
            loadPreviousLevel("/GameGridTwo.fxml", 1, 10); // Spawn en (10,1) en revenant au niveau 2
        }
    }

    private static void loadPreviousLevel(String previousLevel, int spawnRow, int spawnCol) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(Player.class.getResource(previousLevel));
                Pane previousRoot = loader.load();

                // Récupérer la fenêtre actuelle
                Stage stage = (Stage) Stage.getWindows().stream()
                        .filter(Window::isShowing)
                        .findFirst()
                        .orElse(null);

                if (stage == null) {
                    System.err.println("❌ ERREUR : Impossible de récupérer la fenêtre active !");
                    return;
                }

                // Charger le niveau précédent
                Scene previousScene = new Scene(previousRoot);
                stage.setScene(previousScene);
                stage.show();

                // Définir le bon contrôleur en fonction du niveau précédent
                if (previousLevel.equals("/GameGridTwo.fxml")) {
                    Game.level = loader.getController();
                    ((GameGridTwoController) Game.level).setPlayerStartPosition(spawnRow, spawnCol);
                } else if (previousLevel.equals("/GameGridOne.fxml")) {
                    Game.level = loader.getController();
                    ((GameGridOneController) Game.level).setPlayerStartPosition(spawnRow, spawnCol);
                }

                System.out.println("✅ Retour réussi à " + previousLevel + " | Nouvelle position : (" + spawnRow + ", "
                        + spawnCol + ")");

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("❌ Erreur lors du chargement de " + previousLevel + " !");
            }
        });
    }

}
