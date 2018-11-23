import React, {Component} from 'react';
import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem
} from 'reactstrap';
import {Link} from 'react-router-dom';
import con from '../config.js'

export default class Layout extends Component {

    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        let self = this;
        return (
            <Navbar expand="md" style={{marginBottom: "10px", backgroundColor: "#4E729A"}}>
                <NavbarBrand href="/aintrovert" style={{color: "white"}}>AIntrovert</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="ml-auto" navbar>
                        {
                            !self.props.auth &&
                            <>
                                {self.props.id !== "signin" ? (<NavItem>
                                    <Link className="nav navlink" to={con.projectName + '/signin'}>Sign In</Link>
                                </NavItem>) : ''}
                                {self.props.id !== "signup" ? (<NavItem>
                                    <Link className="navlink" to={con.projectName + '/signup'}>Sign Up</Link>
                                </NavItem>) : ''}
                            </>
                        }
                        {
                            this.props.auth &&
                            <>
                                <NavItem className="btn" onClick={self.props.logout} style={{color: "white"}}>Sign
                                    Out</NavItem>
                            </>
                        }
                    </Nav>
                </Collapse>
            </Navbar>
        );
    }
}