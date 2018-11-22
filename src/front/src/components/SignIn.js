import React, { Component } from 'react';
import Layout from './Layout';
import { Card, CardFooter, Container, Col, Form, FormGroup, Label, Input, Button, FormText} from 'reactstrap';
import axios from 'axios';
import con from '../config'
import { AvForm, AvField } from 'availity-reactstrap-validation';
import Validator from '../modules/Validator'

class SignIn extends Component {

  constructor(props){
    super(props);
    //this.props.history.push('/login');
    this.state = {
      email: "",
      password: "",
      errormsg: false
    };
    this.signin = this.signin.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  signin(){
      let self = this;
      if (Validator.validateEmail(this.state.email) && Validator.validatePassword(this.state.password)){

          axios(con.addr+'/mainServices/auth/signin', {
              method: "POST",
              data: JSON.stringify({
                  email: self.state.email,
                  password: self.state.password
              }),
              headers: {
                  'Content-Type': 'application/json'
              }
          })
              .then(function (response) {
                  console.log("    ----    ");
                  console.log(response.data);
                  localStorage.setItem('token', response.data[0]);
                  localStorage.setItem('userId', response.data[1]);
                  self.props.login(self.state.email.toLowerCase());
              })
              .catch(function (error) {
                  console.log(error);
                  self.setState({errormsg: true });
              });
      } else {
          self.setState({errormsg: true });
      }
  }

  handleChange(e){
    switch(e.target.name) {
      case("email"):
        this.setState({email: e.target.value});
        break;
      case("password"):
        this.setState({password: e.target.value});
        break;
      default:

    }
  }

    render() {
        return (
            <div>
                <Layout id="signin" auth = {false} />

                <div className="container text-center" style={{marginTop: "20px", width: "500px"}}>
                    <div className="row">
                        <h1 style={{marginLeft: "190px"}}>Sign In</h1>
                    </div>

                    <AvForm id="loginForm" style={{marginTop: "80px", marginBottom: "80px"}}>
                        <div className="incorrectEmailPasswordError" style={{display: this.state.errormsg ? 'block' : 'none'}}>
                            <div className="alert alert-danger" role="alert">
                                Invalid login! Try again
                            </div>
                        </div>
                        <AvField name="email" label="Email" onChange ={this.handleChange} type="email" value={this.state.email} />
                        <AvField name="password" label="Password" onChange ={this.handleChange} type="password" value={this.state.password} />
                        <Button color="primary" onClick={this.signin}>Submit</Button>
                    </AvForm>

                </div>
            </div>
        );
    }
}

export default SignIn;
