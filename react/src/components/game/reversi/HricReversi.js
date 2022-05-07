import './HricReversi.css';
import {useEffect, useState} from "react";
import fieldService from "./_api/reversiFieldService";
import Field from "./Field";
import {addScore} from "../../../_api/score.service";



function HricReversi({loggedPlayer}) {

    const [field, setField] = useState(null);

    useEffect( () => {
        fieldService.fetchField(loggedPlayer).then(response => {
            setField(response.data);
        })
    }, []);

    const handleAddScore = () => {
        console.log(field.player1.score);
        console.log(field.player2.score);
        if(field.player1.score < field.player2.score)
            return;

        addScore('reversi', loggedPlayer, field.player1.score).then(response => {

        });
    }

    useEffect(() => {
        if(field?.state === "FINISHED") {
            console.log("vnutro");
            handleAddScore();
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
        fieldService.newGame("hard", loggedPlayer).then(response => {
            setField(response.data);
        })
    }

    return (
        <div className="game-container">
            <div>
                <h1>Reversi</h1>
                <div className="reversi-toolbar">
                    <h3>{field?.player1.name}'s score: {field?.player1.score}</h3>
                    <h3>{field?.player2.name}'s score: {field?.player2.score}</h3>
                    <h3>Game State: {field?.state}</h3>
                    <h3>Tiles left: {field?.freeTiles}</h3>
                    <button className="abbuton" onClick={handleNewGame}>
                        New Game
                    </button>
                    <br/>
                    <br/>
                </div>
                {   field &&
                    <Field tiles={field.tiles} onPlaceStone={handlePlaceStone}/>
                }
            </div>
        </div>
    )
}

export default HricReversi;