package pacmans;


import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PacMan extends Circle {
    private boolean invincible;
    private int speed;
    private String currentDirection;
    private boolean moving;

    public PacMan() {
        super(15, Color.YELLOW); // Pac-Man's size and default color
        setTranslateX(300);
        setTranslateY(300);
        speed = 4; // Base speed
        invincible = false;
        currentDirection = "";
        moving = false;

        startMovement();
    }

    public void move(String direction) {
        currentDirection = direction;
        moving = true;
    }

    public void stop() {
        moving = false;
    }

    private void startMovement() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!moving) return;

                switch (currentDirection) {
                    case "W":
                        if (getTranslateY() - speed > 0) {
                            setTranslateY(getTranslateY() - speed);
                        }
                        break;
                    case "S":
                        if (getTranslateY() + speed < 600 - getRadius()) {
                            setTranslateY(getTranslateY() + speed);
                        }
                        break;
                    case "A":
                        if (getTranslateX() - speed > 0) {
                            setTranslateX(getTranslateX() - speed);
                        }
                        break;
                    case "D":
                        if (getTranslateX() + speed < 600 - getRadius()) {
                            setTranslateX(getTranslateX() + speed);
                        }
                        break;
                }
            }
        }.start();
    }

    public void activateInvincibility() {
        invincible = true;
        speed = 6; // Increase speed during invincibility
        setFill(Color.GOLD); // Change color to indicate invincibility

        new Thread(() -> {
            try {
                Thread.sleep(5000); // Invincibility lasts for 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invincible = false;
            speed = 4; // Reset to normal speed
            setFill(Color.YELLOW); // Reset to original color
        }).start();
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void resetPosition() {
        setTranslateX(300);
        setTranslateY(300);
        currentDirection = "";
        stop();
    }
}
