package football_prediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Games {

    ArrayList<Game> games;
    private int pendingWeek = 0;   // first week fo unplayed games

    public Games(String webpage) {
        games = new ArrayList<>();

        int week;
        String winner;
        String loser;
        int winnerScore;
        int loserScore;
        boolean isAHomeGameForWinner;
        boolean played;
        int pos;   // position in String search
        int pos2;   // position in String search
        int winnerYards = 0;
        int loserYards = 0;
        int lineCount = 0;

        try {
            URL url = new URL("https://www.pro-football-reference.com/years/2022/games.htm");

            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                winnerScore = 0;
                loserScore = 0;
                played = false;
                lineCount++;
//if (lineCount > 1467) {
//    System.out.println("@1468");
//}
                if (line.contains("week_num\" csk")) {

                    pos = line.indexOf("week_num\" csk");
                    pos = line.indexOf(">", pos + 1);
                    pos2 = line.indexOf("<", pos + 1);

                    if (line.substring(pos, pos2).contains("Wild")) {
                        week = 19;
                    } else if (line.substring(pos, pos2).contains("Division")) {
                        week = 20;
                    } else if (line.substring(pos, pos2).contains("ConfChamp")) {
                        week = 21;
                    } else {
                        //System.out.println(lineCount + " /" + line.substring(pos + 1, pos2) + "/ " + (pos + 1) + " " + pos2);
                        if (pos + 1 == pos2) {
                            continue;
                        }
                        week = Integer.parseInt(line.substring(pos + 1, pos2));
                    }

                    pos = line.indexOf("/teams/", pos2);
                    pos = line.indexOf(">", pos);
                    pos2 = line.indexOf("<", pos);
                    winner = line.substring(pos + 1, pos2);
                    pos = line.indexOf("game_location", pos2);
                    pos = line.indexOf(">", pos);
                    isAHomeGameForWinner = !(line.charAt(pos + 1) == '@');
                    pos = line.indexOf("/teams/", pos2);
                    pos = line.indexOf(">", pos);
                    pos2 = line.indexOf("<", pos);
                    loser = line.substring(pos + 1, pos2);

                    if (line.contains("preview")) {
                        if (pendingWeek == 0) {
                            pendingWeek = week;
                        }
                    } else {
                        pos = line.indexOf("pts_win", pos2);
                        pos = line.indexOf(">", pos + 1);
                        if (line.substring(pos).contains("strong")) {
                            pos = line.indexOf(">", pos + 1);
                        }
                        pos2 = line.indexOf("<", pos);
                        //  System.out.println("79 " + line.substring(pos + 1));
                        winnerScore = Integer.parseInt(line.substring(pos + 1,
                                pos2));
                        pos = line.indexOf("pts_lose", pos2);
                        pos = line.indexOf(">", pos + 1);
                        pos2 = line.indexOf("<", pos);
                        loserScore = Integer.parseInt(line.substring(pos + 1,
                                pos2));
                        pos = line.indexOf("yards_win", pos2);
                        pos = line.indexOf(">", pos + 1);
                        pos2 = line.indexOf("<", pos);
                        winnerYards = Integer.parseInt(line.substring(pos + 1,
                                pos2));
                        pos = line.indexOf("yards_lose", pos2);
                        pos = line.indexOf(">", pos + 1);
                        pos2 = line.indexOf("<", pos);
                        loserYards = Integer.parseInt(line.substring(pos + 1,
                                pos2));
                        // System.out.println(winner + " " + winnerYards + " " + loserYards);

                        played = true;

                    }
                    //System.out.println(week + " " + winner + " " + isAHomeGameForWinner
                    //        + " " + loser + " " + winnerScore + " " + loserScore + " " + played);
                    games.add(new Game(week, winner, loser, winnerScore,
                            loserScore, winnerYards, loserYards, isAHomeGameForWinner, played));
                    //System.out.println(line);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Games.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Games.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Game> getPendingGames() {
        ArrayList<Game> pendingGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getWeek() == pendingWeek) {
                pendingGames.add(game);
            }
        }
        return pendingGames;
    }

    @Override
    public String toString() {
        String ret = "";
        for (Game game : games) {
            if (!(game == null)) {
                if (game.isPlayed()) {
                    ret = ret + game + "\n";
                }
            }
        }
        return ret;
    }

    public ArrayList<Game> getGamesPlayedForTeam(String team) {
        ArrayList<Game> gamesPlayed = new ArrayList<>();
        for (Game game : games) {
            if (!(game == null) && game.isPlayed()
                    && (game.getWinner().equals(team) || game.getLoser().equals(team))) {
                gamesPlayed.add(game);
            }
        }
        return gamesPlayed;
    }

    /**
     * @return the pendingWeek
     */
    public int getPendingWeek() {
        return pendingWeek;
    }

}
