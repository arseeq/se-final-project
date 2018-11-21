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
                <CardImg width="100%" height="200px"
                         src={(this.props.img) ? this.props.img : "https://4dane94f01emxbo733yxewhi-wpengine.netdna-ssl.com/wp-content/uploads/2017/07/tradeshows_comprehensive-event-mgmt.gif"}
                         alt="Card image cap"/>
                <CardBody>
                    <CardTitle>{this.props.name}</CardTitle>
                    <CardSubtitle style={{marginBottom: "20px"}}>{this.props.meetingdate}</CardSubtitle>
                    <Link style={{
                        border: "1px solid #2a5885",
                        padding: "6px 30px 6px 30px",
                        borderRadius: "5px",
                        marginRight: "78px",
                        color: "#4E729A",
                        fontWeight: "bold"
                    }} to={con.projectName + '/chat/' + self.props.id}>Chat</Link>
                    <Button style={{marginBottom: "7px", backgroundColor: "#4E729A"}}><Link style={{color: "white"}} to={con.projectName + '/group/' + self.props.id}>Details</Link></Button>
                </CardBody>
            </Card>
        );
    }
}

export default Event;