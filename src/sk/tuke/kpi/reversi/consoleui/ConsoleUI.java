package sk.tuke.kpi.reversi.consoleui;

import sk.tuke.kpi.reversi.core.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {

        // getting player name(s)
        switch (field.getGameMode()) {
            case PLAYER_VS_PLAYER -> {
                System.out.println("Player 1, please type your name: ");
                field.getPlayer1().setName(scanner.next());
                System.out.println("Player 2, please type your name: ");
                field.getPlayer1().setName(scanner.next());
            }
            case PLAYER_VS_AI -> {
                System.out.println("Please type your name: ");
                field.getPlayer1().setName(scanner.next());
            }
        }

        // main game loop
        do {
            System.out.println(field.getFreeTiles());
            printGame();
            processInput();
        } while(field.getState() == GameState.PLAYING);
        printGame();

        // finished situations
        if(field.getState() == GameState.FINISHED) {
            printField();
            if (field.getPlayer1().getScore() > field.getPlayer2().getScore()) {
                System.out.println("Player " + field.getPlayer1().getName() + " won!");
            } else if (field.getPlayer1().getScore() < field.getPlayer2().getScore())
                System.out.println("Player " + field.getPlayer2().getName() + " won!");
            else System.out.println("Tie! Nobody won!");
        }
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
                    if(tile.getStone().getPlayer().getColor() == 'R') System.out.print(ANSI_RED + "R " + ANSI_RESET);
                    else System.out.print(ANSI_BLUE + "B " + ANSI_RESET);
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
        try {
            field.addStoneToField(field.getPlayerOnTurn(), row, col);
        } catch (Exception e) {
            if(e instanceof Field.NoPossibleMovesException) {
                System.out.println("No more moves are possible. Both players skipped a move.");
                return;
            }
            System.out.println(e.getMessage());
            System.out.println("Try again!");
        }
    }
}
