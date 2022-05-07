import {Table} from "react-bootstrap";

function Ratings({rating, player, averageRating}) {
    return (
            <h5>
                <p>Name: {player}</p>
                <p>Rating: {rating}</p>
                <p>Average rating: {averageRating}</p>
            </h5>
    );
}


export default Ratings;