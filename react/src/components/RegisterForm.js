import {Button, Form} from "react-bootstrap";
import { useForm } from "react-hook-form";

function RegisterForm({onAddAccount, loggedIn}) {
    const { register, handleSubmit, formState: { errors } } = useForm({mode: "onChange"});
    const onSubmit = data => {
        onAddAccount(data.username, data.password); 
    }

    if(loggedIn.toString().length < 3) {
        return (
            <Form autoComplete="off" onSubmit={handleSubmit(onSubmit)}> 
                <Form.Group className="mb-3">
                    <Form.Label>Register</Form.Label>
                    <input className="form-control"
                        type="text"
                        {...register("username", {
                            minLength: {value: 6, message: "Minimum username length is 6 characters"},
                            maxLength: {value: 30, message: "Maximum username length is 30 characters"},
                            required:  {value: true, message: "This field is required"}
                        })}
                        placeholder="Username" />
                    <Form.Text style={{color: 'red', float: 'right'}}>
                        {errors.username?.message}
                    </Form.Text>

                    <input className="form-control"
                        type="password"
                        {...register("password", {
                            minLength: {value: 6, message: "Minimum password length is 6 characters"},
                            maxLength: {value: 30, message: "Maximum password length is 30 characters"},
                            required:  {value: true, message: "This field is required"}
                        })}
                        placeholder="Password" />
                    <Form.Text style={{color: 'red', float: 'right'}}>
                        {errors.password?.message}
                    </Form.Text>
                </Form.Group>
                <Button type="submit" disabled={errors.username}>Send</Button>
            </Form>
        );
    } else {
        return (
            <h5>You are already logged in</h5>
        );
    }
}

export default RegisterForm;