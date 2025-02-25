/**
* Score class
* Version 1.0
* Audun Halstensen
* 
*/
package CarRacingGame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class RoadMarkings {
    /** metode for å lage gule striper til bakgrunnen */
    public static void addDottedLine(Pane pane) {
        double startX = 400; // Center X position
        double startY = 0;   // Start from the top
        double width = 10;   // Line width
        double height = 30;  // Dash height
        double gap = 30;     // Space between dashes

        // FOR løkke som lager gule striper i veien
        for (double y = startY; y < 600; y += height + gap) {
            Rectangle dash = new Rectangle(startX, y, width, height);
            dash.setFill(Color.YELLOW);
            pane.getChildren().add(dash);
        }
    }
}