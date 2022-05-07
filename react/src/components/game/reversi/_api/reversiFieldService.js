import gsAxios from "../../../../_api";

const FIELD_URL = `/reversi/field`;
const NEW_GAME_URL = FIELD_URL + "/newGame";
const PLACE_STONE_URL = FIELD_URL + "/placeStone";
const COMPUTER_MOVE = FIELD_URL + "/computerMove";

const fetchField = (loggedPlayer) => gsAxios.get(`${FIELD_URL}?loggedPlayer=${loggedPlayer}`);
const newGame = (difficulty, playerName) => gsAxios.get(`${NEW_GAME_URL}?difficulty=${difficulty}&playerName=${playerName}`);
const placeStone = (row, col) => gsAxios.get(`${PLACE_STONE_URL}?row=${row}&col=${col}`);
const computerMove = () => gsAxios.get(COMPUTER_MOVE);

const fieldService = {fetchField, newGame, placeStone, computerMove};
export default fieldService;