package pacmans;


import java.io.*;
import java.util.*;

public class Score {
    private int currentScore;
    private int highScore;

    public Score() {
        this.currentScore = 0;
        this.highScore = loadHighScore();
    }

    public void updateScore(int points) {
        currentScore += points;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void saveScore() {
        if (currentScore > highScore) {
            highScore = currentScore;
            saveHighScore();
        }
    }

    private int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            return 0;
        }
    }

    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }
}
