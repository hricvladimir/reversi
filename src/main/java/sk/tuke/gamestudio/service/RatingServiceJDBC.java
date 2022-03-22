package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class RatingServiceJDBC implements RatingService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "sardinki";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String SELECT_RATING_STATEMENT = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    public static final String SELECT_AVERAGE_RATING_STATEMENT = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?) ON CONFLICT (game, player) DO UPDATE SET rating = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(INSERT_STATEMENT);
        )
        {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.setInt(5, rating.getRating());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Could not set rating!", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(SELECT_AVERAGE_RATING_STATEMENT);
        )
        {
            statement.setString(1, game);

            try(var rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt("avg");
            }
        } catch (SQLException e) {
            throw new RatingException("Could not get average rating!", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(SELECT_RATING_STATEMENT);
        )
        {
            statement.setString(1, game);
            statement.setString(2, player);
            try(var rs = statement.executeQuery()) {
                rs.next();
                return rs.getInt("rating");
            }
        } catch (SQLException e) {
            throw new RatingException("Could not get rating!", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (
                var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.createStatement();
        )
        {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new CommentException("Could not delete ratings!", e);
        }
    }
}
