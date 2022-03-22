package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.reversi.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.GameMode;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(GameMode.PLAYER_VS_AI, 8);
        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();
    }
}
