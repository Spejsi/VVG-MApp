package com.spacedancer.globalandromathick.components;

public class Player {

    String name;
    Integer score;
    Integer position;

    public Player() {
    }

    public Player(String name, Integer score) {
        this.name = name;
        this.score = score;
        this.position = 0;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
