import React, { Component } from 'react';
import Layout from './Layout';
import { Card, CardFooter, Container, Col, Form, FormGroup, Label, Input, Button, FormText} from 'reactstrap';
import axios from 'axios';
import con from '../config'

class SignIn extends Component {

  constructor(props){
    super(props);
    //this.props.history.push('/login');
    this.state = {
      email: "",
      password: ""
    };
    this.signin = this.signin.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  signin(){
    var self = this;
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
      localStorage.setItem('token', response.data);
      self.props.login(self.state.email);
    })
    .catch(function (error) {
      console.log(error);
    });
    
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

          <Form className="form" style={{marginTop: "80px", marginBottom: "80px"}}>
            <Col>
              <FormGroup>
                <Label>Email</Label>
                <Input onChange={this.handleChange} type="email" name="email" id="exampleEmail" value={this.state.email}/>
              </FormGroup>
            </Col>
            <Col>
              <FormGroup>
                <Label for="examplePassword">Password</Label>
                <Input onChange={this.handleChange} type="password" name="password" id="examplePassword" value={this.state.password}/>
              </FormGroup>
            </Col>
            <Button onClick={this.signin}>Submit</Button>
          </Form>

        </div>
      </div>
    );
  }
}

export default SignIn;
