package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.rating.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }

    @GetMapping("/{game}")
    public float getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }
}
