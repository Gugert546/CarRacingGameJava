package CarRacingGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Car {
    public static ImageView addCar(){

    //henter et bilde for å represntere bilen
        Image carPic = new Image(Car.class.getResource("/lib/CarPic.png").toExternalForm());
        ImageView car = new ImageView(carPic); 

    //setter størrelsen og posisjonen til bilen
        car.setRotate(180);
        car.setFitHeight(70);
        car.setFitWidth(70);
        car.setX(400);
        car.setY(500);

        return car;
    }
    
}
