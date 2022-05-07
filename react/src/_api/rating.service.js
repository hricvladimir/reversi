// get => api/rating/reversi/player
// post => api/rating/reversi

import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchRating = (game, player) => gsAxios.get('/rating/' + game + "/" + player);
export const fetchAverageRating = game => gsAxios.get('rating/' + game);
export const addRating = (game, player, rating) => gsAxios.post('/rating/', {
    game, player, rating, ratedOn: formatDate(new Date()),
});