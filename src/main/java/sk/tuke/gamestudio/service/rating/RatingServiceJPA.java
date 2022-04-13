package sk.tuke.gamestudio.service.rating;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.repository.RatingRepository;

import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {

    @Autowired
    RatingRepository repo;

    @Override
    public void setRating(Rating rating) throws RatingException {
        repo.save(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return repo.getAverage(game);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating;
        try{
            rating = repo.getRating(game,player);
        } catch (AopInvocationException e) {
            throw new RatingException("This user did not rate this game yet!");
        }

        return rating;
    }

    @Override
    public void reset() throws RatingException {
        repo.deleteAll();
    }
}
