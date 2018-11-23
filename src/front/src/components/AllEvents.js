import React, {Component} from 'react';
import Event from './Event';
import {Container, Row, Col, Table, Button} from 'reactstrap';
import con from "../config";
import axios from "axios/index";
import Layout from './Layout';
import EventsLayout from './EventsLayout';
import {Link} from 'react-router-dom';

class AllEvents extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            listt: []
        };
        this.join = this.join.bind(this);
        this.leave = this.leave.bind(this);
    }

    join(e) {
        let self = this;
        let ind = e.target.name;
        console.log(JSON.stringify({
            id: self.state.listt[e.target.name].id,
            token: localStorage.getItem('token')
        }));
        axios(con.addr + '/mainServices/event/join', {
            method: "POST",
            data: JSON.stringify({
                id: self.state.listt[ind].id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.state.listt[ind].amIParticipant = true;
                self.setState({listt: self.state.listt});
            })
            .catch(function (error) {
                console.log(error.response);
                // self.setState({authorized: false});
                alert(error.response.data);
            });
    }

    leave(e) {
        let self = this;
        let ind = e.target.name;
        axios(con.addr + '/mainServices/event/leave', {
            method: "POST",
            data: JSON.stringify({
                id: self.state.listt[ind].id.toString(),
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                // self.state.listt[e.target.name].amIParticipant = false;
                self.state.listt[ind].amIParticipant = false;
                console.log(self.state.listt[ind]);
                self.setState({listt: self.state.listt});
            })
            .catch(function (error) {
                console.log(error.response.data);
                alert(error.response.data);
            });
    }

    componentWillMount() {
        let self = this;
        axios(con.addr + '/mainServices/event/getallevents', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState({listt: response.data});
            })
            .catch(function (error) {
                console.log(error.response);
                self.setState({authorized: false});
            });
    }

    render() {
        let self = this;
        return (
            <div>
                <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                <Row style={{margin: "0"}}>
                    <EventsLayout selected="allevents"/>
                    <div className="border rounded" style={{marginLeft: "5%"}}>
                        <Table hover>
                            <thead>
                            <tr>
                                <th>Group #</th>
                                <th>Group Name</th>
                                <th>Date & Time</th>
                                <th>Location</th>
                                <th>Price</th>
                                <th>Points</th>
                                <th>Capacity</th>
                                <th>current size</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                self.state.listt.map((item, index) => {
                                    return (
                                        <tr key={item.id}>
                                            <th scope="row">{index}</th>
                                            <td>{item.name}</td>
                                            <td>{item.meetingdate}</td>
                                            <td>{item.location}</td>
                                            <td>{item.price}</td>
                                            <td>{item.points}</td>
                                            <td>{item.maxsize}</td>
                                            <td>{item.currentSize}</td>
                                            <td>
                                                <Link style={{marginRight: "5px", marginBottom: "7px"}} to={con.projectName + '/group/' + item.id}><Button style={{borderColor: "black", color: "white", backgroundColor: "#4E729A"}}>Details</Button></Link>
                                                <Button hidden={item.amIParticipant}
                                                        onClick={self.join} name={index}
                                                        style={{backgroundColor: "green", borderColor: "black"}}
                                                >join</Button>
                                                <Button hidden={!item.amIParticipant}
                                                        onClick={self.leave} name={index}
                                                        style={{backgroundColor: "red", borderColor: "black"}}
                                                >leave</Button>
                                            </td>
                                        </tr>
                                    )
                                })
                            }
                            </tbody>
                        </Table>
                    </div>
                </Row>
            </div>
        );
    }
}

export default AllEvents;
