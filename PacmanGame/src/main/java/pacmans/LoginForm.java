package pacmans;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginForm extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static void showLoginWindow() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Button startButton = new Button("Start");

        // Change the label's text color to white
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Add image to the top center
        ImageView imageView = null;
        try {
            // Using FileInputStream to load the image
            FileInputStream inputStream = new FileInputStream("src/main/java/images/m.png"); // Correct path to your image
            Image image = new Image(inputStream);
            imageView = new ImageView(image);
            imageView.setFitWidth(250);  // Resize image width (you can increase the size if needed)
            imageView.setPreserveRatio(true); // Maintain aspect ratio
            imageView.setStyle("-fx-padding: 10px;");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  // Handle the case if image is not found
        }

        // Style start button
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");

        // Set action for the start button
        startButton.setOnAction(event -> {
            // Close the login window
            primaryStage.close();

            // Launch MainView (the game window)
            new Manu().start(new Stage()); // Open the game window
        });

        // Layout for Login form
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Add imageView only if it's not null
        if (imageView != null) {
            layout.getChildren().add(imageView);
        }

        layout.getChildren().addAll(usernameLabel, usernameField, startButton);

        // Set background color for the login layout (black background)
        layout.setStyle("-fx-background-color: black;");

        // Set up the Scene with black background and larger size
        Scene scene = new Scene(layout, 400, 500);  // Increased size to match the SignUpForm
        scene.setFill(javafx.scene.paint.Color.BLACK);  // Set black background for the scene

        primaryStage.setTitle("Start");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
