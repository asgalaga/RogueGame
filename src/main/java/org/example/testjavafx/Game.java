package org.example.testjavafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static int size = 14;
    public static List<Point2D> passages = new ArrayList<>();
    public static int tileSize = 58;
    public static int score = 0;
    private static final List<ImageView> monsters = new ArrayList<>();
    private MonsterTimeline monsterTimeline;
    public static Level level;
    public static int playerLevel = 1;
    public static int xp = 0; // XP actuelle
    public static int xpToNextLevel = 100; // XP nécessaire pour le prochain niveau
    private final Map<Point2D, String> equipementMap = new HashMap<>();

    public Game() {
        stopMonsterMovement();
        passages = new ArrayList<>();
        monsters.clear();
        monsterTimeline = null;
    }

    // Liste statique pour stocker les équipements disponibles (évite les doublons)
    private static List<String> equipementsDisponibles = new ArrayList<>();

    public void fill(int gridSize, GridPane gameGrid, int playerStartRow, int playerStartCol, String... monsterImages) {
        size = gridSize;
        passages.clear();
        monsters.clear();

        System.out.println("🔍 Initialisation du niveau :");

        // Placer le sol et les murs
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String imagePath = "";
                switch (maze[row][col]) {
                    case 0 -> {
                        passages.add(new Point2D(row, col));
                        imagePath = "/images/floor.png";
                    }
                    case 1 -> imagePath = "/images/wall.png";
                    case 2 -> imagePath = "/images/door.png";
                    case 9 -> imagePath = "/images/floor.png";
                    default -> imagePath = "/images/floor.png";
                }
                placeImage(imagePath, row, col, gameGrid);
            }
        }

        final int pRow = playerStartRow;
        final int pCol = playerStartCol;
        passages.removeIf(p -> p.getX() == pRow && p.getY() == pCol);

        if (passages.isEmpty()) {
            System.err.println("❌ Aucune case disponible pour les objets !");
            return;
        }

        Collections.shuffle(passages);

        // Placer la clé, les monstres et les potions
        if (!passages.isEmpty()) {
            putObject(3, passages.remove(0), "/images/key.png", gameGrid);
        }

        // 🔥 Vérifier et préparer la liste des équipements disponibles
        if (equipementsDisponibles.isEmpty()) {
            equipementsDisponibles.addAll(Arrays.asList(
                    "/images/equipement/botte2.png",
                    "/images/equipement/bouclier2.png",
                    "/images/equipement/casque2.png",
                    "/images/equipement/chest2.png",
                    "/images/equipement/epee1.png",
                    "/images/equipement/gant2.png",
                    "/images/equipement/masse2.png"));
        }

        // Mélanger les équipements pour un choix aléatoire
        Collections.shuffle(equipementsDisponibles);

        // Placement des monstres
        for (int i = 0; i < 8 && !passages.isEmpty(); i++) {
            Point2D pos = passages.remove(0);
            String monsterImage = monsterImages[i % monsterImages.length];
            putObject(4, pos, monsterImage, gameGrid);
        }

        // 🔥 Placer un équipement unique
        if (!equipementsDisponibles.isEmpty() && !passages.isEmpty()) {
            String equipementImage = equipementsDisponibles.remove(0); // Sélection unique
            Point2D pos = passages.remove(0);
            System.out.println("🛡️ Placement équipement unique : " + equipementImage + " à la position " + pos);
            putObject(8, pos, equipementImage, gameGrid);
        }

        // Placement des potions
        for (int i = 0; i < 2 && !passages.isEmpty(); i++) {
            putObject(5, passages.remove(0), "/images/potion.png", gameGrid);
        }

        // Ajouter le cœur d'amélioration
        if (!passages.isEmpty()) {
            putObject(7, passages.remove(0), "/images/life.png", gameGrid);
        }
    }

    private void placeImage(String imagePath, int row, int col, GridPane gameGrid) {
        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
            imageView.setFitWidth(tileSize);
            imageView.setFitHeight(tileSize);
            gameGrid.add(imageView, col, row);
        }
    }

    private void putObject(int kind, Point2D pos, String imagePath, GridPane gameGrid) {
        int row = (int) pos.getX();
        int col = (int) pos.getY();

        if (maze[row][col] != 0) {
            System.out.println("⚠️ Case non vide en (" + row + "," + col + ") : " + maze[row][col]);
            return;
        }

        URL imgURL = getClass().getResource(imagePath);
        if (imgURL == null) {
            System.err.println("❌ Image non trouvée : " + imagePath);
            return;
        }

        ImageView imageView = new ImageView(new Image(imgURL.toExternalForm()));
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        gameGrid.add(imageView, col, row);
        maze[row][col] = kind;

        // 🔥 Stocker l’équipement s'il s'agit d'un équipement (kind == 8)
        if (kind == 8) {
            equipementMap.put(pos, imagePath);
            System.out.println("🛡️ Équipement stocké : " + imagePath + " à " + pos);
        }

        if (kind == 4) {
            monsters.add(imageView);
            System.out.println("✅ Monstre ajouté en (" + row + "," + col + ")");
        }
    }

    public void remove(int row, int col) {
        System.out.println("Removing object at (" + row + "," + col + "), current value: " + maze[row][col]);

        // D'abord mettre à jour la matrice
        maze[row][col] = 0;

        GridPane grid = level.getGameGrid();
        List<javafx.scene.Node> nodesToRemove = new ArrayList<>();

        // Trouver tous les éléments à cette position
        for (javafx.scene.Node node : grid.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            columnIndex = (columnIndex == null) ? 0 : columnIndex;
            rowIndex = (rowIndex == null) ? 0 : rowIndex;

            if (columnIndex == col && rowIndex == row) {
                nodesToRemove.add(node);
            }
        }

        // S'il y a plus d'un élément (sol + objet)
        if (nodesToRemove.size() > 1) {
            // Retirer l'objet (dernier élément ajouté)
            javafx.scene.Node nodeToRemove = nodesToRemove.get(nodesToRemove.size() - 1);
            grid.getChildren().remove(nodeToRemove);

            // Si c'est un monstre, le retirer de la liste des monstres
            if (nodeToRemove instanceof ImageView && monsters.contains(nodeToRemove)) {
                monsters.remove(nodeToRemove);
            }

            passages.add(new Point2D(row, col));
            grid.requestLayout();
        }

        System.out.println("After removal, maze value at (" + row + "," + col + "): " + maze[row][col]);
        printMaze();
    }

    public void startMonsterMovement() {
        stopMonsterMovement();
        monsterTimeline = new MonsterTimeline(monsters, maze, size);
        monsterTimeline.startMonsterMovement();
    }

    public void stopMonsterMovement() {
        if (monsterTimeline != null) {
            monsterTimeline.stopMonsterMovement();
            monsterTimeline = null;
        }
    }

    public static void resetGame() {
        maze = null;
        size = 14;
        passages.clear();
        monsters.clear();
        score = 0;
        level = null;
        Player.life = 5;
        Player.maxLife = 5;
        Player.playerLevel = 1;
        Player.xp = 0;
        Player.xpToNextLevel = 100;

        // 🔥 Réinitialiser l’équipement du joueur au niveau de base
        Player.helmImage = "/images/equipement/casque1.png";
        Player.chestImage = "/images/equipement/chest1.png";
        Player.weaponImage = "/images/equipement/masse1.png";
        Player.botteImage = "/images/equipement/botte1.png";
        Player.gloveImage = "/images/equipement/gant1.png";
        Player.shieldImage = "/images/equipement/bouclier1.png";

        // 🔥 Réinitialiser la liste des équipements disponibles
        equipementsDisponibles.clear();
        equipementsDisponibles.addAll(Arrays.asList(
                "/images/equipement/botte2.png",
                "/images/equipement/bouclier2.png",
                "/images/equipement/casque2.png",
                "/images/equipement/chest2.png",
                "/images/equipement/epee1.png",
                "/images/equipement/gant2.png",
                "/images/equipement/masse2.png"));

        System.out.println("✅ Game réinitialisé avec équipement de base !");
    }

    public void showHearts() {
        // Affiche jusqu'à maxLife cœurs
        for (int i = 1; i <= 8; i++) {
            ImageView heart = level.getHeart(i);
            if (i <= Player.maxLife) {
                heart.setVisible(true);
                if (i <= Player.life) {
                    heart.setImage(new Image(getClass().getResourceAsStream("/images/life.png")));
                } else {
                    heart.setImage(new Image(getClass().getResourceAsStream("/images/noLife.png")));
                }
            } else {
                heart.setVisible(false); // Cache les cœurs au-delà de maxLife
            }
        }
    }

    public void equiperObjet(String equipementImage, int row, int col) {
        if (equipementImage == null || equipementImage.isEmpty()) {
            System.err.println("❌ Aucun équipement à équiper !");
            return;
        }

        System.out.println("🎭 Équipement récupéré : " + equipementImage);

        if (level == null) {
            System.err.println("❌ ERREUR : `level` n'est pas initialisé !");
            return;
        }

        // Charger l'image
        Image newImage = new Image(getClass().getResourceAsStream(equipementImage));

        // Appliquer l'équipement et sauvegarder l'image équipée
        if (equipementImage.contains("casque")) {
            level.getHelm().setImage(newImage);
            Player.helmImage = equipementImage; // 🔥 Sauvegarde
        } else if (equipementImage.contains("chest")) {
            level.getChest().setImage(newImage);
            Player.chestImage = equipementImage;
        } else if (equipementImage.contains("epee") || equipementImage.contains("masse")) {
            level.getWeapon().setImage(newImage);
            Player.weaponImage = equipementImage;
        } else if (equipementImage.contains("botte")) {
            level.getBotte().setImage(newImage);
            Player.botteImage = equipementImage;
        } else if (equipementImage.contains("gant")) {
            level.getGlove().setImage(newImage);
            Player.gloveImage = equipementImage;
        } else if (equipementImage.contains("bouclier")) {
            level.getShield().setImage(newImage);
            Player.shieldImage = equipementImage;
        } else {
            System.out.println("⚠️ Équipement inconnu : " + equipementImage);
        }

        // Supprimer l’équipement après l'avoir équipé
        remove(row, col);
    }

    public String getEquipmentImage(int row, int col) {
        Point2D pos = new Point2D(row, col);
        return equipementMap.getOrDefault(pos, null);
    }

    public void showExperience() {
        if (level != null) {
            level.getExperience().setText(String.valueOf(Player.xp));
            level.getExperienceNeed().setText("/" + Player.xpToNextLevel);
            level.getLevelLabel().setText(String.valueOf(Player.playerLevel)); // Ajout de cette ligne
        }
    }

    public void showKeys() {
        level.getKeys().setText(Player.key ? "1" : "0");
    }

    public void next(Pane playerPane) {
        stopMonsterMovement();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(level.next));
            Pane nextRoot = loader.load();
            Stage stage = (Stage) playerPane.getScene().getWindow();
            Scene nextScene = new Scene(nextRoot);
            stage.setScene(nextScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du chargement du niveau suivant !");
        }
    }

    public static void printMaze() {
        System.out.println("État actuel de la matrice maze :");
        for (int r = 0; r < size; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < size; c++) {
                line.append(maze[r][c]).append(" ");
            }
            System.out.println(line.toString());
        }
        System.out.println("------------------------------");
    }
}