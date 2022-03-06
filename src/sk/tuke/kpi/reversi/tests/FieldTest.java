package sk.tuke.kpi.reversi.tests;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.reversi.core.Computer;
import sk.tuke.kpi.reversi.core.Field;
import sk.tuke.kpi.reversi.core.Player;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    private Random randomGenerator = new Random();
    private final Field field;
    private int size;

    public FieldTest() {
        Computer player1 = new Computer("testplayer1", 'R');
        Computer player2 = new Computer("testplayer2", 'B');
        size = randomGenerator.nextInt(10) +5;
        System.out.println(size);
        field = new Field(size, player1, player2);
        //player1.setField(field);
        //player2.setField(field);
    }

    @Test
    public void addingStoneShouldChangePlayer() {
        Computer playerOnTurn = (Computer)field.getPlayerOnTurn();
        int freeTilesInField = field.getFreeTiles();
        System.out.println("Free tiles: " + field.getFreeTiles());
        playerOnTurn.makeTurn();
        System.out.println("Free tiles: " + field.getFreeTiles());
        if(freeTilesInField > field.getFreeTiles()) {
            assertNotSame(playerOnTurn, field.getPlayerOnTurn(), "Player was not changed after adding stone.");
        }
        else assertSame(playerOnTurn, field.getPlayerOnTurn(), "Stone was not added to the field, but the player changed.");
    }
}
