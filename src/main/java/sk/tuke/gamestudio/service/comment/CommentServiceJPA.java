package sk.tuke.gamestudio.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.repository.CommentRepository;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @Autowired
    CommentRepository repo;

    @Override
    public void addComment(Comment comment) {
        repo.save(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return repo.getCommentsByGameOrderByCommentedOnDesc(game);
    }

    @Override
    public void reset() {
        repo.deleteAll();
    }
}
