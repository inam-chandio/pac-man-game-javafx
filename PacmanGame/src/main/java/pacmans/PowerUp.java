package pacmans;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUp extends Circle {
    private String type;

    public PowerUp(double x, double y, String type) {
        super(10, Color.ORANGE);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void activate(PacMan pacMan) {
        if ("Invincibility".equals(type)) {
            pacMan.activateInvincibility();
        }
    }
}