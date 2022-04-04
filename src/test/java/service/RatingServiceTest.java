package service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.rating.RatingException;
import sk.tuke.gamestudio.service.rating.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {
    private RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();

    @Test
    public void addRatingTest() {

        ratingServiceJDBC.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        // adding data
        ratingServiceJDBC.setRating(rating1);
        ratingServiceJDBC.setRating(rating2);
        ratingServiceJDBC.setRating(rating3);
        ratingServiceJDBC.setRating(rating4);

        // asserts
        assertEquals(5, ratingServiceJDBC.getRating("reversi", "peter"));
        assertEquals(4, ratingServiceJDBC.getRating("reversi", "jano"));
        assertEquals(3, ratingServiceJDBC.getRating("reversi", "vlado"));
        assertEquals(2, ratingServiceJDBC.getRating("reversi", "jozo"));

    }

    @Test
    public void getRatingTest() {
        ratingServiceJDBC.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        // adding data
        ratingServiceJDBC.setRating(rating1);
        ratingServiceJDBC.setRating(rating2);
        ratingServiceJDBC.setRating(rating3);
        ratingServiceJDBC.setRating(rating4);

        // asserts
        assertEquals(5, ratingServiceJDBC.getRating("reversi", "peter"));
        assertEquals(4, ratingServiceJDBC.getRating("reversi", "jano"));
        assertEquals(3, ratingServiceJDBC.getRating("reversi", "vlado"));
        assertEquals(2, ratingServiceJDBC.getRating("reversi", "jozo"));
    }

    @Test
    public void resetScoresTest() {
        ratingServiceJDBC.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        ratingServiceJDBC.reset(); // resets ratings
        try {
            assertEquals(0, ratingServiceJDBC.getRating("reversi", "peter"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, ratingServiceJDBC.getRating("reversi", "jano"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, ratingServiceJDBC.getRating("reversi", "vlado"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, ratingServiceJDBC.getRating("reversi", "jozo"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
    }
}
