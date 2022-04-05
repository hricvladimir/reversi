package sk.tuke.gamestudio.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.repository.CommentRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CommentServiceJPA implements CommentService{

    @Autowired
    CommentRepository repo;

    @Override
    public void addComment(Comment comment) {
        repo.save(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return repo.getComments(game);
    }

    @Override
    public void reset() {
        repo.deleteAll();
    }
}
