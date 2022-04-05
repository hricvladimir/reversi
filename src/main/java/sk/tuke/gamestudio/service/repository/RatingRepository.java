package sk.tuke.gamestudio.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.tuke.gamestudio.entity.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    @Query("select avg(r.rating) from Rating r where r.game = :game")
    int getAverage(String game);

    @Query("select r.rating from Rating r where r.game = :game and r.player = :player")
    int getRating(String game, String player);
}