package fr.wildcodeschool.kelian.winstate.Models;


public class StatsModel {
    private String result = ""; //3possibilité
    private int score;
    private int clickCounter;
    private double deadTaupeClick;
    private boolean isScoreSet;

    public StatsModel() {
    }

    public StatsModel(String result, int score, int clickCounter, double deadTaupeClick, boolean isScoreSet) {
        this.result = result;
        this.score = score;
        this.clickCounter = clickCounter;
        this.deadTaupeClick = deadTaupeClick;
        this.isScoreSet = isScoreSet;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    public double getDeadTaupeClick() {
        return deadTaupeClick;
    }

    public void setDeadTaupeClick(double deadTaupeClick) {
        this.deadTaupeClick = deadTaupeClick;
    }

    public boolean isScoreSet() {
        return isScoreSet;
    }

    public void setScoreSet(boolean scoreSet) {
        isScoreSet = scoreSet;
    }
}


