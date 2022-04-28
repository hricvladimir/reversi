package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.gamestudio.game.reversi.core.Color;
import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.Tile;
import sk.tuke.gamestudio.game.reversi.core.TileState;

import java.util.Date;

@Controller
@RequestMapping("/reversi")
public class ReversiController {

    Field field = new Field();

    @RequestMapping
    public String reversi(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer col) {
        if(row != null && col != null) {
            try {
                field.addStoneToField(field.getPlayerOnTurn(), row, col);
            } catch (Exception ignored) {

            }
        }
        return "reversi";
    }

    public String getHtmlField() {

        var sb = new StringBuilder();
        int size = field.getSize();

        sb.append("<div class=\"container\">");
        for(int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                sb.append("<a class=\"square\"href='/reversi?row=").append(row).append("&col=").append(col).append("'>\n");
            }
        }
        sb.append("</div>");
        return sb.toString();

//        var sb = new StringBuilder();
//        sb.append("<table>\n");
//
//        int size = field.getSize();
//
//        for(int row = 0; row < size; row++) {
//
//            sb.append("<tr>\n");
//            for (int col = 0; col < size; col++) {
//                var tile = field.getTile(row, col);
//                sb.append("<td class =\"squarecontainer\">\n");
//                sb.append("<a href='/reversi?row=").append(row).append("&col=").append(col).append("'>\n");
//                if(tile.getTileState() == TileState.OCCUPIED) {
//                    if(tile.getStone().getPlayer().getColor() == Color.BLACK)
//                        sb.append("<div class=\"square\" style=\"background-color:black;\"></div>");
//                    else sb.append("<div class=\"square\" style=\"background-color:white;\"></div>");
//                }
//                else sb.append("<div class=\"square\"></div>");
//                sb.append("</a>\n");
//                sb.append("</td>\n");
//            }
//            sb.append("</tr>\n");
//        }
//
//
//        sb.append("</table>\n");
//        return sb.toString();
    }
}


