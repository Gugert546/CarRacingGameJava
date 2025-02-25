/**
* Score class
* Version 1.0
* Audun Halstensen
* 
*/

package CarRacingGame;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Score {

    private static int score;
    private static String fileName = "CarGAme/src/lib/Leaderboard.txt";
    private static File file = new File(fileName);

    /** lagrer resultatet i en fil(hvis det er høyere en tidligere poengsummer) */
    public static void saveScore() throws IOException {

        PriorityQueue<Integer> pq = new PriorityQueue<>(); // Min-heap for top 5 scores

        // Read existing scores into the min-heap
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                pq.add(scanner.nextInt());
                if (pq.size() > 5)
                    pq.poll(); // Keep only top 5
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found. Creating a new one.");
        }

        // Add new score and maintain top 5
        pq.add(score);
        if (pq.size() > 5)
            pq.poll();

        // Convert to list and sort in descending order
        List<Integer> topScores = new ArrayList<>(pq);
        topScores.sort(Collections.reverseOrder());

        // Write back to file
        try (FileWriter myWriter = new FileWriter(file, false)) {
            for (int s : topScores) {
                myWriter.write(s + "\n");
            }
        }

        System.out.println("Successfully updated the leaderboard.");
    }
    /** leser det som ligger på leaderboard filen */
    public static int[] readScore() throws FileNotFoundException {
        int[] scores = new int[5];
        Arrays.fill(scores, 0);

        try (Scanner scanner = new Scanner(file)) {
            List<Integer> tempScores = new ArrayList<>();
            while (scanner.hasNextInt()) {
                tempScores.add(scanner.nextInt());
            }

            // Sort in descending order and store the top 5
            tempScores.sort(Collections.reverseOrder());
            for (int i = 0; i < Math.min(5, tempScores.size()); i++) {
                scores[i] = tempScores.get(i);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found. Returning default scores.");
        }

        return scores;
    }
    /** øker verdien til score med en */
    public static void scoreUp() {
        score++;
    }
    /** reseter score tilbake til 0 */
    public static void resetScore() {
        score = 0;
    }
    /** returnerer score som int */
    public static int returnScore() {
        return score;
    }
    /** metode for å vise et leaderboard
     *  returnerer en VBox, med de fem høyste poengsummene fra fil. 
      */
    public static VBox leaderboard() throws FileNotFoundException{
            int[] topScores = readScore(); // Get top five scores

        VBox leaderboardBox = new VBox(10); // Spacing of 10 between elements
        leaderboardBox.setStyle("-fx-alignment: center;"); // Center-align text
        leaderboardBox.setLayoutX(610);
        leaderboardBox.setLayoutY(90);

        Label title = new Label("Leaderboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        leaderboardBox.getChildren().add(title);

        // Create labels for each score
        for (int i = 0; i < topScores.length; i++) {
            Label scoreLabel = new Label((i + 1) + ". " + topScores[i]);
            scoreLabel.setStyle("-fx-font-size: 16px;");
            leaderboardBox.getChildren().add(scoreLabel);

            
        }
        return leaderboardBox;
    }
}
