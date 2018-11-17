import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';
import Layout from './Layout';
import EventsLayout from './EventsLayout';


class Group extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        var self = this;
        return (
            <div >
                <Layout id="groupPage" auth = {true} />
                <Row>
                    <EventsLayout />
                    <Container id = "groupPage" style={{width: "78%"}}>
                        <Row><h1 style={{color: "#2B587A"}}>{this.props.name}</h1> <Button style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "#4E729A"}}>Join</Button> <Button style={{ marginLeft:"10px", height:"90%", marginTop: "10px", backgroundColor: "#4E729A"}}>Chat</Button></Row>
                        <Row><img src = {this.props.img} style={{ height:"200px", width:"322px", border:"1px groove black" }} /></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Date: {this.props.date}</b></p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Time: {this.props.time}</b></p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Location:</b> {this.props.location}</p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Price:</b> {this.props.price} tenge</p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Points:</b> {this.props.points}</p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Description:</b> {this.props.description}</p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Admin:</b> {this.props.admin}</p></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Participants:</b></p></Row>
                        <Row><ul>
                            {self.props.participants.map(function(name,index){
                                return <li>{name}</li>;
                            })}
                        </ul></Row>
                        <Row><p><b style={{color: "#2B587A"}}>Number of participants:</b> {this.props.participants.length}/{this.props.maxsize}</p></Row>
                    </Container>
                </Row>
            </div>
        );
    }
}

export default Group;