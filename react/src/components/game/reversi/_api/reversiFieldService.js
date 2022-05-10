import gsAxios from "../../../../_api";

const FIELD_URL = `/reversi/field`;
const NEW_EASY_GAME_URL = FIELD_URL + "/newGame/easy";
const NEW_HARD_GAME_URL = FIELD_URL + "/newGame/hard";
const PLACE_STONE_URL = FIELD_URL + "/placeStone";
const COMPUTER_MOVE = FIELD_URL + "/computerMove";

const fetchField = (loggedPlayer) => gsAxios.get(`${FIELD_URL}?loggedPlayer=${loggedPlayer}`);
const newEasyGame = (playerName) => gsAxios.get(`${NEW_EASY_GAME_URL}?playerName=${playerName}`);
const newHardGame = (playerName) => gsAxios.get(`${NEW_HARD_GAME_URL}?playerName=${playerName}`);
const placeStone = (row, col) => gsAxios.get(`${PLACE_STONE_URL}?row=${row}&col=${col}`);
const computerMove = () => gsAxios.get(COMPUTER_MOVE);

const fieldService = {fetchField, newEasyGame, newHardGame, placeStone, computerMove};
export default fieldService;