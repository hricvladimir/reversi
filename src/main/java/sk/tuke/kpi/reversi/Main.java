package main.java.sk.tuke.kpi.reversi;

import main.java.sk.tuke.kpi.reversi.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.reversi.core.Field;
import main.java.sk.tuke.kpi.reversi.core.GameMode;

public class Main {
    public static void main(String[] args) {

        Field field = new Field(GameMode.PLAYER_VS_AI, 8);
        //player2.setField(field);

        ConsoleUI consoleUI = new ConsoleUI(field);
        consoleUI.play();
    }
}
