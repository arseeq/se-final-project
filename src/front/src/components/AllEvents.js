import React, {Component} from 'react';
import Event from './Event';
import {Container, Row, Col, Table, Button} from 'reactstrap';
import con from "../config";
import axios from "axios/index";
import Layout from './Layout';
import EventsLayout from './EventsLayout';

class AllEvents extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            list: []
        };
        this.join = this.join.bind(this);
    }

    join(e) {
        let self = this;
        axios(con.addr + '/mainServices/event/join', {
            method: "POST",
            data: JSON.stringify({
                id: e.target.name,
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                // self.setState({list: response.data});
                e.disabled = true;
            })
            .catch(function (error) {
                console.log(error);
                // self.setState({authorized: false});
            });
    }

    componentWillMount() {
        let self = this;
        axios(con.addr + '/mainServices/event/getallevents', {
            method: "GET",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState({list: response.data});
            })
            .catch(function (error) {
                console.log(error);
                self.setState({authorized: false});
            });
    }

    render() {
        let self = this;
        return (
            <div>
                <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                <Row style={{margin: "0"}}>
                    <EventsLayout />
                    <div className="border rounded" style={{marginLeft: "5%"}}>
                        <Table hover>
                            <thead>
                            <tr>
                                <th>Group #</th>
                                <th>Group Name</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Location</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                self.state.list.map((item, index) => {
                                    return (
                                        <tr key={item.id}>
                                            <th scope="row">{index}</th>
                                            <td>{item.name}</td>
                                            <td>{item.description}</td>
                                            <td>{item.meetingdate}</td>
                                            <td>{item.location}</td>
                                            <td>{item.price}</td>
                                            <td>{item.points}</td>
                                            <td>{item.maxsize}</td>
                                            <td>
                                                <Button color="danger">details</Button>{' '}
                                                <Button disabled={item.admin === localStorage.getItem('email')} color="success"
                                                        onClick={self.join} name={item.id}>join</Button>{' '}
                                            </td>
                                        </tr>
                                    )
                                })
                            }
                            </tbody>
                        </Table>
                        <Row className="paging">
                            <Col md={{offset: 10}}>
                                <Button color="primary">previous</Button>{' '}
                                <Button color="info">next</Button>{' '}
                            </Col>
                        </Row>
                    </div>
                </Row>
            </div>
        );
    }
}

export default AllEvents;
