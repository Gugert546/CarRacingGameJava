package CarRacingGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameManager{
    private Stage primaryStage;
    private Pane gamePane;
    private AnimationTimer gameLoop;
    private Random random = new Random();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ImageView car =Car.addCar();
    private Scene menu;
    private int score;
    private Label scoretext;
public GameManager(Stage primaryStage) {
        gamePane= new Pane();
        this.primaryStage=primaryStage;
    }

public void menuPane(){
     //lager en menupane
        
         Pane menuPane = new Pane(); // Initialize menuPane
         Scene gamePlay = new Scene(gamePane, 800, 600);
         menuPane.getChildren().clear();

    //knapp for å starte spillet
          Button startBt = new Button("Start");
          startBt.setLayoutX(400);
          startBt.setLayoutY(500);
           //håndtering av startknapp
           startBt.setOnAction(e ->{
            
            setupGame();
            primaryStage.setScene(gamePlay);
            primaryStage.show();
            startGameLoop();
            handleKeyPress(gamePlay);

             });
        Rectangle r1 = new Rectangle(0,0,800,600);     
        r1.setFill(Color.RED);

        Label tittel=new Label("RacingGame");
        tittel.setLayoutX(350);
        tittel.setLayoutY(250);
        tittel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

     //instruksjoner for hvordan spille
         String instructions = "instruksjoner : bruk piltastene for å styre bilen, unngå hindringene i veien";
         Label leftTxt = new Label(instructions);
         leftTxt.setLayoutX(100);
         leftTxt.setLayoutY(400);
         leftTxt.setWrapText(true);
         leftTxt.setPrefWidth(175);
 
     //legger til elementene på hovedmenyen
         menuPane.getChildren().addAll(r1,leftTxt,startBt,tittel);

         Scene menu = new Scene(menuPane,800, 600);
         this.menu=menu;
         primaryStage.setTitle("CarRacingGame");
         primaryStage.setScene(menu);
         primaryStage.setResizable(false);
         primaryStage.show();
}
//flytter bilen på x-aksen
public void handleKeyPress(Scene gamePlay) {
      
        gamePlay.setOnKeyPressed(e ->{
            switch (e.getCode()) {
                case LEFT: car.setTranslateX(car.getTranslateX() - 10); break;
                case RIGHT: car.setTranslateX(car.getTranslateX() + 10); break;
                case R: restartGame(); break;
                case ESCAPE: pauseGame(); break;
                default:
                    break;
            }
        });
    }
public void gameOver() {
    System.out.println("Game Over!"); // Debugging
    gameLoop.stop();
    lives=3;
    //game over beskjed
    Label gameOverLabel = new Label("Game Over! Press 'R' to restart.");
    gameOverLabel.setTextFill(Color.RED);
    gameOverLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    gameOverLabel.setLayoutX(250);
    gameOverLabel.setLayoutY(300);

     Button restartButton = new Button("Restart");
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(300);
        restartButton.setOnAction(e -> restartGame());
    
    gamePane.getChildren().addAll(gameOverLabel,restartButton);
    
}
public void restartGame() {
    gamePane.getChildren().clear(); // Clear game objects
    obstacles.clear();

// Restart logic (add background, car, etc.)
    setupGame();
    gameLoop.start();
    
}

public void pauseGame(){
    if(gameLoop != null){
    gameLoop.stop();
    Button menuBt=new Button("til hovedmenyen");
    menuBt.setLayoutX(250);
    menuBt.setLayoutY(500);
    
    Button resumeBt= new Button("fortsett");
    resumeBt.setLayoutX(550);
    resumeBt.setLayoutY(500);
    
    resumeBt.setOnAction(e->{
        gameLoop.start();
        gamePane.getChildren().remove(menuBt);
        gamePane.getChildren().remove(resumeBt);
    });
    menuBt.setOnAction(e->{
        primaryStage.setScene(menu);
        gamePane.getChildren().remove(menuBt);
        gamePane.getChildren().remove(resumeBt);
    });
    gamePane.getChildren().addAll(menuBt,resumeBt);
    }
    
}
public void setupGame(){
    int lives=3;
    this.score=0;
    
    this.lives=lives;
    gamePane.getChildren().clear();
//setter bakgrunnsfarge for veien
    Rectangle r1 = new Rectangle(10,0,780,600);
    r1.setFill(Color.GREY);

// lager autovern
     Rectangle r2 = new Rectangle(0,0,10,600);
     r2.setFill(Color.BLACK);
     Rectangle r3 = new Rectangle(790,0,10,600);
     r3.setFill(Color.BLACK);
//legger til bakgrunnsellementer i pane
    gamePane.getChildren().addAll(r1,r2,r3);

//lager gule striper i veien
    RoadMarkings.addDottedLine(gamePane);
//legger til bilen i pane
    gamePane.getChildren().add(car);

    Label scoretext=new Label();
            this.scoretext=scoretext;
            scoretext.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            scoretext.setText("o");
            scoretext.setLayoutX(400);
            scoretext.setLayoutY(100);
            gamePane.getChildren().add(scoretext);
}


//metode for å lage nye hindringer
    public void spawnObstacle() {
        double x = random.nextDouble() * (800 - 50); // Random X position
        Obstacle obstacle = new Obstacle(x, 0); // Start at the top
        obstacles.add(obstacle);
        gamePane.getChildren().add(obstacle);
    }

    //metode for å oppdatere hindringer
    int lives;
public void updateObstacles() {
            Iterator<Obstacle> iterator = obstacles.iterator();
            
            while (iterator.hasNext()) {
                Obstacle obstacle = iterator.next();
                obstacle.moveDown();
                if (car.getBoundsInParent().intersects(obstacle.getBoundsInParent())){
                    lives = lives-1;
                    if( lives == 0 ){
                        gameOver();
                    };
                }
                
    
         // fjerner hindringene når de når bunn av skjermen
                  if (obstacle.getY() > 600) {
                        gamePane.getChildren().remove(obstacle);
                        iterator.remove();
                        this.score++;
                        scoretext.setText(""+this.score);
                        
                    }
            }
        }
// lager en game loop -startes ved trykk på start knappen i hovedmenyen
public void startGameLoop() {
    AnimationTimer gameLoop = new AnimationTimer() {
        private long lastSpawnTime = 0;

        @Override
        public void handle(long now) {
            if (now - lastSpawnTime > 1_000_000_000) { // Spawn every second to-do endre til en variabel vanskelighetsgrad
                spawnObstacle();
                lastSpawnTime = now;
            }
            updateObstacles();
        }
        
    };
    gameLoop.start();
    this.gameLoop=gameLoop;
}
}
    
        //poengtavle
             /* 
        //bruk hBox for å legge en variabel på hver linje, variabelen tar vare på tiden som blir brukt. er det mulig å legge flere hbox samlet inn i pane? mener seff vBox
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15,5,5,5));
        vBox.getChildren().add(new Label("highscores"));
        
        //Label[] highscores = {new Label(poengsum)
             */