package com.spacedancer.globalandromathick.components;

public class DatabasePlayer {

    String name;
    Integer score;

    public DatabasePlayer() {
    }

    public DatabasePlayer(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
