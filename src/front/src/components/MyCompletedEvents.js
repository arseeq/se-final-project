import React, {Component} from 'react';
import Event from './Event';
import {Container, Row, Col, Button} from 'reactstrap';
import con from "../config";
import axios from "axios/index";
import Layout from './Layout';
import EventsLayout from './EventsLayout';
import CompletedEvent from './CompletedEvent';

class MyCompletedEvents extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            list: []
        };
    }

    componentWillMount() {
        let self = this;
        axios(con.addr + '/mainServices/event/getmypassiveevents', {
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
                    <EventsLayout selected="mycompletedevents" />
                    <Container className="border rounded" style={{width: "78%"}}>
                        <Row style={{width: "100%"}}>
                            {
                                self.state.list.map((item, index) => {
                                    return (
                                        <Col md="4" key={item.id}>
                                            <CompletedEvent name={item.name} id={item.id}
                                                            description={item.description} meetingdate={item.meetingdate}
                                                            img={"https://previews.123rf.com/images/kchung/kchung1403/kchung140300771/27140587-%EC%8A%A4%ED%83%AC%ED%94%84-%ED%9D%B0%EC%83%89-%EB%B0%B0%EA%B2%BD-%EC%9C%84%EC%97%90-%EB%B9%A8%EA%B0%84%EC%83%89-%ED%85%8D%EC%8A%A4%ED%8A%B8%EB%A1%9C-%EC%99%84%EB%A3%8C.jpg"}
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

export default MyCompletedEvents;