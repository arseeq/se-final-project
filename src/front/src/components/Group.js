import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';
import Layout from './Layout';
import EventsLayout from './EventsLayout';
import axios from 'axios';
import con from '../config';
import {withRouter} from "react-router-dom";

class Group extends Component {
    constructor(props) {
        super(props);
        this.state = {
            participants: [],
            ready: false
        };

    }

    componentWillMount(){
        let self = this;
        let { match: { params } } = this.props;
        axios(con.addr + '/mainServices/event/getEventById', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
                id: params.id.toString()
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState(response.data);
                self.setState({ready: true});
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        let self = this;
        if (self.state.ready)
            return (
                <div >
                    <Layout id="groupPage" auth = {true} />
                    <Row>
                        <EventsLayout />
                        <Container id = "groupPage" style={{width: "78%"}}>
                            <Row><h1 style={{color: "#2B587A"}}>{this.state.name}</h1> <Button style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "#4E729A"}}>Join</Button> <Button style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "#4E729A"}}>Chat</Button></Row>
                            <Row><img src = { (this.state.img) ? this.state.img : "https://4dane94f01emxbo733yxewhi-wpengine.netdna-ssl.com/wp-content/uploads/2017/07/tradeshows_comprehensive-event-mgmt.gif"} style={{ height:"200px", width:"322px", border:"1px groove black" }} /></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Date:</b> {this.state.meetingdate}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Location:</b> {this.state.location}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Points:</b> {this.state.points}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Description:</b> {this.state.description}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Admin:</b> {this.state.admin}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Participants:</b></p></Row>
                            <Row><ul>
                                {self.state.participants.map(function(participant,index){
                                    return <li key={index}>{participant.email}</li>;
                                })}
                            </ul></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Number of participants:</b> {this.state.participants.length}/{this.state.maxsize}</p></Row>
                        </Container>
                    </Row>
                </div>
            );
        else
            return(
                <h1>Loading ....</h1>
            )
    }
}

export default withRouter(Group);