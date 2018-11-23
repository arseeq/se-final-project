import React, {Component} from 'react';
import Event from './Event';
import {Container, Row, Col, Button} from 'reactstrap';
import con from "../config";
import axios from "axios/index";
import Layout from './Layout';
import EventsLayout from './EventsLayout';

class MyEvents extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            list: []
        };
    }

    componentWillMount() {
        let self = this;
        axios(con.addr + '/mainServices/event/getmyactiveevents', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log("my List:");
                console.log(response.data);
                self.setState({list: response.data});
            })
            .catch(function (error) {
                console.log(error);
                self.setState({authorized: false});
            });
    }

    removeEvent(ind){
        let self = this;
        this.state.list.splice(ind, 1);
        this.setState({list: self.state.list});
    }

    render() {
        let self = this;
        return (
            <div>
                <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                <Row style={{margin: "0"}}>
                    <EventsLayout selected="myevents" />
                    <Container className="border rounded" style={{width: "78%"}}>
                        <Row style={{width: "100%"}}>
                            {
                                self.state.list.map((item, index) => {
                                    return (
                                        <Col md="4" key={item.id}>
                                            <Event name={item.name} id={item.id}
                                                   description={item.description} meetingdate={item.meetingdate}
                                                   img={item.img}
                                                   ind={index}
                                                   removeEvent={self.removeEvent.bind(self)}
                                            />
                                        </Col>
                                    )
                                })
                            }
                        </Row>
                    </Container>
                </Row>
            </div>
        );
    }
}

export default MyEvents;