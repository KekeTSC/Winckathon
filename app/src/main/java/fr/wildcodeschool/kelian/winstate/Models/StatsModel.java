package fr.wildcodeschool.kelian.winstate.Models;


public class StatsModel {
    private String conditionOfOther = "";
    private boolean response;
    private String lastResult = ""; //3possibilité
    private int happiness;
    private boolean isBetterThanOther;
    private String photoUrlResponse;
    private String photoUrlGame;
    private int score;

    public StatsModel() {
    }

    public StatsModel(String conditionOfOther, boolean response, String lastResult, boolean asWon, int happiness, boolean isBetterThanOther, String photoUrlResponse, String photoUrlGame, int score) {
        this.conditionOfOther = conditionOfOther;
        this.response = response;
        this.lastResult = lastResult;
        this.happiness = happiness;
        this.isBetterThanOther = isBetterThanOther;
        this.photoUrlResponse = photoUrlResponse;
        this.photoUrlGame = photoUrlGame;
        this.score = score;
    }

    public String getConditionOfOther() {
        return conditionOfOther;
    }

    public void setConditionOfOther(String conditionOfOther) {
        this.conditionOfOther = conditionOfOther;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public boolean isBetterThanOther() {
        return isBetterThanOther;
    }

    public void setBetterThanOther(boolean betterThanOther) {
        isBetterThanOther = betterThanOther;
    }

    public String getPhotoUrlResponse() {
        return photoUrlResponse;
    }

    public void setPhotoUrlResponse(String photoUrlResponse) {
        this.photoUrlResponse = photoUrlResponse;
    }

    public String getPhotoUrlGame() {
        return photoUrlGame;
    }

    public void setPhotoUrlGame(String photoUrlGame) {
        this.photoUrlGame = photoUrlGame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
