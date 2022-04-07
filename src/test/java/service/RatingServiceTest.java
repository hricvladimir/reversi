package service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.rating.RatingException;
import sk.tuke.gamestudio.service.rating.RatingService;
import sk.tuke.gamestudio.service.rating.RatingServiceJDBC;
import sk.tuke.gamestudio.service.rating.RatingServiceJPA;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {
    private RatingService service = new RatingServiceJDBC();

    @Test
    public void addRatingTest() {

        service.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        // adding data
        service.setRating(rating1);
        service.setRating(rating2);
        service.setRating(rating3);
        service.setRating(rating4);

        // asserts
        assertEquals(5, service.getRating("reversi", "peter"));
        assertEquals(4, service.getRating("reversi", "jano"));
        assertEquals(3, service.getRating("reversi", "vlado"));
        assertEquals(2, service.getRating("reversi", "jozo"));

    }

    @Test
    public void getRatingTest() {
        service.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        // adding data
        service.setRating(rating1);
        service.setRating(rating2);
        service.setRating(rating3);
        service.setRating(rating4);

        // asserts
        assertEquals(5, service.getRating("reversi", "peter"));
        assertEquals(4, service.getRating("reversi", "jano"));
        assertEquals(3, service.getRating("reversi", "vlado"));
        assertEquals(2, service.getRating("reversi", "jozo"));
    }

    @Test
    public void resetScoresTest() {
        service.reset(); // resets ratings

        // creating ratings
        Rating rating1 = new Rating("peter", "reversi", 5, new Date());
        Rating rating2 = new Rating("jano", "reversi", 4, new Date());
        Rating rating3 = new Rating("vlado", "reversi", 3, new Date());
        Rating rating4 = new Rating("jozo", "reversi", 2, new Date());

        service.reset(); // resets ratings
        try {
            assertEquals(0, service.getRating("reversi", "peter"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, service.getRating("reversi", "jano"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, service.getRating("reversi", "vlado"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
        try {
            assertEquals(0, service.getRating("reversi", "jozo"));
        } catch (RatingException exception) {
            System.out.println("Didnt find rating.");
        }
    }
}
