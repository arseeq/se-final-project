import React, { Component } from 'react';
import Layout from './Layout';
import { Card, CardFooter, Container, Col, Form, FormGroup, Label, Input, Button, FormText} from 'reactstrap';
import axios from 'axios';
import con from '../config'

class SignUp extends Component {

	constructor(props){
		super(props);
		//this.props.history.push('/login');
    this.state = {
      name: "",
      surname: "",
      email: "",
      password: ""
    };
    this.signup = this.signup.bind(this);
    this.handleChange = this.handleChange.bind(this);
	}

  signup(){
    var self = this;
    axios(con.addr+'/mainServices/auth/signup', {
      method: "POST",
      data: JSON.stringify({
        name: self.state.name,
        surname: self.state.surname,
        email: self.state.email,
        password: self.state.password
      }),
      headers: {
        'Content-Type': 'application/json' 
      }
    })
    .then(function (response) {
      console.log(response);
      localStorage.setItem('token', response.data);
      self.props.login(self.state.email);
    })
    .catch(function (error) {
      console.log(error);
    });
  }

  handleChange(e){
    switch(e.target.name) {
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
      default:

    }
  }

  render() {
    return (

      <div>
        <Layout id="signup" auth = {false} />

        <div className="container text-center" style={{paddingTop: "20px", width: "500px"}}>
          <div className="row">
            <h2 style={{marginLeft: "80px", marginBottom: "20px"}}>Registratsiya zhasa</h2>
          </div>
          <Form className="text-left" style={{marginTop: "80px", marginBottom: "80px"}}>
            <FormGroup>
              <Label for="name">Name</Label>
              <Input type="text" onChange={this.handleChange} name="name" id="name" value={this.state.name}/>
            </FormGroup>
            <FormGroup>
              <Label for="surname">Surname</Label>
              <Input type="text" onChange={this.handleChange} name="surname" id="surname" value={this.state.surname}/>
            </FormGroup>
            <FormGroup>
              <Label for="exampleEmail">Email</Label>
              <Input type="email" onChange={this.handleChange} name="email" id="exampleEmail" value={this.state.email}/>
            </FormGroup>
            <FormGroup>
              <Label for="examplePassword">Password</Label>
              <Input type="password" onChange={this.handleChange} name="password" id="examplePassword" value={this.state.password}/>
            </FormGroup>
            <FormGroup>
              <Label for="exampleSelect">Select</Label>
              <Input type="select" name="select" id="exampleSelect">
                <option>Male</option>
                <option>Mambet</option>
                <option>Female</option>
              </Input>
            </FormGroup>
            <FormGroup check>
              <Label check>
                <Input type="checkbox" />{' '}
                Remember that I am mambet
              </Label>
            </FormGroup>
            <Button onClick={this.signup} style={{marginTop: "20px"}}>Submit</Button>
          </Form>
        </div>
      </div>
    );
  }
}

export default SignUp;
