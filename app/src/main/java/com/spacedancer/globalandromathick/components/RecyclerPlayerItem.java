package com.spacedancer.globalandromathick.components;

public class RecyclerPlayerItem {
    private String playerRanking;
    private String playerName;
    private String playerScore;

    public RecyclerPlayerItem(Player player) {
        this.playerRanking = player.getPosition()+".";
        this.playerName = player.getName();
        this.playerScore = String.valueOf(player.getScore());
    }

    public String getPlayerRanking() {
        return playerRanking;
    }

    public void setPlayerRanking(String playerRanking) {
        this.playerRanking = playerRanking;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(String playerScore) {
        this.playerScore = playerScore;
    }
}
