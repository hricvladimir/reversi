import {Link} from "react-router-dom";
import obrazok from "../images/reversi-image.png";

function Settings({setDifficulty}) {
    function easy() {
        setDifficulty('easy');
    }

    function hard() {
        setDifficulty('hard');
    }

    return (
        <div className="settingsContainer">

            <h1 className="welcome-text">Welcome to Reversi!</h1>
            <h2 className="difficulty-text">Please select DIFFICULTY:</h2><br/>


            <div className="button-container">
                <Link to="/game">
                    <button onClick={easy} className="settings-confirm-button" type="button"> EASY </button>
                </Link>
                <Link to="/game">
                    <button onClick={hard} className="settings-confirm-button" type="button"> HARD </button>
                </Link>
            </div>
            
            <div className="reversi-image">
                <img id="reversi-img" src={obrazok} alt="reversiimg"/>
            </div>
        </div>
    );
}

export default Settings;