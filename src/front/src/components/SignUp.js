import React, {Component} from 'react';
import Layout from './Layout';
import {Card, CardFooter, Container, Col, Form, FormGroup, Label, Input, Button, FormText} from 'reactstrap';
import axios from 'axios';
import con from '../config'
import {AvForm, AvField} from 'availity-reactstrap-validation';
import validator from '../modules/Validator'

class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            surname: "",
            email: "",
            password: "",
            passwordConfirm: "",
            sendDisabled: true,
            errormsg: false
        };
        this.signup = this.signup.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    signup() {
        let self = this;
        axios(con.addr + '/mainServices/auth/signup', {
            method: "POST",
            data: JSON.stringify({
                name: self.state.name,
                surname: self.state.surname,
                email: self.state.email.toLowerCase(),
                password: self.state.password
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response);
                localStorage.setItem('token', response.data[0]);
                localStorage.setItem('userId', response.data[1]);
                localStorage.setItem('email', self.state.email.toLowerCase());
                self.props.login(self.state.email.toLowerCase());
            })
            .catch(function (error) {
                console.log(error);
                self.setState({errormsg: true});
            });
    }

    handleChange(e) {
        switch (e.target.name) {
            case("name"):
                this.setState({name: e.target.value});
                break;
            case("surname"):
                this.setState({surname: e.target.value});
                break;
            case("email"):
                this.setState({email: e.target.value});
                break;
            case("password"):
                this.setState({password: e.target.value});
                break;
            case("passwordConfirm"):
                this.setState({passwordConfirm: e.target.value});
                break;
            default:

        }
    }

    render() {
        var self = this;
        return (

            <div>
                <Layout id="signup" auth={false}/>

                <div className="container text-center" style={{paddingTop: "20px", width: "500px"}}>
                    <div className="row">
                        <h2 style={{marginLeft: "80px", marginBottom: "20px"}}>Sign Up</h2>
                    </div>
                    <AvForm id="registerForm" style={{marginTop: "80px", marginBottom: "80px"}}>
                        <div className="userExists" style={{display: this.state.errormsg ? 'block' : 'none'}}>
                            <div className="alert alert-danger" role="alert">
                                Entered email already exists! Enter another email
                            </div>
                        </div>
                        <AvField name="name" label="Name" id="name" onChange={this.handleChange} type="text"
                                 value={this.state.name} errorMessage="Name should contain at least 2 characters"
                                 validate={{
                                     required: {value: true},
                                     pattern: {
                                         value: '^([A-Z][a-z]+$)',
                                         errorMessage: 'Name should be in latin alphabetic starting with the first capital letter'
                                     },
                                     minLength: {value: 2}
                                 }}/>
                        <AvField name="surname" label="Surname" id="surname" onChange={this.handleChange} type="text"
                                 value={this.state.surname} errorMessage="Surname should contain at least 1 character"
                                 validate={{
                                     required: {value: true},
                                     pattern: {
                                         value: '^([A-Z][a-z]+$)',
                                         errorMessage: 'Surname should be in latin alphabetic starting with the first capital letter'
                                     },
                                     minLength: {value: 2}
                                 }}/>
                        <AvField name="email" label="Email" onChange={this.handleChange} type="email"
                                 value={this.state.email} errorMessage="Invalid email format" validate={{
                                     required: {value: true},
                                    pattern: {
                                         value: '^(([^<>()\\[\\]\\\\.,;:\\s@"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@"]+)*)|(".+"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$',
                                        errorMessage: 'Invalid email'
                                    }
                        }}/>
                        <AvField name="password" label="Password" onChange={this.handleChange} type="password"
                                 value={this.state.password}
                                 errorMessage='Your password should contain at least 8 characters' validate={{
                            required: {value: true},
                            pattern: {
                                value: '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\\S+$)(?=.{8,})',
                                errorMessage: 'Minimum 8 characters, at least 1 uppercase, 1 lowercase and 1 number. No whitespaces allowed!'
                            }
                        }}/>
                        <AvField name="passwordConfirm" label="Confirm password" onChange={this.handleChange}
                                 type="password" value={this.state.passwordConfirm}
                                 errorMessage='Passwords do not match' validate={{
                            required: {value: true},
                            pattern: {value: this.state.password}
                        }}/>
                        <Button color="primary" onClick={this.signup} disabled={!validator.validateEmail(self.state.email) || !validator.validatePassword(self.state.password) || self.state.password !== self.state.passwordConfirm || !validator.validName(self.state.name) || !validator.validName(self.state.surname) } >Submit</Button>
                    </AvForm>
                </div>
            </div>
        );
    }
}

export default SignUp;
