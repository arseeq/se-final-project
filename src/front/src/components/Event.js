import React, {Component} from 'react';
import {Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle, Button} from 'reactstrap';
import con from "../config";
import {Link} from 'react-router-dom';
import axios from "axios/index";

class Event extends Component {
    constructor(props) {
        super(props);
        this.leave = this.leave.bind(this);
    }

    leave(e){
        let self = this;
        axios(con.addr + '/mainServices/event/leave', {
            method: "POST",
            data: JSON.stringify({
                id: self.props.id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                // self.state.listt[e.target.name].amIParticipant = false;
                self.props.removeEvent(self.props.ind);
            })
            .catch(function (error) {
                alert(error.response.data);
                console.log(error);
            });

    }

    render() {
        let self = this;
        return (

            <Card style={{margin: "10px"}}>
                <CardImg width="100%" height="200px"
                         src={(this.props.img) ? this.props.img : "https://4dane94f01emxbo733yxewhi-wpengine.netdna-ssl.com/wp-content/uploads/2017/07/tradeshows_comprehensive-event-mgmt.gif"}
                         alt="Card image cap"/>
                <CardBody>
                    <CardTitle>{this.props.name}</CardTitle>
                    <CardSubtitle style={{marginBottom: "20px"}}>{this.props.meetingdate}</CardSubtitle>
                    <Link  to={con.projectName + '/chat/' + self.props.id}><Button style={{borderColor: "black" ,marginRight: "20px", marginTop: "3px", marginBottom: "7px", backgroundColor: "white", color: "black"}}>Chat</Button></Link>
                    <Link to={con.projectName + '/group/' + self.props.id}><Button style={{borderColor: "black" ,marginRight: "20px", marginTop: "3px", marginBottom: "7px", backgroundColor: "#4E729A"}}>Details</Button></Link>
                    <Button style={{borderColor: "black", marginTop: "3px", marginBottom: "7px", backgroundColor: "red"}} onClick={self.leave}>Leave</Button>
                </CardBody>
            </Card>
        );
    }
}

export default Event;