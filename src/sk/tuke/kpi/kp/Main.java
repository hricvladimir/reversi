package sk.tuke.kpi.kp;

import sk.tuke.kpi.kp.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.core.Computer;
import sk.tuke.kpi.kp.core.Field;
import sk.tuke.kpi.kp.core.Player;

public class Main {
    public static void main(String[] args) {

        Player player1 = new Player("Player1", 'b');
        Player player3 = new Player("computah", 'w');
        Computer player2 = new Computer("Player2", 'w');

        Field field = new Field(5, player1, player2);
        player2.setField(field);

        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();
    }
}
