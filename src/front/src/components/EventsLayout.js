import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';
import {
    Link
} from 'react-router-dom';
import con from '../config';

class EventsLayout extends Component {
    constructor(props) {
        super(props);
        console.log("  ---- " + this.props.selected);
    }

    render() {
        let self = this;
        return (
            <div style={{ marginLeft: "40px", width:"200px", paddingTop: "30px", color: "#4E729A", fontWeight: "bold"}}>
                <Row style={self.props.selected === 'myaccount' ? {marginBottom: "10px", backgroundColor: "silver"} : {marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/user/" + localStorage.getItem('userId')}>My Account</Link>
                </Row>
                <Row style={self.props.selected === 'allevents' ? {marginBottom: "10px", backgroundColor: "silver"} : {marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/allevents"}>All Events</Link>
                </Row>
                <Row style={self.props.selected === 'myevents' ? {marginBottom: "10px", backgroundColor: "silver"} : {marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/myevents"}>My Events</Link>
                </Row>
                <Row style={self.props.selected === 'mycompletedevents' ? {marginBottom: "10px", backgroundColor: "silver"} : {marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/mycompletedevents"}>My Completed Events</Link>
                </Row>
                <Row style={self.props.selected === 'createevent' ? {marginBottom: "10px", backgroundColor: "silver"} : {marginBottom: "10px"}}>
                    <Link className="layout_link text-center" to={con.projectName + "/createevent"}>Create Event</Link>
                </Row>
            </div>
        );
    }
}

export default EventsLayout;