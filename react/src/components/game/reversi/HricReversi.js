import './HricReversi.css';
import {useEffect, useState} from "react";
import fieldService from "./_api/reversiFieldService";
import Field from "./Field";
import {addScore} from "../../../_api/score.service";



function HricReversi({loggedPlayer, fetchData, difficulty}) {

    const [field, setField] = useState(null);

    useEffect( () => {
        if(difficulty === 'hard')
            fieldService.newHardGame(loggedPlayer).then(response => {
                setField(response.data);
            })
        else {
            fieldService.newEasyGame(loggedPlayer).then(response => {
                setField(response.data);
            })
        }
    }, []);

    const handleAddScore = () => {
        if(field.player1.score < field.player2.score || difficulty === 'easy')
            return;
        addScore('reversi', loggedPlayer, field.player1.score).then(response => {
        });
    }

    useEffect(() => {
        if(field?.state === "FINISHED") {
            console.log("vnutro");
            handleAddScore();
            fetchData();
        }
    },[field?.state]);

    const handlePlaceStone = async (row, col) => {
        if (
            "FINISHED".localeCompare(field.state) === 0 ||
            "OCCUPIED".localeCompare(field.tiles[row][col].tileState) === 0
        ) return;

        const response = await fieldService.placeStone(row, col);
        setField(response.data);

        const response2 = await fieldService.computerMove();
        setField(response2.data);
    };

    const handleNewGame = () => {

        if("HARD".localeCompare(field?.difficulty) === 0) {
            console.log(field?.difficulty);
            fieldService.newHardGame(loggedPlayer).then(response => {
                setField(response.data);
            });
        }

        else {
            fieldService.newEasyGame(loggedPlayer).then(response => {
                setField(response.data);
            })
        }
    }

    return (
        <div>
            <div className="game-container">

                <div className="reversi-info">

                    <h3>Player: <span className="concrete-info-text">{field?.player1.name}</span></h3>
                    <h3>Score: <span className="concrete-info-text">{field?.player1.score}</span></h3>
                    <h3>AI score: <span className="concrete-info-text">{field?.player2.score}</span></h3>
                    <h3>Game State: <span className="concrete-info-text">{field?.state}</span></h3>
                    <h3>Tiles left: <span className="concrete-info-text">{field?.freeTiles}</span></h3>
                    <h3>Difficulty: <span className="concrete-info-text">{field?.difficulty}</span></h3>
                    <br/>
                    <br/>
                    <div className="button-container">
                        <button className="abbuton" onClick={handleNewGame}>
                            New Game
                        </button>
                    </div>
                </div>

                {   field &&
                    <Field tiles={field.tiles} onPlaceStone={handlePlaceStone}/>
                }



            </div>
        </div>
    )
}

export default HricReversi;