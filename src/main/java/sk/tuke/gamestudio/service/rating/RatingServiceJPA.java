package sk.tuke.gamestudio.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.rating.repository.RatingRepository;

import javax.transaction.Transactional;

@Transactional
@Service
public class RatingServiceJPA implements RatingService {

    @Autowired
    RatingRepository repository;

    @Override
    public void setRating(Rating rating) throws RatingException {
        repository.save(rating);
    }

    @Query
    public int getAverageRating(String game) throws RatingException {
        return repository.getAverage(game);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return repository.getRating(player,game);
    }

    @Override
    public void reset() throws RatingException {

    }
}
