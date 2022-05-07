import {Table} from "react-bootstrap";

function Scores({scores}) {
    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Player</th>
                <th>Score</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            {scores.map(score => (
                <tr key={`score-${score.id}`}>
                    <td>{score.player}</td>
                    <td>{score.points}</td>
                    <td>{new Date(score.playedOn).toLocaleString()}</td>
                </tr>
            ))}

            </tbody>
        </Table>
    );
}

export default Scores;