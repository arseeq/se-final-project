import React, {Component} from 'react';
import {Container, Row, Col, Button, Table} from 'reactstrap';
import Layout from './Layout';
import EventsLayout from './EventsLayout';
import Event from './Event';
import {withRouter, Link} from "react-router-dom";
import axios from "axios/index";
import con from "../config";


class User extends Component {
    constructor(props) {
        super(props);
        let { match: { params } } = this.props;
        this.state = {
            ready: false,
            id: params.id.toString()
        };
    }

    componentWillMount(){
        let self = this;
        let { match: { params } } = this.props;
        axios(con.addr + '/mainServices/user/getUserInfoById', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
                id: params.id
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState(response.data);
                axios(con.addr + '/mainServices/event/getUserEvents', {
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
                        self.setState({events: response.data});
                        self.setState({ready: true});
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        let self = this;
        if (this.state.ready)
            return (
                <div >
                    <Layout id="groupPage" auth = {true} />
                    <Row>
                        <EventsLayout selected={self.state.id.toString() === localStorage.getItem('userId').toString() ? "myaccount" : ""}/>
                        <Container id = "groupPage" style={{width: "78%"}}>
                            <h2>{self.state.name} {self.state.surname}</h2>
                            <hr/>
                            <p style={{color: "#2a5885"}}><span style={{color: "#828282"}}>Email:</span>{self.state.email}</p>
                            <p style={{color: "#2a5885"}}><span style={{color: "#828282"}}>Score:</span>{self.state.score}</p>
                            <hr/>
                            <div style={{border: "1px solid #2a5885", padding: "10px", borderRadius: "5px"}}>
                                <p style={{color: "#2a5885", fontWeight: "bold"}}>Events</p>
                                <hr/>
                                <Row style={{width: "100%"}}>
                                    <div className="border rounded" style={{marginLeft: "5%"}}>
                                        <Table hover>
                                            <thead>
                                            <tr>
                                                <th>Group #</th>
                                                <th>Group Name</th>
                                                <th>Time</th>
                                                <th>Location</th>
                                                <th>Price</th>
                                                <th>Points</th>
                                                <th>Capacity</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {
                                                self.state.events.map((item, index) => {
                                                    return (
                                                        <tr key={index} style={ item.isCompleted ? {backgroundColor: "SILVER"} : {} }>
                                                            <th scope="row">{index}</th>
                                                            <td>{item.name}</td>
                                                            <td>{item.meetingdate}</td>
                                                            <td>{item.location}</td>
                                                            <td>{item.price}</td>
                                                            <td>{item.points}</td>
                                                            <td>{item.maxsize}</td>
                                                            <td>
                                                                <Link style={{color: "white", marginBottom: "7px"}} to={con.projectName + '/group/' + item.id}><Button style={{backgroundColor: "#4E729A"}}>Details</Button></Link>
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
                        </Container>
                    </Row>
                </div>
            );
        else
            return <h1>Loading ...</h1>;
    }
}

export default withRouter(User);