import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {addComment, fetchComments} from "./_api/comment.service";
import {fetchScores} from "./_api/score.service";
import {useEffect, useState} from "react";
import Comments from "./components/Comments";
import CommentForm from "./components/CommentForm";
import Scores from "./components/Scores";
import {addRating, fetchAverageRating, fetchRating} from "./_api/rating.service";
import Ratings from "./components/Ratings";
import RatingForm from "./components/RatingForm";
import HricReversi from "./components/game/reversi/HricReversi";

function App() {

    const loggedPlayer = 'gody';
    const selectedGame = 'reversi';
    const [comments, setComments] = useState([]);
    const [scores, setScores] = useState([]);
    const [rating, setRating] = useState([]);
    const [averageRating, setAverageRating] = useState([]);


    const fetchData = () => {
        fetchScores(selectedGame).then(response => {
            setScores(response.data);
        })
        fetchComments(selectedGame).then(response => {
            setComments(response.data);
        });
        fetchRating(selectedGame, loggedPlayer).then(response => {
            setRating(response.data);
        })
        fetchAverageRating(selectedGame).then(response => {
            setAverageRating(response.data);
        })
    }

    useEffect( () => {
        fetchData();
    }, []);

    const handleSendComment = comment => {
        addComment(selectedGame, loggedPlayer, comment).then(response => {
            fetchData();
        });
    }

    const handleSendRating = rating => {
        addRating(selectedGame, loggedPlayer, rating).then(response => {
            fetchData();
        })
    }

    return (
    <div className="App container-fluid mt-4 mb-5">
        {/* HEADING */}
        <h1>Reversi</h1>
        <HricReversi loggedPlayer={loggedPlayer}/>

        {/* SCORES */}
        <h1>Scores</h1>
        <Scores scores={scores}/>

        {/* RATING */}
        <h1>Ratings</h1>
        <Ratings rating={rating} player={loggedPlayer} averageRating={averageRating}/>
        <RatingForm game={selectedGame} player={loggedPlayer} onSendRating={handleSendRating}/>

        {/* COMMENTS */}
        <h1>Comments</h1>
        <h2>Add comment</h2>
        <CommentForm game={selectedGame} player={loggedPlayer} onSendComment={handleSendComment}/>
        <br/>
        <Comments comments={comments}/>


    </div>
    );
}

export default App;
