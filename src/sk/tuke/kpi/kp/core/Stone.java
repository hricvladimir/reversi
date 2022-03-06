package sk.tuke.kpi.kp.core;

public class Stone {
    private Player player;
    private boolean shouldChange;

    public Stone(Player player) {
        this.player = player;
        shouldChange = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean shouldChange() {
        return shouldChange;
    }

    public void setShouldChange(boolean shouldChange) {
        this.shouldChange = shouldChange;
    }
}
