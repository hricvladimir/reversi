package sk.tuke.kpi.reversi.core;

public class Computer extends Player{

    private Field field;

    public Computer(String name, char color) {
        super(name, color);
    }
    public Computer(char color) {
        super(color);
    }

    public void makeTurn() throws Exception {
        if(!field.isMovePossible()) {
            throw new Exception("Computer was unable to make a turn!");
        }
        int size = field.getSize();

        int row;
        int col;

        do {
            row = (int) (Math.random() *size);
            col = (int) (Math.random() *size);
        } while(!field.isMovePossible(row, col));
        field.addStoneToField(this, row, col);
    }

    public void advancedTurn() {

    }

    public void setField(Field field) {
        this.field = field;
    }
}
