package sk.tuke.gamestudio.service.score;

import sk.tuke.gamestudio.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "sardinki";
    public static final String DELETE_STATEMENT = "DELETE FROM score";
    public static final String SELECT_STATEMENT = "SELECT player, game, points, playedOn FROM score WHERE game = ? order by points desc LIMIT 10";
    public static final String INSERT_STATEMENT = "INSERT INTO score (player, game, points, playedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void addScore(Score score) throws ScoreException{
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(INSERT_STATEMENT)
        )
        {
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException{
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(SELECT_STATEMENT)
        )
        {
            statement.setString(1, game);
            try(var rs = statement.executeQuery()) {
                var scores = new ArrayList<Score>();
                while (rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }

    @Override
    public void reset() throws ScoreException{
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.createStatement()
        )
        {
            var rs =  statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }
}
