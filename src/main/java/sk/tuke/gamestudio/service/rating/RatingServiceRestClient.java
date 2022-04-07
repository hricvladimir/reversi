package sk.tuke.gamestudio.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.Objects;

@Service
public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return Objects.requireNonNull(restTemplate.getForEntity(url + "/" + game, Integer.class).getBody());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating = -1;
        try{
            rating = Objects.requireNonNull(restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class).getBody());
        } catch (HttpServerErrorException ex) {
            throw new RatingException("This user did not rate this game yet!");
        }

        return rating;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}