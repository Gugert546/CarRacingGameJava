package CarRacingGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Obstacle extends Rectangle {
    private static Random random = new Random();
    private static double WIDTH = random.nextDouble() * (150 - 25);
    private static final double HEIGHT = 50;
    private static double SPEED = random.nextDouble() * (10 - 2);

    public Obstacle(double x, double y) {
        super(WIDTH, HEIGHT, Color.RED);
        setX(x);
        setY(y);
    }

    public void moveDown() {
        setY(getY() + SPEED);
    }

}
