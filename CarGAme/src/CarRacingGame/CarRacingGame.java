package CarRacingGame;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;

public class CarRacingGame extends Application {

    private GameManager gameManager;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        gameManager = new GameManager(primaryStage);
        gameManager.menuPane();
    }

    public static void main(String[] args) {
        Application.launch(args);

    }

}
