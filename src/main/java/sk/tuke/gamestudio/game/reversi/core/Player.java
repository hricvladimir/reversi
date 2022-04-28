package sk.tuke.gamestudio.game.reversi.core;

public class Player {
    private String name;
    private Color color;
    private int score;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    public Player(Color color) {
        this.name = "player";
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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
