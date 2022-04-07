package service;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.comment.CommentService;
import sk.tuke.gamestudio.service.comment.CommentServiceJDBC;
import sk.tuke.gamestudio.service.comment.CommentServiceJPA;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CommentServiceTest {

    private CommentService service = new CommentServiceJDBC();

    @Test
    public void addCommentTest() {
        service.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        // adding data
        service.addComment(comment1);
        service.addComment(comment2);
        service.addComment(comment3);
        service.addComment(comment4);

        List<Comment> list = service.getComments("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals("dobra hra", list.get(0).getComment());
        assertEquals("zla hra", list.get(1).getComment());
        assertEquals("ok hra", list.get(2).getComment());
        assertEquals("vpohode hra", list.get(3).getComment());
    }

    @Test
    public void getCommentsTest() {
        service.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        // adding data
        service.addComment(comment1);
        service.addComment(comment2);
        service.addComment(comment3);
        service.addComment(comment4);

        List<Comment> list = service.getComments("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals("dobra hra", list.get(0).getComment());
        assertEquals("zla hra", list.get(1).getComment());
        assertEquals("ok hra", list.get(2).getComment());
        assertEquals("vpohode hra", list.get(3).getComment());
    }

    @Test
    public void resetScoresTest() {
        service.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        service.reset(); // resets scores

        assertEquals(0, service.getComments("reversi").size());
    }
}
