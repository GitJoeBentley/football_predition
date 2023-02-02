package football_prediction;

public class Game {

    private int week = 0;
    private final String winner;
    private final String loser;
    private int winnerScore;
    private final int loserScore;
    private final int winnerYards;
    private final int loserYards;
    private final boolean isAHomeGameForWinner;
    private boolean played = false;  // game is already completed

    public Game(int week, String winner, String loser, int winnerScore,
            int loserScore, int winnerYards, int loserYards,boolean homeGame, boolean played) {
        this.week = week;
        this.winner = winner;
        this.loser = loser;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
        this.winnerYards = winnerYards;
        this.loserYards = loserYards;
        this.isAHomeGameForWinner = homeGame;
        this.played = played;
    }

    /**
     * @return the winnerScore
     */
    public int getWinnerScore() {
        return winnerScore;
    }

    /**
     * @param winnerScore the winnerScore to set
     */
    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    /**
     * @return the loserScore
     */
    public int getLoserScore() {
        return loserScore;
    }

    /**
     * @return the homeGame
     */
    public boolean getIsAHomeGameForWinner() {
        return isAHomeGameForWinner;
    }

    /**
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    /**
     * @return the played
     */
    public boolean isPlayed() {
        return played;
    }

    /**
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * @return the loser
     */
    public String getLoser() {
        return loser;
    }
    
    @Override
    public String toString() {
        String ret = getWeek() + " " + getWinner() + " " + getLoser() + "  "
                + Integer.toString(getWinnerScore()) + "-" + Integer.toString(getLoserScore())
                + " " + isAHomeGameForWinner + " " + isPlayed();
        return ret;
    }

    /**
     * @return the winnerYards
     */
    public int getWinnerYards() {
        return winnerYards;
    }

    /**
     * @return the loserYards
     */
    public int getLoserYards() {
        return loserYards;
    }
}
