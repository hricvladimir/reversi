import {Button, Form} from "react-bootstrap";
import { useForm } from "react-hook-form";

function RatingForm({game, player, onSendRating, loggedIn}) {
    const { register, handleSubmit, formState: { errors } } = useForm({mode: "onChange"});
    const onSubmit = data => {
        onSendRating(data.rating);
    }

    if(loggedIn.toString().length > 3) {
        return (
            <Form autoComplete="off" onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3">
                    <Form.Label>Rating</Form.Label>
                    <input className="form-control"
                           type="text"

                           {...register("rating", {
                               required: "Required",
                               minLength: {value: 1, message: "Please input number in range 1 - 5"},
                               maxLength: {value: 1, message: "Please input number in range 1 - 5"},
                               pattern: {
                                   value: /[1-5]/i,
                                   message: "Please enter rating in range of 1-5"
                               },
                           })}
                           placeholder="Enter your rating" />
                    <Form.Text style={{color: 'red', float: 'right'}}>
                        {errors.rating?.message}
                    </Form.Text>
                </Form.Group>
                <Button type="submit" disabled={errors.rating}>Send</Button>
            </Form>

        );
    } else {
        return (
            <h5>You must log in to submit a rating.</h5>
        );
    }
}

export default RatingForm;