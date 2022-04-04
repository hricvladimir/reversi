package service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.score.ScoreService;
import sk.tuke.gamestudio.service.score.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void addScoreTest() {
        scoreService.reset(); // resets scores

        // creating score data
        Score score1 = new Score("vlado", "reversi", 25, new Date());
        Score score2 = new Score("peter", "reversi", 20, new Date());
        Score score3 = new Score("jano", "reversi", 16, new Date());
        Score score4 = new Score("jozef", "reversi", 13, new Date());

        // adding data
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        scoreService.addScore(score4);

        List<Score> list = scoreService.getTopScores("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals("vlado", list.get(0).getPlayer());
        assertEquals("peter", list.get(1).getPlayer());
        assertEquals("jano", list.get(2).getPlayer());
        assertEquals("jozef", list.get(3).getPlayer());
    }

    @Test
    public void getTopScoresTest() {
        scoreService.reset(); // resets scores

        // creating score data
        Score score1 = new Score("vlado", "reversi", 25, new Date());
        Score score2 = new Score("peter", "reversi", 20, new Date());
        Score score3 = new Score("jano", "reversi", 16, new Date());
        Score score4 = new Score("jozef", "reversi", 13, new Date());

        // adding data
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        scoreService.addScore(score4);

        List<Score> list = scoreService.getTopScores("reversi");

        // asserts
        assert(list.size() == 4);
        assertEquals(25, list.get(0).getPoints());
        assertEquals(20, list.get(1).getPoints());
        assertEquals(16, list.get(2).getPoints());
        assertEquals(13, list.get(3).getPoints());
    }

    public void resetScoresTest() {
        scoreService.reset(); // resets scores

        // creating score data
        Score score1 = new Score("vlado", "reversi", 25, new Date());
        Score score2 = new Score("peter", "reversi", 20, new Date());
        Score score3 = new Score("jano", "reversi", 16, new Date());
        Score score4 = new Score("jozef", "reversi", 13, new Date());

        scoreService.reset(); // resets scores

        assertEquals(0, scoreService.getTopScores("reversi").size());
    }
}
