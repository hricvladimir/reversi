package sk.tuke.gamestudio.entity;

import sk.tuke.gamestudio.service.rating.RatingId;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(RatingId.class)
public class Rating {

    @Id
    private String game;

    @Id
    private String player;
    private int rating;
    private Date ratedOn;

    public Rating(String player, String game, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public Rating() {

    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating='" + rating + '\'' +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
