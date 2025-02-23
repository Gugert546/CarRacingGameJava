package CarRacingGame;
//lagre score, lese score, returne array som kan vises i hbox i menyen

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class Score {

    private static int score;
    private static String fileName = "CarRacingGameJava/CarGAme/src/lib/test.txt";
    private static File file = new File(fileName);

    // printe til fil
    public static void saveScore() throws IOException {

        System.out.println(file.getAbsolutePath());

        FileWriter myWriter = new FileWriter(file, true);

        if (myWriter != null) {
            System.out.println("fil ok");
        }
        myWriter.write(score + " ");
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    }

    // lese fra fil
    public void readScore() throws FileNotFoundException {
        String fileName = "CarRacingGameJava/CarGAme/src/lib/test.txt";
        File file = new File(fileName);

        Scanner scanner = new Scanner(file);
        String a = scanner.nextLine();
        scanner.close();
        System.out.println(a);

    }

    public static void scoreUp() {
        score++;
    }

    public static void resetScore() {
        score = 0;
    }

    public static int returnScore() {
        return score;
    }

}
