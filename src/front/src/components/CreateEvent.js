import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input, FormText, Container, Row, Col } from 'reactstrap';
import con from "../config";
import axios from "axios/index";

class CreateEvent extends Component {

	constructor(props){
		super(props);
		this.state = {
            name: "",
            date: "",
			time: "",
            description: "",
            location: "",
			category: "Other",
            price: 0,
            points: 0,
			img: "",
			msg: ""
        };
        this.handleChange = this.handleChange.bind(this);
        this.send = this.send.bind(this);
	}

    handleChange(e) {
        switch (e.target.name) {
            case("name"):
                this.setState({name: e.target.value});
                break;
            case("location"):
                this.setState({location: e.target.value});
                break;
            case("date"):
                this.setState({date: e.target.value});
                break;
            case("time"):
                this.setState({time: e.target.value});
                break;
            case("description"):
                this.setState({description: e.target.value});
                break;
            case("category"):
                this.setState({category: e.target.value});
                break;
            case("price"):
                this.setState({price: e.target.value});
                break;
            case("points"):
                this.setState({points: e.target.value});
                break;
            case("img"):
                this.setState({img: e.target.value});
                break;
            default:
        }
    }

    send(){
        var self = this;
        console.log(JSON.stringify({
            name: self.state.name,
            meetingdate: self.state.date+'',
            meetingtime: self.state.time+'',
            description: self.state.description,
            location: self.state.location,
            category: self.state.category+'',
            price: self.state.price+'',
            points: self.state.points+'',
            img: self.state.img,
            token: localStorage.getItem('token')
        }));
        axios(con.addr + '/mainServices/event/create', {
            method: "POST",
            data: JSON.stringify({
                name: self.state.name,
                meetingdate: self.state.date+'',
                meetingtime: self.state.time+'',
                description: self.state.description,
                location: self.state.location,
                category: self.state.category+'',
                price: self.state.price+'',
                points: self.state.points+'',
                img: self.state.img,
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response);
                self.setState({msg: "created"});
            })
            .catch(function (error) {
                console.log(error);
                self.setState({msg: "error"});
            });
	}

  render() {
		var self = this;
    return (
	    <Container className="border rounded event-create">
	        <FormGroup>
	          <Label for="name">Name of the Event<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.name} type="text" name="name" id="name" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="location">Location<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.location} type="text" name="location" id="location" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="description">Description<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.description} type="textarea" name="description" id="description" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="date">Date<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.data} type="date" name="date" id="date" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="time">Time<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.time} type="time" name="time" id="time" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="category">Category<span>*</span></Label>
	          <Input onChange={this.handleChange} value={this.state.category} type="select" name="category" id="category" required>
	            <option>Sport</option>
	            <option>Study</option>
	            <option>Outdoors</option>
				  <option selected>Other</option>
	          </Input>
	        </FormGroup>
              <FormGroup>
                  <Label for="price">Price<span>*</span></Label>
                  <Input onChange={this.handleChange} value={this.state.price} type="number" name="price" id="price" required/>
              </FormGroup>
              <FormGroup>
                  <Label for="points">Points<span>*</span></Label>
                  <Input onChange={this.handleChange} value={this.state.points} type="number" name="points" id="points" required/>
              </FormGroup>
	        <FormGroup>
	          <Label for="img">Link to image</Label>
	          <Input onChange={this.handleChange} value={this.state.img} type="text" name="img" id="img" />
	        </FormGroup>
	        <Button onClick={self.send}>Submit</Button>
			  <p>{self.state.msg}</p>
      </Container>
    );
  }
}

export default CreateEvent;
