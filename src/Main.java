import consoleui.ConsoleUI;
import core.Computer;
import core.Field;
import core.Player;

public class Main {
    public static void main(String[] args) {

        Player player1 = new Player("jozef", 'b');
        Player player3 = new Player("kamaratxd", 'w');
        Computer player2 = new Computer("pidar", 'w');

        Field field = new Field(5, player1, player2);
        player2.setField(field);

        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();
    }
}
