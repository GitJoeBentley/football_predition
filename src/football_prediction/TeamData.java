package football_prediction;

public class TeamData {

    /**
     * @return the pointsGivenUpPerYardGivenUp
     */
    public float getPointsGivenUpPerYardGivenUp() {
        return pointsGivenUpPerYardGivenUp;
    }

    /**
     * @param pointsGivenUpPerYardGivenUp the pointsGivenUpPerYardGivenUp to set
     */
    public void setPointsGivenUpPerYardGivenUp(float pointsGivenUpPerYardGivenUp) {
        this.pointsGivenUpPerYardGivenUp = pointsGivenUpPerYardGivenUp;
    }

    /**
     * @return the pointsPerYardGained
     */
    public float getPointsPerYardGained() {
        return pointsPerYardGained;
    }

    /**
     * @param pointsPerYardGained the pointsPerYardGained to set
     */
    public void setPointsPerYardGained(float pointsPerYardGained) {
        this.pointsPerYardGained = pointsPerYardGained;
    }

    /**
     * @return the conference
     */
    public String getConference() {
        return conference;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }
    private String conference;
    private String division;
    private int wins = 0;
    private int losses = 0;
    private int ties = 0;
    private int gamesPlayed = 0;
    private int pointsFor = 0;
    private int pointsAgainst = 0;
    private float percent = 0.0f;
    private float averageMargin = 0.0f;    
    private float momentum = 0.0f;
    private float averageYardsGained = 0.0f;
    private float averageYardsGivenUp = 0.0f;
    private float difficultyOfSchedule = 0.0f;
    private float homeTeamAdvantage = 0.0f;
    private float strength = 0.0f;
    private float pointsPerYardGained = 0.0f;
    private float pointsGivenUpPerYardGivenUp = 0.0f;
    
    public TeamData(String conference, String division) {
        this.conference = conference;
        this.division = division;  
    }
    
    @Override
    public String toString() {
        return getConference() + " " + getDivision() + " " + getWins() + "-" + getLosses() + "-" + getTies() + 
                " " + getPercent() + " " + getPointsFor() + " " + getPointsAgainst() + " " + 
                getAverageMargin() + " " + getMomentum() + /* " " + homeTeamAdvantage + */
                " " + getDifficultyOfSchedule() + " " + getStrength();
    }
    
    public float sortKey() {
        char firstCharOfConference = getConference().charAt(0);
        char firstCharOfDivision = getDivision().charAt(0);
        int conferenceAsAnInt = (int) firstCharOfConference;
        int divisionAsAnInt = (int) firstCharOfDivision;
        float pctInReverseOrder = 1.00f - getPercent();
        float key = 100.0f * conferenceAsAnInt + divisionAsAnInt + pctInReverseOrder;
        return key;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * @param losses the losses to set
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * @param ties the ties to set
     */
    public void setTies(int ties) {
        this.ties = ties;
    }

    /**
     * @param gamesPlayed the gamesPlayed to set
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * @param pointsFor the pointsFor to set
     */
    public void setPointsFor(int pointsFor) {
        this.pointsFor = pointsFor;
    }

    /**
     * @param pointsAgainst the pointsAgainst to set
     */
    public void setPointsAgainst(int pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }

    /**
     * @param averageMargin the averageMargin to set
     */
    public void setAverageMargin(float averageMargin) {
        this.averageMargin = averageMargin;
    }

    /**
     * @param momentum the momentum to set
     */
    public void setMomentum(float momentum) {
        this.momentum = momentum;
    }

    /**
     * @param difficultyOfSchedule the difficultyOfSchedule to set
     */
    public void setDifficultyOfSchedule(float difficultyOfSchedule) {
        this.difficultyOfSchedule = difficultyOfSchedule;
    }

    /**
     * @param homeTeamAdvantage the homeTeamAdvantage to set
     */
    public void setHomeTeamAdvantage(float homeTeamAdvantage) {
        this.homeTeamAdvantage = homeTeamAdvantage;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(float strength) {
        this.strength = strength;
    }

    /**
     * @return the averageMargin
     */
    public float getAverageMargin() {
        return averageMargin;
    }

    /**
     * @return the momentum
     */
    public float getMomentum() {
        return momentum;
    }

    /**
     * @param conference the conference to set
     */
    public void setConference(String conference) {
        this.conference = conference;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return the losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * @return the ties
     */
    public int getTies() {
        return ties;
    }

    /**
     * @return the gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @return the pointsFor
     */
    public int getPointsFor() {
        return pointsFor;
    }

    /**
     * @return the pointsAgainst
     */
    public int getPointsAgainst() {
        return pointsAgainst;
    }

    /**
     * @return the percent
     */
    public float getPercent() {
        return percent;
    }

    /**
     * @return the difficultyOfSchedule
     */
    public float getDifficultyOfSchedule() {
        return difficultyOfSchedule;
    }

    /**
     * @return the homeTeamAdvantage
     */
    public float getHomeTeamAdvantage() {
        return homeTeamAdvantage;
    }

    /**
     * @return the strength
     */
    public float getStrength() {
        return strength;
    }

    /**
     * @param averageYardsGained the averageYardsGained to set
     */
    public void setAverageYardsGained(float averageYardsGained) {
        this.averageYardsGained = averageYardsGained;
    }

    /**
     * @param averageYardsGivenUp the averageYardsGivenUp to set
     */
    public void setAverageYardsGivenUp(float averageYardsGivenUp) {
        this.averageYardsGivenUp = averageYardsGivenUp;
    }

    /**
     * @return the averageYardsGained
     */
    public float getAverageYardsGained() {
        return averageYardsGained;
    }

    /**
     * @return the averageYardsGivenUp
     */
    public float getAverageYardsGivenUp() {
        return averageYardsGivenUp;
    }
}
