import "./Menu.css";
import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link, NavLink} from "react-router-dom";
import { Modal } from 'bootstrap'


const Menu = () => {
    return (
        <Navbar bg="secondary" expand="lg" style={{position: "fixed", width: '100vw', top: '0', left: '0'}}>
            <Container className="header-container">
                {/* eslint-disable-next-line react/jsx-no-undef */}
                <Link className="navbar-brand" to ="/"><strong>REVERSI</strong></Link>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <NavLink className="nav-link" to="scores">Scores</NavLink>
                        <NavLink className="nav-link" to="comments">Comments</NavLink>
                        <NavLink className="nav-link" to="ratings">Ratings</NavLink>
                        <NavDropdown title="Actions" id="basic-nav-dropdown">
                            <NavLink className="nav-link" to="login">Login</NavLink>
                            <NavLink className="nav-link" to="register">Register</NavLink>
                            <NavLink className="nav-link" to="logout">Logout</NavLink>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Menu;