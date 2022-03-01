package core;

public class Computer extends Player{

    private Field field;

    public Computer(String name, char color) {
        super(name, color);
    }

    public void makeTurn() {
        if(!field.isMovePossible()) return;
        int size = field.getSize();

        int row;
        int col;

        do {
            row = (int) (Math.random() *size);
            col = (int) (Math.random() *size);
        } while(!field.addStoneToField(this, row, col) && field.getState() != GameState.FINISHED);
    }

    public void advancedTurn() {

    }

    public void setField(Field field) {
        this.field = field;
    }
}
