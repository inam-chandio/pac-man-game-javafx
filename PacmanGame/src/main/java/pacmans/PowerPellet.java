package pacmans;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerPellet extends Pellet {
    public PowerPellet(double x, double y) {
        super(x, y);
        this.setRadius(7); // Larger radius for distinction
        this.setFill(Color.GOLD); // Gold color for power pellets
    }
}