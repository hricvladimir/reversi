package main.java.sk.tuke.kpi.reversi.core;

public class Player {
    private String name;
    private char color;
    private int score;

    public Player(String name, char color) {
        this.name = name;
        this.color = color;
    }
    public Player(char color) {
        this.name = "player";
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        this.score += 1;
    }

    public void setName(String name) {
        this.name = name;
    }
}
