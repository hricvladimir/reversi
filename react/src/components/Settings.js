import {Link} from "react-router-dom";
import obrazok from "../images/reversi-image.png";

function Settings({setDifficulty, setGamemode}) {
    function easy() {
        setDifficulty('easy');
        setGamemode('PVAI');
    }

    function hard() {
        setDifficulty('hard');
        setGamemode('PVAI');
    }

    function pvp() {
        setDifficulty('easy');
        setGamemode('PVP');
    }

    return (
        <div className="settingsContainer">

            <h1 className="welcome-text">Welcome to Reversi!</h1>
            <h2 className="difficulty-text">Please select DIFFICULTY/GAMEMODE:</h2><br/>


            <div className="button-container">
                <Link to="/game">
                    <button onClick={easy} className="settings-confirm-button" type="button"> EASY </button>
                </Link>
                <Link to="/game">
                    <button onClick={hard} className="settings-confirm-button" type="button"> HARD </button>
                </Link>
                <Link to="/game">
                    <button onClick={pvp} className="settings-confirm-button" type="button"> PLAYER vs. PLAYER </button>
                </Link>
            </div>
            
            <div className="reversi-image">
                <img id="reversi-img" src={obrazok} alt="reversiimg"/>
            </div>
        </div>
    );
}

export default Settings;