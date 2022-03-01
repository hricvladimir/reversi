package core;

public class Player {
    private String name;
    private char color;
    private int score;

    public Player(String name, char color) {
        if(name == null) return;
        if(color != 'b' && color != 'w') return;

        this.name = name;
        this.color = color;
    }

    public char getColor() {
        return color;
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
}
