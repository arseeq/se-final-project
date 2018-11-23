import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';
import Layout from './Layout';
import EventsLayout from './EventsLayout';
import axios from 'axios';
import con from '../config';
import {withRouter, Link} from "react-router-dom";

class Group extends Component {
    constructor(props) {
        super(props);
        this.state = {
            participants: [],
            ready: false
        };
        this.getData = this.getData.bind(this);
        this.containsMe = this.containsMe.bind(this);
    }

    getData(){
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
                self.containsMe();
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    componentWillMount(){
        this.getData();
    }

    join() {
        let self = this;
        axios(con.addr + '/mainServices/event/join', {
            method: "POST",
            data: JSON.stringify({
                id: self.state.id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.getData();
            })
            .catch(function (error) {
                console.log(error.response);
                // self.setState({authorized: false});
                alert(error.response.data);
            });
    }

    leave() {
        let self = this;
        axios(con.addr + '/mainServices/event/leave', {
            method: "POST",
            data: JSON.stringify({
                id: self.state.id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.getData();
            })
            .catch(function (error) {
                console.log(error.response.data);
                alert(error.response.data);
            });
    }

    complete() {
        let self = this;
        axios(con.addr + '/mainServices/event/completeEvent', {
            method: "POST",
            data: JSON.stringify({
                id: self.state.id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.getData();
            })
            .catch(function (error) {
                console.log(error.response.data);
                alert(error.response.data);
            });
    }

    containsMe() {
        let self = this;
        let found = false;
        this.state.participants.forEach((participant) => {
            if (participant.id.toString() === localStorage.getItem('userId').toString()){
                found = true;
                self.setState({containsMe: true});
            }
        });
        if (!found)
            self.setState({containsMe: false});
    }

    render() {
        let self = this;
        if (self.state.ready)
            return (
                <div >
                    <Layout id="groupPage" auth = {true} />
                    <Row>
                        <EventsLayout selected="" />
                        <Container id = "groupPage" style={{width: "78%"}}>
                            <Row><h1 style={{color: "#2B587A"}}>{this.state.name}</h1> <Button hidden={self.state.containsMe || self.state.isCompleted}
                                                                                               onClick={self.join.bind(self)}
                                                                                               style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "green", borderColor: "black"}}>Join</Button>
                                <Button hidden={!self.state.containsMe}
                                        onClick={self.leave.bind(self)}
                                        style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "red", borderColor: "black"}}>Leave</Button>
                                <Link hidden={!self.state.containsMe} to={con.projectName + '/chat/' + self.state.id} style={{marginLeft:"10px", color: "white", marginTop: "10px", height:"90%"}}><Button style={{    backgroundColor: "#4E729A"}}>Chat</Button></Link>
                                <Button hidden={self.state.admin.toLowerCase() !== localStorage.getItem('email').toLowerCase() || self.state.isCompleted}
                                        onClick={self.complete.bind(self)}
                                        style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "orange", borderColor: "black"}}>Completed</Button>
                            </Row>
                            <Row><img src = { (this.state.img) ? this.state.img : "https://4dane94f01emxbo733yxewhi-wpengine.netdna-ssl.com/wp-content/uploads/2017/07/tradeshows_comprehensive-event-mgmt.gif"} style={{ height:"200px", width:"322px", border:"1px groove black" }} /></Row>
                            <Row hidden={!self.state.isCompleted}><p><b style={{color: "#2B587A"}}>Completed</b></p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Date:</b> {this.state.meetingdate}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Location:</b> {this.state.location}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Points:</b> {this.state.points}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Description:</b> {this.state.description}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Admin:</b> {this.state.admin}</p></Row>
                            <Row><p><b style={{color: "#2B587A"}}>Participants:</b></p></Row>
                            <Row><ul>
                                {self.state.participants.map(function(participant,index){
                                    return <li key={index}><Link to={con.projectName + "/user/" + participant.id}>{participant.email}</Link></li>;
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