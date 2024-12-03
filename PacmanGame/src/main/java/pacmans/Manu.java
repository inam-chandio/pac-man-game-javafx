package pacmans;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Manu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the Start button
        Button startButton = new Button("Start");

        // Style the Start button to make it large and stylish
        startButton.setStyle("-fx-font-size: 20px; -fx-padding: 15px 30px; -fx-background-color: #FFD700; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 8, 0, 2, 2);");

        // Define the button action when clicked
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openGameWindow(primaryStage); // Open the Pacman game window and close the current window
            }
        });

        // Set up the left side layout (VBox) with the background image (bg1.png)
        VBox leftPanel = new VBox();
        leftPanel.setPrefWidth(400);  // Set the width of the left panel (image side)
        leftPanel.setAlignment(javafx.geometry.Pos.CENTER);  // Align the image to the center

        // Set background image for the left panel (bg1.png)
        try {
            // Load the background image for the left panel (bg1.png)
            FileInputStream inputStream = new FileInputStream("src/main/java/images/bg1.png");
            Image bgImage = new Image(inputStream);
            BackgroundImage backgroundImage = new BackgroundImage(bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(400, 600, false, false, true, false));  // Same size as bg.png
            leftPanel.setBackground(new Background(backgroundImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set up the right side layout (VBox) without the background image
        VBox rightPanel = new VBox(20);  // 20 is the gap between elements
        rightPanel.setAlignment(javafx.geometry.Pos.TOP_CENTER);  // Align content at the top center

        // Load the "m.png" image to replace the title
        Image titleImage = null;
        try {
            FileInputStream titleInputStream = new FileInputStream("src/main/java/images/m.png"); // Path to your m.png
            titleImage = new Image(titleInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create an ImageView to display the image
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(300);  // Set the desired width for the image
        titleImageView.setPreserveRatio(true);  // Keep the aspect ratio of the image intact

        // Create text elements for the control instructions
        Text controlsText = new Text("Controls:");
        controlsText.setFill(javafx.scene.paint.Color.YELLOW);
        controlsText.setFont(Font.font("Courier New", 20));

        Text upText = new Text("W - UP");
        upText.setFill(javafx.scene.paint.Color.YELLOW);
        upText.setFont(Font.font("Courier New", 18));

        Text downText = new Text("S - DOWN");
        downText.setFill(javafx.scene.paint.Color.YELLOW);
        downText.setFont(Font.font("Courier New", 18));

        Text leftText = new Text("A - LEFT");
        leftText.setFill(javafx.scene.paint.Color.YELLOW);
        leftText.setFont(Font.font("Courier New", 18));

        Text rightText = new Text("D - RIGHT");
        rightText.setFill(javafx.scene.paint.Color.YELLOW);
        rightText.setFont(Font.font("Courier New", 18));

        // Add the image, controls, and the start button to the right panel
        rightPanel.getChildren().addAll(titleImageView, controlsText, upText, downText, leftText, rightText, startButton);

        // Set up the main layout (HBox) with two panels: left and right
        HBox mainLayout = new HBox();  // Horizontal box to hold the left and right panels
        mainLayout.getChildren().addAll(leftPanel, rightPanel);
        mainLayout.setSpacing(0);  // No space between left and right panels
        mainLayout.setStyle("-fx-background-color: black;");  // Set the background color of the main layout to black

        // Create and set the scene with a large window size
        Scene scene = new Scene(mainLayout, 800, 600); // Adjust the width and height as needed
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Function to open the game window (Pacman game) when the button is clicked
    private void openGameWindow(Stage primaryStage) {
        // Close the current window
        primaryStage.close();

        // Instead of calling Main.main(), we directly start Main
        try {
            MainView game = new MainView();
            game.start(new Stage()); // Launch the Pacman game in a new window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);  // Only call launch here, in Main.java
    }
}
