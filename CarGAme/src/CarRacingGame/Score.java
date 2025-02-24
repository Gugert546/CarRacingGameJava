package CarRacingGame;
//lagre score, lese score, returne array som kan vises i hbox i menyen

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

public class Score {

    private static int score;
    private static String fileName = "CarRacingGameJava/CarGAme/src/lib/test.txt";
    private static File file = new File(fileName);

    // printe til fil
    public static void saveScore() throws IOException {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Min-heap for top 5 scores

        // Read existing scores into the min-heap
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                minHeap.add(scanner.nextInt());
                if (minHeap.size() > 5)
                    minHeap.poll(); // Keep only top 5
            }
        } catch (FileNotFoundException e) {
            System.out.println("Leaderboard file not found. Creating a new one.");
        }

        // Add new score and maintain top 5
        minHeap.add(score);
        if (minHeap.size() > 5)
            minHeap.poll();

        // Convert to list and sort in descending order
        List<Integer> topScores = new ArrayList<>(minHeap);
        topScores.sort(Collections.reverseOrder());

        // Write back to file
        try (FileWriter myWriter = new FileWriter(file, false)) {
            for (int s : topScores) {
                myWriter.write(s + "\n");
            }
        }

        System.out.println("Successfully updated the leaderboard.");
    }

    // lese fra fil
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
