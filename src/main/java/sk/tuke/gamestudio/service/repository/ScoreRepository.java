package sk.tuke.gamestudio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {
    @Query("select s from Score s where s.game = :game order by s.playedOn")
    List<Score> getScoresByGame(String game);
}
