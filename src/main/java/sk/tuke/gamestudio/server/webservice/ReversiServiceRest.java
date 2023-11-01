package sk.tuke.gamestudio.server.webservice;

// /api/reversi/field

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.game.reversi.core.*;

@RestController
@RequestMapping("api/reversi/field")
public class ReversiServiceRest {
    private Field field = new Field(Difficulty.HARD);

    @GetMapping()
    public Field getField(@RequestParam String loggedPlayer) {
        field = new Field(Difficulty.HARD);
        field.getPlayer1().setName(loggedPlayer);
        return field;
    }

    @GetMapping("/newGame/easy")
    public Field newGameEasy(@RequestParam String playerName) {
        field = new Field(Difficulty.EASY);
        field.getPlayer1().setName(playerName);
        return field;
    }

    @GetMapping("/newGame/hard")
    public Field newGameHard(@RequestParam String playerName) {
        field = new Field(Difficulty.HARD);
        field.getPlayer1().setName(playerName);
        return field;
    }

    @GetMapping("/newGame/pvp")
    public Field newGameHard(@RequestParam String playerName1, @RequestParam String playerName2) {
        field = new Field();
        field.getPlayer1().setName(playerName1);
        field.getPlayer2().setName(playerName2);
        field.setGameMode(GameMode.PLAYER_VS_PLAYER);
        return field;
    }



    @GetMapping("/placeStone")
    public Field placeStone(@RequestParam int row, @RequestParam int col) {
        try {
            Player player = field.getPlayerOnTurn();
            if(!field.isMovePossible()) {
                field.changeTurn();
            }

            if(!(player instanceof Computer))
                field.addStoneToField(player, row, col);

            return field;
        } catch (Exception e) {
            return field;
        }
    }

    @GetMapping("/computerMove")
    public Field computerMove() {
        Player player = field.getPlayerOnTurn();
        if(player instanceof Computer) {
            try {
                if(field.isMovePossible())
                    ((Computer) player).makeTurn();
                else field.changeTurn();

            } catch (Exception e) {
                return field;
            }
        }
        return field;
    }
}
