package sk.tuke.gamestudio.game.reversi.core;

public class Computer extends Player{

    private Field field;


    private final int[][] priority = {
            {99, -8, 8, 6, 6, 8, -8,99},
            {-8,-24,-4,-3,-3,-4,-24,-8},
            { 8, -4, 7, 4, 4, 7, -4, 8},
            { 6, -3, 4, 0, 0, 4, -3, 6},
            { 6, -3, 4, 0, 0, 4, -3, 6},
            { 8, -4, 7, 4, 4, 7, -4, 8},
            {-8,-24,-4,-3,-3,-4,-24,-8},
            {99, -8, 8, 6, 6, 8, -8,99},
    };

    public Computer(String name, Color color) {
        super(name, color);
    }
    public Computer(Color color) {
        super(color);
    }

    public void makeTurn() throws Exception {
        if(field.getDifficulty() == Difficulty.EASY)
            randomTurn();
        else advancedTurn();
    }

    private void randomTurn() throws Exception {
        if(!field.isMovePossible())
            throw new Exception("Computer was unable to make a turn!");

        int size = field.getSize();

        int row;
        int col;

        do {
            row = (int) (Math.random() *size);
            col = (int) (Math.random() *size);
        } while(!field.isMovePossible(row, col));
        field.addStoneToField(this, row, col);
    }

    private void advancedTurn() throws Exception {

        if(!field.isMovePossible())
            throw new Exception("Computer was unable to make a turn!");

        int maxRow = -1;
        int maxCol = -1;
        int maxMarked = 0;
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(field.getTiles()[row][col].getTileState() != TileState.FREE) continue;
                field.markStones(row, col);
                int counted = countMarkedStones();
                if(counted > maxMarked) {
                    maxRow = row;
                    maxCol = col;
                    maxMarked = counted;
                    continue;
                }
                if(counted == maxMarked && field.getSize() == 8) { // WARNING - works only with size 8!
                    if(!checkPriority(row, col, maxRow, maxCol)) continue;
                    maxRow = row;
                    maxCol = col;
                }
            }
        }
        field.addStoneToField(this, maxRow, maxCol);
    }

    private boolean checkPriority(int row, int col, int maxRow, int maxCol) {
        // returns true if new position has better priority
        // if priority is worse or the same, returns false
        if(maxRow == -1 || maxCol == -1) return true;
        return priority[row][col] > priority[maxRow][maxCol];

    }

    private int countMarkedStones() {
        Tile[][] tiles = field.getTiles();
        int markedStones = 0;
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(tiles[row][col].getTileState() != TileState.FREE)
                    if(tiles[row][col].getStone().shouldChange()) {
                        markedStones++;
                        tiles[row][col].getStone().setShouldChange(false);
                    }
            }
        }
        return markedStones;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
