package pacmans;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ghost extends Circle {
    private double speed;
    private double startX;
    private double startY;

    public Ghost(double startX, double startY, String ghostColor) {
        super(15, Color.RED); // Ghost size and color
        this.startX = startX;
        this.startY = startY;
        this.speed = 2.0;

        setTranslateX(startX);
        setTranslateY(startY);
    }

    // Move randomly
    public void moveRandomly() {
        double dx = (Math.random() > 0.5 ? 1 : -1) * speed;
        double dy = (Math.random() > 0.5 ? 1 : -1) * speed;

        if (getTranslateX() + dx > 0 && getTranslateX() + dx < 600 - getRadius()) {
            setTranslateX(getTranslateX() + dx);
        }
        if (getTranslateY() + dy > 0 && getTranslateY() + dy < 600 - getRadius()) {
            setTranslateY(getTranslateY() + dy);
        }
    }

    // Chase Pac-Man: Move towards Pac-Man's position
    public void chasePacMan(PacMan pacMan) {
        double pacManX = pacMan.getTranslateX();
        double pacManY = pacMan.getTranslateY();

        double dx = pacManX - getTranslateX();
        double dy = pacManY - getTranslateY();

        double distance = Math.hypot(dx, dy);

        if (distance > 0) {
            dx /= distance; // Normalize the direction vector
            dy /= distance;

            // Move the ghost towards Pac-Man with a speed modifier
            setTranslateX(getTranslateX() + dx * speed);
            setTranslateY(getTranslateY() + dy * speed);
        }
    }

    // Reset position to original
    public void resetPosition() {
        setTranslateX(startX);
        setTranslateY(startY);
    }
}
