package sk.tuke.gamestudio.game.reversi.core;

public class Tile {

    private final int row;
    private final int col;
    private TileState tileState;
    private Stone stone;

    public Tile(int row, int col) {
        this.col = col;
        this.row = row;
        this.tileState = TileState.FREE;
    }

    public void occupyTile(Stone stone) {
        if(tileState == TileState.OCCUPIED) return;
        this.stone = stone;
        this.tileState = TileState.OCCUPIED;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Stone getStone() {
        return stone;
    }

    public TileState getTileState() {
        return tileState;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }
}
