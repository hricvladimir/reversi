import {Button, Form} from "react-bootstrap";
import { useForm } from "react-hook-form";

function CommentForm({game, player, onSendComment, loggedIn}) {
    const { register, handleSubmit, formState: { errors } } = useForm({mode: "onChange"});
    const onSubmit = data => {
        onSendComment(data.comment);
    }

    if(loggedIn.toString().length > 3) {
        return (
            <Form autoComplete="off" onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3">
                    <Form.Label>Comment</Form.Label>
                    <input className="form-control"
                           type="text"
                           {...register("comment", {
                               minLength: {value: 3, message: "Minimum comment length is 3 characters"},
                               maxLength: {value: 500, message: "Maximum comment length is 500 characters"},
                               required:  {value: true, message: "Comment message is required"}
                           })}
                           placeholder="Enter your comment" />
                    <Form.Text style={{color: 'red', float: 'right'}}>
                        {errors.comment?.message}
                    </Form.Text>
                </Form.Group>
                <Button type="submit" disabled={errors.comment}>Send</Button>
            </Form>

        );
    } else {
        return (
            <h5>You must be logged in to submit a comment.</h5>
        );
    }
}

export default CommentForm;