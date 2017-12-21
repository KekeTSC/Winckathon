package fr.wildcodeschool.kelian.winstate.Models;

public class GameModel {
    private String uidPlayer1;
    private String uidPlayer2;
    private StatsModel statsModelP1;
    private StatsModel statsModelP2;
    private String winnerUid;

    public GameModel() {
    }

    public GameModel(String uidPlayer1, String uidPlayer2, StatsModel statsModelP1, StatsModel statsModelP2, String winnerUid) {
        this.uidPlayer1 = uidPlayer1;
        this.uidPlayer2 = uidPlayer2;
        this.statsModelP1 = statsModelP1;
        this.statsModelP2 = statsModelP2;
        this.winnerUid = winnerUid;
    }

    public String getUidPlayer1() {
        return uidPlayer1;
    }

    public void setUidPlayer1(String uidPlayer1) {
        this.uidPlayer1 = uidPlayer1;
    }

    public String getUidPlayer2() {
        return uidPlayer2;
    }

    public void setUidPlayer2(String uidPlayer2) {
        this.uidPlayer2 = uidPlayer2;
    }

    public StatsModel getStatsModelP1() {
        return statsModelP1;
    }

    public void setStatsModelP1(StatsModel statsModelP1) {
        this.statsModelP1 = statsModelP1;
    }

    public StatsModel getStatsModelP2() {
        return statsModelP2;
    }

    public void setStatsModelP2(StatsModel statsModelP2) {
        this.statsModelP2 = statsModelP2;
    }

    public String getWinnerUid() {
        return winnerUid;
    }

    public void setWinnerUid(String winnerUid) {
        this.winnerUid = winnerUid;
    }
}
