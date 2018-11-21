import React, {Component} from 'react';
import AllEvents from './AllEvents';
import MyEvents from './MyEvents';
import CreateEvent from './CreateEvent';
import Layout from './Layout';
import {Container, Row, Col, Button} from 'reactstrap';

class Groups extends Component {
    constructor(props) {
        super(props);
        this.state = {color1: "#39CCCC", color2: "#7FDBFF", color3: "#7FDBFF", st: 1};
        this.select1 = this.select1.bind(this);
        this.select2 = this.select2.bind(this);
        this.select3 = this.select3.bind(this);
    }

    select1() {
        this.setState({color1: "#39CCCC", color2: "#7FDBFF", color3: "#7FDBFF", st: 1})
    }

    select2() {
        this.setState({color1: "#7FDBFF", color2: "#39CCCC", color3: "#7FDBFF", st: 2})
    }

    select3() {
        this.setState({color1: "#7FDBFF", color2: "#7FDBFF", color3: "#39CCCC", st: 3})
    }

    render() {
        let self = this;
        if (this.state.st === 1) {
            return (
                <div>
                    <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                    <Container className="table">
                        <Row>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                        onClick={this.select1}><h4>All events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                        onClick={this.select2}><h4>My events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                        onClick={this.select3}><h4>Create Event Group</h4></Button>
                            </Col>
                        </Row>
                        <AllEvents/>
                    </Container>
                </div>
            );
        }
        if (this.state.st === 2) {
            return (
                <div>
                    <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                    <Container className="table">
                        <Row>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                        onClick={this.select1}><h4>All events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                        onClick={this.select2}><h4>My events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                        onClick={this.select3}><h4>Create Event Group</h4></Button>
                            </Col>
                        </Row>
                        <MyEvents/>
                    </Container>
                </div>
            );
        }
        if (this.state.st === 3) {
            return (
                <div>
                    <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                    <Container className="table">
                        <Row>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                        onClick={this.select1}><h4>All events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                        onClick={this.select2}><h4>My events</h4></Button>
                            </Col>
                            <Col sm="4">
                                <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                        onClick={this.select3}><h4>Create Event Group</h4></Button>
                            </Col>
                        </Row>
                        <CreateEvent/>
                    </Container>
                </div>
            );
        }
    }
}

export default Groups;
