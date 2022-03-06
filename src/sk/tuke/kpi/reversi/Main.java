package sk.tuke.kpi.reversi;

import sk.tuke.kpi.reversi.consoleui.ConsoleUI;
import sk.tuke.kpi.reversi.core.Computer;
import sk.tuke.kpi.reversi.core.Field;
import sk.tuke.kpi.reversi.core.Player;
import sk.tuke.kpi.reversi.tests.FieldTest;

public class Main {
    public static void main(String[] args) {

        FieldTest fieldTest = new FieldTest();
        fieldTest.addingStoneShouldChangePlayer();

        Player player1 = new Player("Player1", 'B');
        Player player3 = new Player("computah", 'R');
        Computer player2 = new Computer("Player2", 'R');

        Field field = new Field(5, player1, player2);
        //player2.setField(field);

        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();


    }
}
