import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';
import {
    Link
} from 'react-router-dom';
import con from '../config';

class EventsLayout extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        var self = this;
        return (
            <div style={{ marginLeft: "40px", width:"200px", paddingTop: "30px", color: "#4E729A", fontWeight: "bold"}}>
                <Row style={{marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to="">My Account</Link>
                </Row>
                <Row style={{marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/allevents"}>All Events</Link>
                </Row>
                <Row style={{marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/myevents"}>My Events</Link>
                </Row>
                <Row style={{marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/createevent"}>Create Event</Link>
                </Row>
            </div>
        );
    }
}

export default EventsLayout;