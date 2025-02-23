package CarRacingGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {

    private static double WIDTH = 75;
    private static final double HEIGHT = 50;
    private static double SPEED = 2;

    public Obstacle(double x, double y) {
        super(WIDTH, HEIGHT, Color.RED);
        setX(x);
        setY(y);
    }

    public void moveDown() {
        setY(getY() + SPEED);
    }

}
