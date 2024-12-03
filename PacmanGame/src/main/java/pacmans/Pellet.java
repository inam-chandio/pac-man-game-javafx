package pacmans;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pellet extends Circle {
    public Pellet(double x, double y) {
        super(5, Color.WHITE); // Default size and color for pellets
        setCenterX(x);
        setCenterY(y);
    }
}