package pacmans;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameBoard extends Pane {
    private static final int TILE_SIZE = 30; // Size of each grid tile
    private static final int ROWS = 20; // Number of rows
    private static final int COLS = 20; // Number of columns

    private PacMan pacMan;
    private List<Ghost> ghosts;
    private List<Pellet> pellets;
    private int score;
    private int lives;
    private Text scoreText;
    private Text livesText;
    private boolean gameEnded;

    // 2D array to keep track of wall/block locations
    private boolean[][] walls = new boolean[ROWS][COLS];

    public GameBoard() {
        this.setPrefSize(COLS * TILE_SIZE, ROWS * TILE_SIZE);
        initializeGame();
    }

    public PacMan getPacMan() {
        return pacMan;  // Return the PacMan instance
    }

    private void initializeGame() {
        score = 0;
        lives = 3;
        gameEnded = false;

        // Draw the grid with stylish paths and walls
        drawGrid();

        // Add Pac-Man
        pacMan = new PacMan(); // Use the default constructor
        this.getChildren().add(pacMan);

        // Initialize game elements
        ghosts = new ArrayList<>();
        pellets = new ArrayList<>();

        addGhosts();  // Add at least 6 ghosts
        addPellets();  // Add pellets

        // Add score and lives display
        scoreText = new Text(10, 20, "Score: 0");
        scoreText.setFill(Color.YELLOW);
        scoreText.setFont(Font.font("Courier New", 18));
        this.getChildren().add(scoreText);

        livesText = new Text(10, 40, "Lives: 3");
        livesText.setFill(Color.YELLOW);
        livesText.setFont(Font.font("Courier New", 18));
        this.getChildren().add(livesText);
    }
    // In GameBoard.java
    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;  // Store the Text reference
        this.getChildren().add(scoreText);  // Optionally add it to the scene
    }


    private void drawGrid() {
        // Draw grid background and walls
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setX(col * TILE_SIZE);
                tile.setY(row * TILE_SIZE);

                // Create wall around the edges of the grid
                if (row == 0 || row == ROWS - 1 || col == 0 || col == COLS - 1) {
                    tile.setFill(Color.DARKBLUE); // Wall color
                    walls[row][col] = true; // Mark this cell as a wall
                } else if ((row % 2 == 0 || col % 2 == 0) && Math.random() > 0.7) {
                    tile.setFill(Color.DARKBLUE); // Add more walls
                    walls[row][col] = true; // Mark this cell as a wall
                } else {
                    tile.setFill(Color.BLACK); // Empty space
                    walls[row][col] = false;
                }

                this.getChildren().add(tile);
            }
        }
    }

    private void addGhosts() {
        // Add at least 6 ghosts at different locations with unique colors
        String[] ghostColors = {"RED", "BLUE", "PINK", "CYAN", "GREEN", "YELLOW"};
        int startX = TILE_SIZE * 2;
        int startY = TILE_SIZE * 2;
        for (int i = 0; i < 6; i++) {
            Ghost ghost = new Ghost(startX + (i * 3) * TILE_SIZE, startY + (i * 2) * TILE_SIZE, ghostColors[i]);
            ghosts.add(ghost);
            this.getChildren().add(ghost);
        }
    }

    private void addPellets() {
        // Add fewer pellets at fixed positions to reduce clutter
        for (int row = 1; row < ROWS - 1; row++) {
            for (int col = 1; col < COLS - 1; col++) {
                // Random chance to add a pellet
                if (Math.random() > 0.85) {
                    Pellet pellet = new Pellet(col * TILE_SIZE + TILE_SIZE / 2.0, row * TILE_SIZE + TILE_SIZE / 2.0);
                    pellets.add(pellet);
                    this.getChildren().add(pellet);
                }
            }
        }
    }

    private void moveGhosts() {
        // Move ghosts randomly or chase Pac-Man
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly();  // You can make ghosts smarter later to chase Pac-Man
        }
    }

    public void startGame() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveGhosts(); // Move ghosts every frame
                checkCollisions(); // Check for collisions with Pac-Man, ghosts, and pellets
                updateUI(); // Update score and lives display
            }
        }.start();
    }

    private void checkCollisions() {
        // Handle pellet consumption
        pellets.removeIf(pellet -> {
            if (pacMan.getBoundsInParent().intersects(pellet.getBoundsInParent())) {
                score += 10; // Normal pellets
                this.getChildren().remove(pellet);
                return true;  // Pellet is consumed
            }
            return false;  // Pellet is not consumed
        });

        // Handle ghost collision
        for (Ghost ghost : ghosts) {
            if (pacMan.getBoundsInParent().intersects(ghost.getBoundsInParent())) {
                lives--;
                if (lives <= 0) {
                    gameOver();
                } else {
                    resetGame();
                }
            }
        }
    }

    private void updateUI() {
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
    }

    private void resetGame() {
        pacMan.resetPosition();
        for (Ghost ghost : ghosts) {
            ghost.resetPosition();
        }
    }

    private void gameOver() {
        if (!gameEnded) {
            gameEnded = true;
            this.getChildren().clear();

            // Set black background
            this.setStyle("-fx-background-color: black;");

            // Display "Game Over!" message
            Text gameOverText = new Text(COLS * TILE_SIZE / 2.0 - 100, ROWS * TILE_SIZE / 2.0 - 30, "Game Over!");
            gameOverText.setFill(Color.RED);
            gameOverText.setFont(Font.font("Courier New", 48));
            this.getChildren().add(gameOverText);

            // Display final score
            Text finalScoreText = new Text(COLS * TILE_SIZE / 2.0 - 100, ROWS * TILE_SIZE / 2.0 + 30, "Final Score: " + score);
            finalScoreText.setFill(Color.YELLOW);
            finalScoreText.setFont(Font.font("Courier New", 36));
            this.getChildren().add(finalScoreText);

            // Add "Play Again" button
            Button playAgainButton = new Button("Play Again");
            playAgainButton.setLayoutX(COLS * TILE_SIZE / 2.0 - 50);  // Center horizontally
            playAgainButton.setLayoutY(30);  // Position it at the top (you can adjust the value as needed)
            playAgainButton.setStyle("-fx-font-size: 18px; -fx-base: #FF6347;");
            playAgainButton.setOnAction(event -> restartGame());  // Action to restart the game when clicked
            this.getChildren().add(playAgainButton);

            // Optionally, save the score to a file after game over
            saveScoreToFile();

            // Optionally, play a game-over sound or show animations
            // For example:
            // playGameOverSound();  // Uncomment this line if you want to add a sound
        }
    }

    private void saveScoreToFile() {
        // Save the final score to a file (appending to it)
        File file = new File("score.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Final Score: " + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void restartGame() {
        // Clear existing children (reset the board)
        this.getChildren().clear();

        // Reinitialize game state (score, lives, ghosts, etc.)
        initializeGame();

        // Start the game loop again
        startGame();

        // Optionally, you can reset other game settings if necessary
        // For example:
        // pacMan.setSpeed(5);  // Reset Pac-Man speed if modified
        // ghosts.forEach(ghost -> ghost.setSpeed(3));  // Reset ghosts speed if modified
    }

    // Optionally, you can add a sound for game over
    private void playGameOverSound() {
        // You can add a simple sound effect here, e.g., a "game over" sound
        // For example, you could use JavaFX's AudioClip to play a sound
        // AudioClip gameOverSound = new AudioClip("file:///path/to/gameover.wav");
        // gameOverSound.play();
    }
}
