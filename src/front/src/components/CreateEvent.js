import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input, FormText, Container, Row, Col } from 'reactstrap';

class CreateEvent extends Component {
  render() {
    return (
	    <Container className="border rounded event-create">
	      <Form>
	        <FormGroup>
	          <Label for="name">Name of the Event<span>*</span></Label>
	          <Input type="text" name="name" id="name" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="place">Place<span>*</span></Label>
	          <Input type="text" name="place" id="place" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="description">Description<span>*</span></Label>
	          <Input type="textarea" name="description" id="description" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="date">Date<span>*</span></Label>
	          <Input type="date" name="date" id="date" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="time">Time<span>*</span></Label>
	          <Input type="time" name="time" id="time" required/>
	        </FormGroup>
	        <FormGroup>
	          <Label for="category">Category<span>*</span></Label>
	          <Input type="select" name="category" id="category" required>
	            <option>Sport</option>
	            <option>Study</option>
	            <option>Outdoors</option>
	          </Input>
	        </FormGroup>
	        <FormGroup>
	          <Label for="img">Link to image</Label>
	          <Input type="text" name="img" id="img" />
	        </FormGroup>

	        <Button>Submit</Button>
	      </Form>
      </Container>
    );
  }
}

export default CreateEvent;
