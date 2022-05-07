import {Table} from "react-bootstrap";

function Comments({comments}) {
    return (
    <Table striped bordered hover>
        <thead>
        <tr>
            <th>Player</th>
            <th>Comment</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        {comments.map(comment => (
            <tr key={`comment-${comment.id}`}>
                <td>{comment.player}</td>
                <td>{comment.comment}</td>
                <td>{new Date(comment.commentedOn).toLocaleString()}</td>
            </tr>
        ))}

        </tbody>
    </Table>
    );
}

export default Comments;