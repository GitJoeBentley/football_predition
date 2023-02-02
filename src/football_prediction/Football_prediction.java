package football_prediction;

// Game data from:  https://www.pro-football-reference.com/years/2022/games.htm
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Football_prediction {

    public static void main(String[] args) {
        Teams nfl = new Teams("nfl_teams.txt");
        Games games = new Games("https://www.pro-football-reference.com/years/2022/games.htm");
        nfl.getStats(games);
        nfl.printStandings();
        predictNextGames(nfl, games);
    }

    public static void predictNextGames(Teams nfl, Games games) {
        ArrayList<Game> pendingGames = games.getPendingGames();
        int pendingWeek = games.getPendingWeek();
        String date  = 
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyy"));
        String OutputFilename = "c:/temp/football_predictions_" + date + ".txt";
        String scheduledGame;
        try {
            PrintWriter fout = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(OutputFilename)));
            // Print headings
            System.out.println("\nGames for Week " + pendingWeek + "\n");
            fout.println("\nGames for Week " + pendingWeek + "\n\n");
            for (Game game : pendingGames) {
                if (!game.isPlayed()) {
                    scheduledGame = game.getWinner() + " at " + game.getLoser();
                    System.out.printf("%-46s", scheduledGame);
                    System.out.println(nfl.predictWinner(game.getLoser(), game.getWinner()));
                    fout.printf("%-46s", scheduledGame);
                    fout.println(nfl.predictWinner(game.getLoser(), game.getWinner()) + "\n");
                }

            }
            fout.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        OutputFilename = "c:/temp/football_predictions_based_on_yardage_" + date + ".txt";
        try {
            PrintWriter fout = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(OutputFilename)));
            // Print headings
            System.out.println("\nGames for Week " + pendingWeek + " {Predictions based on yardage)\n");
            fout.println("\nGames for Week " + pendingWeek + "\n\n");
            for (Game game : pendingGames) {
                if (!game.isPlayed()) {
                    scheduledGame = game.getWinner() + " at " + game.getLoser();
                    System.out.printf("%-46s", scheduledGame);
                    System.out.println(nfl.predictWinnerBasedOnYards(game.getLoser(), game.getWinner()));
                    fout.printf("%-46s", scheduledGame);
                    fout.println(nfl.predictWinner(game.getLoser(), game.getWinner()) + "\n");
                }

            }
            fout.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
}
