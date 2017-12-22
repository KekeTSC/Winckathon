package fr.wildcodeschool.kelian.winstate.Models;


public class StatsModel {
    private String lastResult; //3possibilité
    private boolean joker;
    private String photoUrlGame;
    private int score;
    private boolean isTouching;

    public StatsModel() {
    }

    public StatsModel(String lastResult, boolean joker, String photoUrlGame, int score, boolean isTouching) {
        this.lastResult = lastResult;
        this.joker = joker;
        this.photoUrlGame = photoUrlGame;
        this.score = score;
        this.isTouching = isTouching;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
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

    public boolean isTouching() {
        return isTouching;
    }

    public void setTouching(boolean touching) {
        isTouching = touching;
    }
}
