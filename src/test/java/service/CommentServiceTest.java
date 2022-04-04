package service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.comment.CommentService;
import sk.tuke.gamestudio.service.comment.CommentServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void addCommentTest() {
        commentService.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        // adding data
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);
        commentService.addComment(comment4);

        List<Comment> list = commentService.getComments("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals("dobra hra", list.get(0).getComment());
        assertEquals("zla hra", list.get(1).getComment());
        assertEquals("ok hra", list.get(2).getComment());
        assertEquals("vpohode hra", list.get(3).getComment());
    }

    @Test
    public void getCommentsTest() {
        commentService.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        // adding data
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);
        commentService.addComment(comment4);

        List<Comment> list = commentService.getComments("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals("dobra hra", list.get(0).getComment());
        assertEquals("zla hra", list.get(1).getComment());
        assertEquals("ok hra", list.get(2).getComment());
        assertEquals("vpohode hra", list.get(3).getComment());
    }

    @Test
    public void resetScoresTest() {
        commentService.reset(); // resets scores

        // creating comments
        Comment comment1 = new Comment("vlado", "reversi", "dobra hra", new Date());
        Comment comment2 = new Comment("peter", "reversi", "zla hra", new Date());
        Comment comment3 = new Comment("jano", "reversi", "ok hra", new Date());
        Comment comment4 = new Comment("jozo", "reversi", "vpohode hra", new Date());

        commentService.reset(); // resets scores

        assertEquals(0, commentService.getComments("reversi").size());
    }
}
