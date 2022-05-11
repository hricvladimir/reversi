import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import React from 'react';
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
import Menu from "./components/Menu";
import {Route, Routes} from "react-router-dom";
import Settings from "./components/Settings";
import LoginForm from "./components/LoginForm";
import Logout from "./components/Logout";
import RegisterForm from "./components/RegisterForm";
import {addAccount, fetchAccounts} from "./_api/account.service";
import { useLocalStorage } from './useLocalStorage';

function App() {
    const selectedGame = 'reversi';
    const [comments, setComments] = useState([]);
    const [scores, setScores] = useState([]);
    const [rating, setRating] = useState([]);
    const [averageRating, setAverageRating] = useState([]);
    const [difficulty, setDifficulty] = useState(['hard']);
    const [gamemode, setGamemode] = useState(['PVAI']);
    const [loggedIn, setLoggedIn] = useLocalStorage('account','');

    const fetchData = () => {
        fetchScores(selectedGame).then(response => {
            setScores(response.data);
        })
        fetchComments(selectedGame).then(response => {
            setComments(response.data);
        });
        fetchRating(selectedGame, loggedIn.username).then(response => {
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
        addComment(selectedGame, loggedIn.username, comment).then(response => {
            fetchData();
        });
    }

    const handleSendRating = rating => {
        addRating(selectedGame, loggedIn.username, rating).then(response => {
            fetchData();
        })
    }

    const handleAddAccount = (username, password) => {
        let hashed_password = password;
        addAccount(username, hashed_password).then(response => {
            console.log('account created');
        })
    }

    const handleLoginAttempt = (username, password) => {
        let hashed_password = password;
        fetchAccounts(username, hashed_password).then(response => {
            console.log('login attempted');
            console.log(response.data);
            const correctAccount = response.data;

            if(correctAccount[0] != null) {
                console.log('exists');
                console.log(correctAccount[0].username);

                if(correctAccount[0].hashedPassword.valueOf() === hashed_password.valueOf()) {
                    setLoggedIn(correctAccount[0]);
                    console.log('saved:');
                    console.log(loggedIn);

                    window.location.href = "http://localhost:3000";
                } else {
                    console.log('incorrect password');
                }
            } else {
                console.log('does not exist');
            }
        })
    }

    return (
        <div className="App container-fluid mt-4 mb-5">
            {/* HEADING */}
            <Menu/>
            <div className="container index-container">
                <Routes>
                    <Route path={"scores"} element={
                        <div className="scores">
                            <React.Fragment>
                                <h1>Scores</h1>
                                <Scores scores={scores}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"comments"} element={
                        <div className="comments">
                            <React.Fragment>
                                <h1>Comments</h1>
                                <h2>Add comment</h2>
                                <CommentForm game={selectedGame} player={loggedIn.username} onSendComment={handleSendComment} loggedIn={loggedIn}/>
                                <br/>
                                <Comments comments={comments}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"ratings"} element={
                        <div className="ratings">
                            <React.Fragment>
                                <Ratings rating={rating} player={loggedIn.username} averageRating={averageRating}/>
                                <RatingForm game={selectedGame} player={loggedIn.username} onSendRating={handleSendRating} loggedIn={loggedIn}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"login"} element={
                        <div className="login">
                            <React.Fragment>
                                <h1>Login</h1>
                                <LoginForm onLoginAttempted={handleLoginAttempt} loggedIn={loggedIn}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"logout"} element={
                        <div className="logout">
                            <React.Fragment>
                                <Logout setLoggedIn={setLoggedIn}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"register"} element={
                        <div className="register">
                            <React.Fragment>
                                <h1>Register</h1>
                                <RegisterForm onAddAccount={handleAddAccount} loggedIn={loggedIn}/>
                            </React.Fragment>
                        </div>
                    }/>
                    <Route path={"/"} element={
                        <div className="settings-container">
                            <Settings setDifficulty={setDifficulty} setGamemode={setGamemode}/>
                        </div>
                    }/>
                    <Route path={"/game"} element={
                        <div className="game-container">
                            <HricReversi loggedPlayer={loggedIn.username} fetchData={fetchData} difficulty={difficulty} gamemode={gamemode} setScores={setScores}/>
                        </div>
                    }/>
                </Routes>
            </div>
        </div>
    );
}

export default App;
