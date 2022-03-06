package sk.tuke.kpi.reversi.consoleui;

import sk.tuke.kpi.reversi.core.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);
    private Computer computer = null;

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        // determine if the game is PvP or PvAI
        if(field.getPlayer1() instanceof Computer) computer = (Computer) field.getPlayer1();
        else if(field.getPlayer2() instanceof Computer) computer = (Computer) field.getPlayer2();

        // game loop
        do {
            printGame(); // prints game stats and field
            if (isFinished()) {
                field.setState(GameState.FINISHED);
                //break;
            }
            if(field.getPlayerOnTurn() instanceof Computer) {
                computer.makeTurn();
            } else processInput();

        } while(field.getState() == GameState.PLAYING);

        if(field.getState() == GameState.FINISHED) {
            printField();
            if(field.getPlayer1().getScore() > field.getPlayer2().getScore()) {
                System.out.println("Player " + field.getPlayer1().getName() + " won!");
            }
            else System.out.println("Player " + field.getPlayer2().getName() + " won!");
        }
    }

    private boolean isFinished() {
        if(!field.isMovePossible()) field.changeTurn();
        return !field.isMovePossible();
    }

    public void printGame() {
        //for (int i = 0; i < 50; ++i) System.out.println();
        System.out.println("\nType two numbers [row] [col] and press Enter");
        printGameStats();
        printField();
    }

    private void printField() {
        System.out.print(" ");
        for(int i = 1; i <= field.getSize(); i++)
            System.out.print(ANSI_RESET + " " + i);
        System.out.println();

        int index = 0;
        for(Tile[] tileRow : field.getTiles()) {
            System.out.print(ANSI_RESET + ++index + " ");
            for(Tile tile : tileRow) {
                if(tile.getTileState() == TileState.OCCUPIED) {
                    if(tile.getStone().getPlayer().getColor() == 'R') System.out.print(ANSI_RED + "R ");
                    else System.out.print(ANSI_BLUE + "B ");
                } else System.out.print(ANSI_RESET + "- ");
            }
            System.out.println();
        }
    }

    private void printGameStats() {
        Player playerOnTurn = field.getPlayerOnTurn();
        if(playerOnTurn.getColor() == 'B')
            System.out.println("Player " + playerOnTurn.getName() + " with color " + ANSI_BLUE + "B" + ANSI_RESET + " is on turn.");
        else System.out.println("Player " + playerOnTurn.getName() + " with color " + ANSI_RED + "R" + ANSI_RESET + " is on turn.");
        System.out.println(field.getPlayer1().getName() + "'s score: " + field.getPlayer1().getScore());
        System.out.println(field.getPlayer2().getName() + "'s score: " + field.getPlayer2().getScore());
    }

    public void processInput() {
        if(field.getPlayerOnTurn() instanceof Computer) return;
        int row, col;
        try {
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
        } catch(InputMismatchException exception) {

            scanner.next();
            scanner.next();
            System.out.println("Wrong input. Try again!");
            return;
        }


        if(isPositionOutOfBounds(row, col)) {
            System.out.println("Your input is out of bounds. Try again!");
            return;
        }

        if(!field.addStoneToField(field.getPlayerOnTurn(), row,col)) {
            System.out.println("You can not place a stone in this position. Try again!");
        }
    }

    private boolean isPositionOutOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= field.getSize() || col >= field.getSize(); // determines if desired position is out of bounds
    }
}
