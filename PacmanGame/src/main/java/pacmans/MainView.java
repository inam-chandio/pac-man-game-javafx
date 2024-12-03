package pacmans;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainView extends Application {
    private GameBoard gameBoard;

    public static void main(String[] args) {
        launch(args);  // Launch the MainView
    }

    @Override
    public void start(Stage primaryStage) {
        gameBoard = new GameBoard();

        // Create a Text for displaying score
        Text scoreText = new Text("Score: 0");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(20));
        gameBoard.setScoreText(scoreText);

        Scene scene = new Scene(gameBoard, 600, 600, Color.BLACK);
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Advanced Pac-Man Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameBoard.startGame();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                gameBoard.getPacMan().move("W");
                break;
            case S:
                gameBoard.getPacMan().move("S");
                break;
            case A:
                gameBoard.getPacMan().move("A");
                break;
            case D:
                gameBoard.getPacMan().move("D");
                break;
            default:
                break;
        }
    }
}
