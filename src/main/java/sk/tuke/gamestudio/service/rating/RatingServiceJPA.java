package sk.tuke.gamestudio.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.repository.RatingRepository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Transactional
@Service
public class RatingServiceJPA implements RatingService {

    @Autowired
    RatingRepository repository;

    @Override
    public void setRating(Rating rating) throws RatingException {
        repository.save(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return 0;
    }

    @Override
    public void reset() throws RatingException {

    }
}
