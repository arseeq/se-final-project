import React, {Component} from 'react';
import {Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button} from 'reactstrap';
import con from "../config";
import {Link} from 'react-router-dom';

class Event extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        let self = this;
        return (
            <Card style={{margin: "10px"}}>
                <CardImg width="100%" height="200px" src={this.props.img} alt="Card image cap"/>
                <CardBody>
                    <CardTitle>{this.props.name}</CardTitle>
                    <CardSubtitle>{this.props.meetingdate}</CardSubtitle>
                    <CardText>{this.props.description}</CardText>
                    <Link to={con.projectName + '/chat/' + self.props.id}>Chat</Link>
                    <Button>See details</Button>
                </CardBody>
            </Card>
        );
    }
}

export default Event;