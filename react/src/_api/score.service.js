// get => api/score/reversi
// post => api/score

import gsAxios from "./index";
import {formatDate} from "./utils";

export const fetchScores = game => gsAxios.get('/score/' + game);
export const addScore = (game, player, points) => gsAxios.post('/score/', {
    game, player, points, playedOn: formatDate(new Date()),
});