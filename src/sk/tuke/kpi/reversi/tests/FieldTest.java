package sk.tuke.kpi.reversi.tests;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.reversi.core.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FieldTest {
    private final Field field;
    private int size;

    public FieldTest() {
        Computer player1 = new Computer("testplayer1", 'B');
        Computer player2 = new Computer("testplayer2", 'R');
        Random randomGenerator = new Random();
        size = randomGenerator.nextInt(10) +5;
        field = new Field(GameMode.PLAYER_VS_AI, size);
    }

    @Test
    public void whenBothPlayersCantMoveGameShouldFinish() {
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(row == size-1 && col == size-1) {
                    break;
                }
                if(field.getTiles()[row][col].getTileState() != TileState.OCCUPIED) {
                    field.getTiles()[row][col].occupyTile(new Stone(field.getPlayerOnTurn()));
                }
            }
        }
        field.changeTurn();
        try {
            field.addStoneToField(field.getPlayerOnTurn(), size-1, size-1);
        } catch (Exception e) {
            if(e instanceof Field.NoPossibleMovesException) {
                System.out.println("No more moves are possible. Both players skipped a move.");
            }
            else System.out.println("Other error during test.");
        }
        assertSame(GameState.FINISHED, field.getState());
    }
}
