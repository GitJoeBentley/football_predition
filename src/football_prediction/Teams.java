package football_prediction;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Teams {

    private final TreeMap<String, TeamData> teams;

    public Teams(String teamFile) {
        teams = new TreeMap<>();
        getTeams(teamFile);
    }

    private void getTeams(String filename) {
        String conf = "";
        String div = "";
        String buffer;
        TeamData tempTeamData;
        try {

            BufferedReader in
                    = new BufferedReader(new FileReader(filename));
            in.readLine();  // read and ignore the first line this the teams file

            while ((buffer = in.readLine()) != null) {
                if (buffer.length() < 2) {
                    // skip a blank line
                } else if (buffer.contains("AFC") || buffer.contains("NFC")) {
                    conf = buffer.substring(0, 3);
                    div = buffer.substring(4);
                } else {
                    tempTeamData = new TeamData(conf, div);
                    teams.put(buffer, tempTeamData);
                }
            }
        } catch (IOException e) {
            System.out.println(filename + " does not exist\n");
        }
    }

    public void getStats(Games games) {
        String name;  // team name
        TeamData teamData;
        int pointsFor;
        int pointsAgainst;
        int cumPointsFor;       // cumulative
        int cumPointsAgainst;   // cumulative
        int cumYardsGained;
        int cumYardsGivenUp;
        int wins;
        int losses;
        int ties;
        int numberOfGamesPlayed;
        int margin;
        //float momentumDivisor = 0.0f;
        float momentum;
        int divisor;
        float divisorSum;
        int homeTeamAdvantage;
        int cumHomeTeamAdvantage;
        for (Map.Entry team : teams.entrySet()) {
            name = (String) team.getKey();
            // System.out.println(name);
            teamData = (TeamData) team.getValue();
            wins = 0;
            losses = 0;
            ties = 0;
            momentum = 0.0f;
            divisorSum = 0.0f;
            cumPointsFor = 0;
            cumPointsAgainst = 0;
            cumHomeTeamAdvantage = 0;
            cumYardsGained = 0;
            cumYardsGivenUp = 0;

            ArrayList<Game> gamesPlayed = games.getGamesPlayedForTeam(name);
            numberOfGamesPlayed = gamesPlayed.size();
            divisor = numberOfGamesPlayed + 1;
            for (Game game : gamesPlayed) {
                // Look for a tie
                if (game.getWinnerScore() == game.getLoserScore()) {
                    pointsFor = game.getWinnerScore();
                    pointsAgainst = game.getLoserScore();
                    cumPointsFor += pointsFor;
                    cumPointsAgainst += pointsAgainst;
                    cumYardsGained += game.getWinnerYards();
                    cumYardsGivenUp += game.getLoserYards();
                    ties++;
                } else if (game.getWinner().equals(name)) {
                    pointsFor = game.getWinnerScore();
                    pointsAgainst = game.getLoserScore();
                    cumPointsFor += pointsFor;
                    cumYardsGained += game.getWinnerYards();
                    cumYardsGivenUp += game.getLoserYards();
                    cumPointsAgainst += pointsAgainst;
                    wins++;
                } else {
                    pointsFor = game.getLoserScore();
                    pointsAgainst = game.getWinnerScore();
                    cumPointsFor += pointsFor;
                    cumPointsAgainst += pointsAgainst;
                    cumYardsGained += game.getLoserYards();
                    cumYardsGivenUp += game.getWinnerYards();
                    losses++;
                }
                margin = pointsFor - pointsAgainst;
                if (margin > 0 && game.getIsAHomeGameForWinner()
                        || margin < 0 && !game.getIsAHomeGameForWinner()) {
                    homeTeamAdvantage = margin;
                } else {
                    homeTeamAdvantage = -margin;
                }

                momentum = momentum + (1.0f / divisor) * margin;
                divisorSum = divisorSum + 1.0f / divisor;
                divisor--;
                cumHomeTeamAdvantage += homeTeamAdvantage;
            }
            momentum = momentum / divisorSum;
            //System.out.println(name + " Mom = " + momentum);
            //numberOfGamesPlayed = wins + losses + ties;
            teamData.setWins(wins);
            teamData.setLosses(losses);
            teamData.setTies(ties);
            teamData.setPointsFor(cumPointsFor);
            teamData.setPointsAgainst(cumPointsAgainst);
            teamData.setGamesPlayed(wins + losses + ties);
            teamData.setPercent((wins + 0.5f * ties) / (float) numberOfGamesPlayed);
            teamData.setAverageMargin((float) (cumPointsFor - cumPointsAgainst) / numberOfGamesPlayed);
            teamData.setMomentum(momentum);
            teamData.setAverageYardsGained((float) cumYardsGained / numberOfGamesPlayed);
            teamData.setAverageYardsGivenUp((float) cumYardsGivenUp / numberOfGamesPlayed);
            teamData.setHomeTeamAdvantage((float) cumHomeTeamAdvantage / numberOfGamesPlayed);
            teamData.setPointsPerYardGained((float) cumPointsFor / cumYardsGained);
            teamData.setPointsGivenUpPerYardGivenUp((float) cumPointsAgainst / cumYardsGivenUp);

            teams.put(name, teamData);
            //System.out.println(name + "  " + numberOfGamesPlayed);
        }
        getDifficultyOfScheduleAndStrength(games);
    }

    public void getDifficultyOfScheduleAndStrength(Games games) {
        String name;  // team name
        TeamData teamData;
        int numberOfGamesPlayed;
        String opponent;
        float sumOfAverageMargins;
        float difficultyOfSchedule;

        for (Map.Entry team : teams.entrySet()) {
            name = (String) team.getKey();
            // System.out.println(name);
            teamData = (TeamData) team.getValue();
            ArrayList<Game> gamesPlayed = games.getGamesPlayedForTeam(name);
            numberOfGamesPlayed = gamesPlayed.size();
            sumOfAverageMargins = 0.0f;
            for (Game game : gamesPlayed) {
                if (name.equals(game.getWinner())) {
                    opponent = game.getLoser();
                } else {
                    opponent = game.getWinner();
                }
                sumOfAverageMargins += teams.get(opponent).getAverageMargin();
            }
            difficultyOfSchedule = sumOfAverageMargins / numberOfGamesPlayed;
            teamData.setDifficultyOfSchedule(difficultyOfSchedule);
            teamData.setStrength((teamData.getAverageMargin() + teamData.getMomentum()) / 2.0f + difficultyOfSchedule);
            teams.put(name, teamData);
        }
    }

    private void sortForStandings(ArrayList<String> teamNames, ArrayList<TeamData> teamData) {
        int minIndex;
        float minIndexSortKey;
        float jSortKey;
        String tempTeamName;
        TeamData tempTeamData;

        for (int i = 0; i < teamData.size() - 1; i++) {
            minIndex = i;

            for (int j = i + 1; j < teamData.size(); j++) {
                jSortKey = teamData.get(j).sortKey();
                minIndexSortKey = teamData.get(minIndex).sortKey();
                if (jSortKey < minIndexSortKey) {
                    //   if (teams[j].sortKey() < teams[minIndex].sortKey()) {
                    //if (teamsList[j] < teams[minIndex].sortKey()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                // swap ArrayList elements
                tempTeamName = teamNames.get(i);
                tempTeamData = teamData.get(i);
                teamNames.set(i, teamNames.get(minIndex));
                teamData.set(i, teamData.get(minIndex));
                teamNames.set(minIndex, tempTeamName);
                teamData.set(minIndex, tempTeamData);
            }
        }

    }

    void printStandings() {
        ArrayList<String> teamNames = new ArrayList<>();
        ArrayList<TeamData> teamData = new ArrayList<>();
        Set<Map.Entry<String, TeamData>> entries = teams.entrySet();
        for (Map.Entry<String, TeamData> entry : entries) {
            teamNames.add(entry.getKey());
            teamData.add(entry.getValue());
        }
        sortForStandings(teamNames, teamData);
        for (int i = 0; i < teamData.size(); i++) {
            if (i % 16 == 0) {
                if (teamData.get(i).getConference().equals("AFC")) {
                    System.out.print("American ");
                } else {
                    System.out.print("National ");
                }
                System.out.println("Football Conference");
            }
            if (i % 4 == 0) {
                System.out.print(teamData.get(i).getConference() + " ");
                System.out.printf("%-20s", teamData.get(i).getDivision());

                System.out.println("  W  L  T    Pct   PF   PA  AvMg   Mom YdsGn YdsGU   DofS   Str   HTA");
            }
            System.out.printf("%-24s", teamNames.get(i));
            System.out.printf("%3d", teamData.get(i).getWins());
            System.out.printf("%3d", teamData.get(i).getLosses());
            System.out.printf("%3d", teamData.get(i).getTies());
            System.out.printf("%7.3f", teamData.get(i).getPercent());
            System.out.printf("%5d", teamData.get(i).getPointsFor());
            System.out.printf("%5d", teamData.get(i).getPointsAgainst());
            System.out.printf("%6.1f", teamData.get(i).getAverageMargin());
            System.out.printf("%6.1f", teamData.get(i).getMomentum());
            System.out.printf("%6.0f", teamData.get(i).getAverageYardsGained());
            System.out.printf("%6.0f", teamData.get(i).getAverageYardsGivenUp());
            System.out.printf("%7.2f", teamData.get(i).getDifficultyOfSchedule());
            System.out.printf("%6.1f", teamData.get(i).getStrength());
            System.out.printf("%6.1f", teamData.get(i).getHomeTeamAdvantage());
            System.out.printf("%7.3f", teamData.get(i).getPointsPerYardGained());
            System.out.print("\n");
            if (i % 4 == 3) {
                System.out.println("");
            }
        }
    }

    public String predictWinner(String homeTeam, String awayTeam) {
        String prediction;
        float homeTS = teams.get(homeTeam).getStrength();  // home team strength
        float awayTS = teams.get(awayTeam).getStrength();  // away team strength
        float htaForHomeTeam = teams.get(homeTeam).getHomeTeamAdvantage();
        float htaForAwayTeam = teams.get(awayTeam).getHomeTeamAdvantage();
        float homeExpectation = homeTS + htaForHomeTeam / 2.0f;
        float awayExpectation = awayTS - htaForAwayTeam / 2.0f;

        float margin = homeExpectation - awayExpectation;
        String marginStr = String.format("%.0f", Math.abs(margin));
        if (margin > 0.5f) {
            prediction = homeTeam + " by " + marginStr;
        } else if (margin < -0.5f) {
            prediction = awayTeam + " by " + marginStr;
        } else {
            prediction = "tie";
        }

        return prediction;
    }

    public String predictWinnerBasedOnYards(String homeTeam, String awayTeam) {
        String predictionBasedOnYards;
        float htaForHomeTeam = teams.get(homeTeam).getHomeTeamAdvantage();
        float htaForAwayTeam = teams.get(awayTeam).getHomeTeamAdvantage();

        // Prediction based on yards gained and yards given up
        float homeExpectedYards = (teams.get(homeTeam).getAverageYardsGained()
                + teams.get(awayTeam).getAverageYardsGivenUp()) / 2.0f;
        float homeExpectedPointsBasedOnYards = homeExpectedYards * teams.get(homeTeam).getPointsPerYardGained();
        
        float awayExpectedYards = (teams.get(awayTeam).getAverageYardsGained()
                + teams.get(homeTeam).getAverageYardsGivenUp()) / 2.0f;
        float awayExpectedPointsBasedOnYards = awayExpectedYards * teams.get(awayTeam).getPointsPerYardGained();

        //System.out.println(homeExpectedPointsBasedOnYards + "  " + awayExpectedPointsBasedOnYards);
        float marginBasedOnYards
                = (homeExpectedPointsBasedOnYards + htaForHomeTeam / 2.0f)
                - (awayExpectedPointsBasedOnYards - htaForAwayTeam / 2.0f);
        String marginStr = String.format("%.0f", Math.abs(marginBasedOnYards));
        if (marginBasedOnYards > 0.5f) {
            predictionBasedOnYards = homeTeam + " by " + marginStr;
        } else if (marginBasedOnYards < -0.5f) {
            predictionBasedOnYards = awayTeam + " by " + marginStr;
        } else {
            predictionBasedOnYards = "tie";
        }

        //System.out.println(predictionBasedOnYards);
        return predictionBasedOnYards;
    }

    @Override
    public String toString() {
        String ret = "";
        String teamName;
        Set<Map.Entry<String, TeamData>> entries = teams.entrySet();
        for (Map.Entry<String, TeamData> entry : entries) {
            teamName = String.format("%-24s", entry.getKey());
            ret = ret + teamName + entry.getValue() + "\n";
        }
        return ret;
    }
}
