package sk.tuke.gamestudio.service.comment;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return (List<Comment>) entityManager.createQuery("select c from Comment c where c.game = :game order by c.commentedOn")
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
