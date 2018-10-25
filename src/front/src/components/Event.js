import React, { Component } from 'react';
import { Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button } from 'reactstrap';

class Event extends Component {
    constructor(props) {
      super(props)
    }

  render() {
    return (
      <Card style={{margin: "10px"}}>
        <CardImg width="100%" height="200px" src={this.props.img} alt="Card image cap" />
        <CardBody>
          <CardTitle>{this.props.title}</CardTitle>
          <CardSubtitle>{this.props.date}</CardSubtitle>
          <CardText>{this.props.descr}</CardText>
          <Button>See details</Button>
        </CardBody>
      </Card>
    );
  }
};

export default Event;