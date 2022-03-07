package sk.tuke.kpi.reversi.tests;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.reversi.core.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    private final Field field;
    private Computer player1;
    private Computer player2;
    private int size;

    public FieldTest() {
        Computer player1 = new Computer("testplayer1", 'R');
        Computer player2 = new Computer("testplayer2", 'B');
        this.player1 = player1;
        this.player2 = player2;
        Random randomGenerator = new Random();
        size = randomGenerator.nextInt(10) +5;
        field = new Field(size, player1, player2);
    }

    @Test
    public void addingStoneShouldChangePlayer() {
        Computer playerOnTurn = (Computer)field.getPlayerOnTurn();
        int freeTilesInField = field.getFreeTiles();
        playerOnTurn.makeTurn();
        if(freeTilesInField > field.getFreeTiles()) {
            assertNotSame(playerOnTurn, field.getPlayerOnTurn(), "Player was not changed after adding stone.");
        }
        else assertSame(playerOnTurn, field.getPlayerOnTurn(), "Stone was not added to the field, but the player changed.");

    }

    @Test
    public void placingStonesShouldDecreaseEmptyTiles() {
        int freeTiles = field.getFreeTiles();


        for(int i = 0; i < 10; i++) {
            if(field.getPlayerOnTurn() instanceof Computer) ((Computer) field.getPlayerOnTurn()).makeTurn();
        }

        assertNotEquals(field.getFreeTiles(), freeTiles, "Free tiles should decrease after adding stones.");
    }

    @Test
    public void sizeShouldCorrespondToRandomlyGeneratedSize() {
        assertEquals(field.getSize(), size, "Field size should be the same as randomly generated size. " +
                "randomly generated size == " + size + "; actual size == " + field.getSize());
    }

    @Test
    public void addingStoneShouldBeInCorrectPosition() {

        Field field = new Field(player1, player2);

        int row;
        int col;
        do {
            row = (int) (Math.random() * field.getSize());
            col = (int) (Math.random() * field.getSize());
        } while(!field.addStoneToField(field.getPlayerOnTurn(), row, col) && field.getState() != GameState.FINISHED);

        assertNotEquals(field.getTiles()[row][col].getTileState(), TileState.FREE);

    }


}
