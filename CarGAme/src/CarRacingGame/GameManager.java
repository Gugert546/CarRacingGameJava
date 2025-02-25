/**
* GameManager class
* Version 1.0
* Audun Halstensen
*/

package CarRacingGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameManager {
    //globale variabler
    private Stage primaryStage;
    private Pane gamePane;
    private AnimationTimer gameLoop;
    private Random random = new Random();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ImageView car = Car.addCar();
   
    private Label scoretext;
    private int lives;
    private Label lifes = new Label();
    private int speed=2;
    private Pane menuPane;
    private Scene menu;
    private VBox leaderboardBox;
    
    /** konstruktør for gameManager klassen */
    public GameManager(Stage primaryStage) {
        gamePane = new Pane();
        this.primaryStage = primaryStage;
    }
    /** lager og viser en hovedmey*/
    public void menuPane() throws FileNotFoundException {

        // lager en menupane og fjerner tidligere children
        Pane menuPane = new Pane(); // Initialize menuPane
        Scene gamePlay = new Scene(gamePane, 800, 600);

        menuPane.getChildren().clear();

        // knapp for å starte spillet
        Button startBt = new Button("Start");
        startBt.setLayoutX(375);
        startBt.setLayoutY(450);

        // håndtering av startknapp
        startBt.setOnAction(e -> {
            Obstacle.setSpeed(speed);
            setupGame();
            primaryStage.setScene(gamePlay);
            primaryStage.show();
            startGameLoop();
            handleKeyPress(gamePlay);

        });
        ToggleGroup rgroup = new ToggleGroup();
        RadioButton d1 = new RadioButton();
        
        d1.setToggleGroup(rgroup);
        d1.setSelected(true);
        d1.setOnAction(e->{speed=2;});
        RadioButton d2 = new RadioButton();
        d2.setToggleGroup(rgroup);
        d2.setOnAction(e->{speed=3;});
        RadioButton d3 = new RadioButton();
        d3.setToggleGroup(rgroup);
        d3.setOnAction(e->{speed=5;});
        
        d1.setLayoutX(15);
        d1.setLayoutY(350);
        d2.setLayoutX(35);
        d2.setLayoutY(350);
        d3.setLayoutX(55);
        d3.setLayoutY(350);

        Label dlabel=new Label("Vanskelighetsgrad:");
        dlabel.setLayoutX(10);
        dlabel.setLayoutY(325);
        

        // bakgrunn, satt til samme farge som gifen
        Rectangle r1 = new Rectangle(0, 0, 800, 600);
        r1.setFill(Color.rgb(235, 237, 238));

        // venste hvite rektangle
        Rectangle r2 = new Rectangle(-20, 200, 300, 200);
        r2.setArcWidth(30.0);
        r2.setArcHeight(30.0);
        r2.setFill(Color.WHITE);

        // høyre hvite rektangle
        Rectangle r3 = new Rectangle(600, 80, 200, 390);
        r3.setFill(Color.WHITE);
        r3.setArcWidth(30.0);
        r3.setArcHeight(30.0);

        // animert bil
        Image mocar = new Image(GameManager.class.getResource("/lib/movingcar.gif").toExternalForm());
        ImageView movingCar = new ImageView(mocar);
        movingCar.setFitHeight(150);
        movingCar.setFitWidth(200);
        if (movingCar != null) { // debugg
            System.out.println("gif loaded");
        }

        // path
        Line l1 = new Line(0, 550, 800, 550);

        // animasjon
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(50000));
        pt.setNode(movingCar);
        pt.setPath(l1);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();

        // tittel
        Label tittel = new Label("RacingGame");
        tittel.setLayoutX(325);
        tittel.setLayoutY(250);
        tittel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // instruksjoner for hvordan spille
        String instructions = "instruksjoner : bruk piltastene for å styre bilen, unngå hindringene i veien. du får poeng for alle hindringene du kjører forbi";
        Label leftTxt = new Label(instructions);
        leftTxt.setLayoutX(25);
        leftTxt.setLayoutY(250);
        leftTxt.setWrapText(true);
        leftTxt.setPrefWidth(175);

        VBox leaderboardBox = Score.leaderboard();
        this.leaderboardBox=leaderboardBox;
        // legger til elementene på hovedmenyen
        menuPane.getChildren().addAll(r1, r2, r3, leftTxt, startBt, tittel, movingCar, leaderboardBox, d1,d2,d3,dlabel);
        this.menuPane=menuPane;
        Scene menu = new Scene(menuPane, 800, 600);
        primaryStage.setTitle("CarRacingGame");
        primaryStage.setScene(menu);
        primaryStage.setResizable(false);
        primaryStage.show();
        this.menu=menu;
    }
    /** metode for å håndtere key-input, flytter også bilen på x-aksen */
    public void handleKeyPress(Scene gamePlay) {

        gamePlay.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    car.setTranslateX(car.getTranslateX() - 10);
                    break;
                case RIGHT:
                    car.setTranslateX(car.getTranslateX() + 10);
                    break;
                case R:
                    restartGame();
                    break;
                case ESCAPE:
                    pauseGame();
                    break;
                default:
                    break;
            }
        });
    }
    /**metode for å oppdatere poengtavla på hovedmenyen */
    public void updateMenu() throws FileNotFoundException{
        menuPane.getChildren().remove(leaderboardBox);
        leaderboardBox=Score.leaderboard();
        menuPane.getChildren().add(leaderboardBox);
    }
    /**en metode for å håndtere et game-over scenario*/
    public void gameOver() throws IOException {
        System.out.println("Game Over!"); // Debugging
        gameLoop.stop();
        lives = 3;
        Score.saveScore();
        Score.resetScore();
        
        // game over beskjed
        Label gameOverLabel = new Label("Game Over! Press 'R' to restart.");
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        gameOverLabel.setLayoutX(150);
        gameOverLabel.setLayoutY(300);

        Button menuBt = new Button("til hovedmenyen");
        menuBt.setLayoutX(350);
        menuBt.setLayoutY(400);

        Button restartBt = new Button("restart");
        restartBt.setLayoutX(375);
        restartBt.setLayoutY(500);

        restartBt.setOnAction(e -> {
            restartGame();
            gamePane.getChildren().remove(menuBt);
            gamePane.getChildren().remove(restartBt);
        });
        menuBt.setOnAction(e -> {
            try {
                updateMenu();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            primaryStage.setScene(menu);
            gamePane.getChildren().clear(); 
            obstacles.clear();
        });
        gamePane.getChildren().addAll(menuBt, restartBt,gameOverLabel);
    }
    /**metode for å restarte spillet*/
    public void restartGame() {
        gamePane.getChildren().clear(); // Clear game objects
        obstacles.clear();

        // Restart logic (add background, car, etc.)
        setupGame();
        gameLoop.start();

    }
    /**metode for å pause spillet- lager og viser en pausemeny*/
    public void pauseGame() {
        if (gameLoop != null) {
            gameLoop.stop();
            Button menuBt = new Button("til hovedmenyen");
            menuBt.setLayoutX(350);
            menuBt.setLayoutY(400);

            Button resumeBt = new Button("fortsett");
            resumeBt.setLayoutX(375);
            resumeBt.setLayoutY(500);

            resumeBt.setOnAction(e -> {
                gameLoop.start();
                gamePane.getChildren().remove(menuBt);
                gamePane.getChildren().remove(resumeBt);
                
            });
            menuBt.setOnAction(e -> {
                primaryStage.setScene(menu);
                gamePane.getChildren().clear(); 
                obstacles.clear();
                
            });
            gamePane.getChildren().addAll(menuBt, resumeBt);
        }

    }
    /**metode for å sette opp spillet*/
    public void setupGame() {
        
        gamePane.getChildren().clear();
        // setter bakgrunnsfarge for veien
        Rectangle r1 = new Rectangle(10, 0, 780, 600);
        r1.setFill(Color.GREY);

        // lager autovern
        Rectangle r2 = new Rectangle(0, 0, 10, 600);
        r2.setFill(Color.BLACK);
        Rectangle r3 = new Rectangle(790, 0, 10, 600);
        r3.setFill(Color.BLACK);
        // legger til bakgrunnsellementer i pane
        gamePane.getChildren().addAll(r1, r2, r3);

        // lager gule striper i veien
        RoadMarkings.addDottedLine(gamePane);
        // legger til bilen i pane
        gamePane.getChildren().add(car);
        // legger til liv counter
        lives = 3;
        gamePane.getChildren().add(lifes);
        lifes.setText("liv: " + lives);
        lifes.setLayoutX(10);
        lifes.setLayoutY(30);

        Label scoretext = new Label();
        this.scoretext = scoretext;
        scoretext.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        scoretext.setText("o");
        scoretext.setLayoutX(400);
        scoretext.setLayoutY(100);
        gamePane.getChildren().add(scoretext);
    }
    /**metode for å lage nye hindringer*/
    public void spawnObstacle() {
        double x = random.nextDouble() * (800 - 50); // Random X position
        Obstacle obstacle = new Obstacle(x, 0); // Start at the top
        obstacles.add(obstacle);
        gamePane.getChildren().add(obstacle);
    }
    /**  metode for å oppdatere hindringer(også ansvarlig for kollisjoner)*/
    public void updateObstacles() throws IOException {
        Iterator<Obstacle> iterator = obstacles.iterator();

        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.moveDown();
            if (car.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
                gamePane.getChildren().remove(obstacle);
                iterator.remove();
                lives--;
                System.out.println(lives);
                lifes.setText("liv:" + lives);
                System.out.println(lives);
            }

            // fjerner hindringene når de når bunn av skjermen
            if (obstacle.getY() > 600) {
                gamePane.getChildren().remove(obstacle);
                iterator.remove();
                Score.scoreUp();
                scoretext.setText("score: " + Score.returnScore());

            }
            if (lives == 0) {
                gameOver();
            }
        }
    }
    /** lager en game loop -startes ved trykk på start knappen i hovedmenyen*/
    public void startGameLoop() {
        this.lives = 3;
        Score.resetScore();
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastSpawnTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastSpawnTime > 500_000_000) { // Spawn every second to-do endre til en variabel
                                                         // vanskelighetsgrad
                    spawnObstacle();
                    lastSpawnTime = now;
                }
                try {
                    updateObstacles();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };
        gameLoop.start();
        this.gameLoop = gameLoop;
    }
}
