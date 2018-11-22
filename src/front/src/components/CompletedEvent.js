import React, {Component} from 'react';
import {Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button} from 'reactstrap';
import con from "../config";
import {Link} from 'react-router-dom';
import axios from "axios/index"

class CompletedEvent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let self = this;
        return (
            <Card style={{margin: "10px"}}>
                <CardImg width="100%" height="200px"
                         src={(this.props.img)}
                         alt="Card image cap"/>
                <CardBody>
                    <CardTitle>{this.props.name}</CardTitle>
                    <CardSubtitle style={{marginBottom: "20px"}}>{this.props.meetingdate}</CardSubtitle>
                    <Link to={con.projectName + '/group/' + self.props.id}><Button style={{borderColor: "black" ,marginRight: "20px", marginTop: "3px", marginBottom: "7px", backgroundColor: "#4E729A"}}>Details</Button></Link>
                </CardBody>
            </Card>
        );
    }
}

export default CompletedEvent;