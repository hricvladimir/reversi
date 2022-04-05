package sk.tuke.gamestudio.service.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.repository.ScoreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScoreServiceJPA implements ScoreService{
    @Autowired
    ScoreRepository repo;

    @Override
    public void addScore(Score score) {
        repo.save(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return repo.getScoresByGame(game);
    }

    @Override
    public void reset() {
        repo.deleteAll();
    }
}
