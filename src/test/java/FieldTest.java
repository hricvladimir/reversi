import org.junit.jupiter.api.Test;
import java.util.Random;
import sk.tuke.kpi.reversi.core.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FieldTest {
    private final Random randomGenerator = new Random();

    public FieldTest() {
        Computer player1 = new Computer("testplayer1", 'B');
        Computer player2 = new Computer("testplayer2", 'R');

    }

    @Test
    public void whenBothPlayersCantMoveGameShouldFinish() {

        int size = 6;

        Field field = new Field(GameMode.PLAYER_VS_AI, size);
        field.changeTurn();
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                if(row == size -1 && col == size -1) {
                    break;
                }
                if(field.getTiles()[row][col].getTileState() != TileState.OCCUPIED) {
                    field.getTiles()[row][col].occupyTile(new Stone(field.getPlayerOnTurn()));
                }
            }
        }
        field.changeTurn();
        try {
            field.addStoneToField(field.getPlayerOnTurn(), size -1, size -1);
        } catch (Exception e) {
            if(e instanceof Field.NoPossibleMovesException) {
                System.out.println("No more moves are possible. Both players skipped a move.");
            }
            else System.out.println("Other error during test.");
        }
        assertSame(GameState.FINISHED, field.getState());
    }

    @Test
    public void placingStoneColumnCheck() {
        Field field = new Field(GameMode.PLAYER_VS_PLAYER);
        field.getTiles()[3][3].occupyTile(new Stone(field.getPlayerOnTurn()));
        field.getTiles()[3][4].occupyTile(new Stone(field.getPlayerOnTurn()));
        field.getTiles()[3][5].occupyTile(new Stone(field.getPlayerOnTurn()));
        field.getTiles()[3][6].occupyTile(new Stone(field.getPlayerOnTurn()));
        field.changeTurn();
        field.getTiles()[3][2].occupyTile(new Stone(field.getPlayerOnTurn()));
        Player controlPlayer = field.getPlayerOnTurn();
        try {
            field.addStoneToField(field.getPlayerOnTurn(), 3, 7);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        for(int i = 2; i <= 7; i++) {
            if(field.getTiles()[3][i].getStone().getPlayer() != controlPlayer) {
                assert(false);
                break;
            }
        }
        assert(true);
    }
    @Test
    public void placingStonesShouldDeceaseFreeTiles() {
        Field field = new Field(GameMode.PLAYER_VS_PLAYER, 8);
        int freeTiles = field.getFreeTiles();

        for(int i = 0; i < 10; i++) {
            int size = field.getSize();
            int row;
            int col;
            do {
                row = (int) (Math.random() *size);
                col = (int) (Math.random() *size);
            } while(!field.isMovePossible(row, col));
            try {
                freeTiles--;
                field.addStoneToField(field.getPlayerOnTurn(), row, col);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                freeTiles++;
            }
        }
        assertSame(freeTiles, field.getFreeTiles());
    }

    @Test
    public void placingStonesInWrongPositionShouldNotDeceaseFreeTiles() {
        Field field = new Field(GameMode.PLAYER_VS_PLAYER, 8);
        int freeTiles = field.getFreeTiles();
        try {
            for(int i = 0; i < 5; i++) {
                field.addStoneToField(field.getPlayerOnTurn(), 3, 5);
                field.addStoneToField(field.getPlayerOnTurn(), 5, 2);
            }
        } catch (Exception e) {
            //
        }

        assertSame(freeTiles, field.getFreeTiles());
    }

    @Test
    public void stoneShouldNotBeAddedInWrongPosition() {
        Field field = new Field(GameMode.PLAYER_VS_PLAYER, 8);
        int size = field.getSize();
        for(int row = 0; row < 2; row++) {
            for(int col = 0; col < size; col++) {
                try {
                    field.addStoneToField(field.getPlayerOnTurn(), row, col);
                    if(field.getTiles()[row][col].getTileState() != TileState.OCCUPIED) assert(false);
                } catch (Exception ignored) {

                }
            }
        }
        try {
            field.addStoneToField(field.getPlayerOnTurn(), 2, 2);
            field.addStoneToField(field.getPlayerOnTurn(), 2, 4);
            field.addStoneToField(field.getPlayerOnTurn(), 2, 5);
            field.addStoneToField(field.getPlayerOnTurn(), 3, 4);
            field.addStoneToField(field.getPlayerOnTurn(), 4, 2);
            field.addStoneToField(field.getPlayerOnTurn(), 5, 5);
            field.addStoneToField(field.getPlayerOnTurn(), 5, 3);
            field.addStoneToField(field.getPlayerOnTurn(), 5, 2);

            for(int col = 2; col <= 5; col++) {
                if(field.getTiles()[2][col].getTileState() == TileState.OCCUPIED) assert(false);
            }
            for(int col = 2; col <= 5; col++) {
                if(field.getTiles()[5][col].getTileState() == TileState.OCCUPIED) assert(false);
            }
            for(int row = 2; row <= 5; row++) {
                if(field.getTiles()[row][2].getTileState() == TileState.OCCUPIED) assert(false);
            }
            for(int row = 2; row <= 5; row++) {
                if(field.getTiles()[row][5].getTileState() == TileState.OCCUPIED) assert(false);
            }

        } catch (Exception ignored) {

        }
        assert(true);
    }

    @Test
    public void fieldWithIncorrectSizeShouldNotBeCreated() {
        int exceptions = 0;
        try{
            Field field1 = new Field(GameMode.PLAYER_VS_PLAYER,-10);
        } catch(IllegalArgumentException e) {
            exceptions++;
        }
        try{
            Field field2 = new Field(GameMode.PLAYER_VS_PLAYER,-3);
        } catch(IllegalArgumentException e) {
            exceptions++;
        }
        try{
            Field field3 = new Field(GameMode.PLAYER_VS_PLAYER,2);
        } catch(IllegalArgumentException e) {
            exceptions++;
        }
        try{
            Field field4 = new Field(GameMode.PLAYER_VS_PLAYER,7);
        } catch(IllegalArgumentException e) {
            exceptions++;
        }

        assertSame(4, exceptions);
    }

    @Test
    public void ifFinishedStoneShouldNotBeAdded() {
        Field field = new Field(GameMode.PLAYER_VS_PLAYER);
        field.setState(GameState.FINISHED);
        try {
            field.addStoneToField(field.getPlayerOnTurn(), 4, 5);
        } catch (Exception e) {
            assert(e instanceof Field.IllegalGameStateException);
        }
    }
}
