/**
* Obstacle class
* Version 1.0
* Audun Halstensen
*/

package CarRacingGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {

    private  static double WIDTH = 75;
    private  static final double HEIGHT = 50;
    private  static int SPEED;
    /** konstruktør for en hindring */
    public Obstacle(double x, double y) {
        super(WIDTH, HEIGHT, Color.RED);
        setX(x);
        setY(y);
        
    }
    /**metode for å flytte hindringene nedover på skjermen */
    public void moveDown() {
        setY(getY() + SPEED);
    }
    /** metode for å sette farten til motkommende hindringer */
    public static void setSpeed(int obSpeed){
        SPEED=obSpeed;
    }
}
