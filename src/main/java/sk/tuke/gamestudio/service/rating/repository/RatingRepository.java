package sk.tuke.gamestudio.service.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.tuke.gamestudio.entity.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    @Query("select avg(r.rating) from Rating r where r.game = :game")
    public int getAverage(String game);

    @Query("select r.rating from Rating r where r.player =: player and r.game =: game")
    public int getRating(String game, String player);
}