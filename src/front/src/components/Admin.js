import React, { Component } from 'react';
import AllEvents from './AllEvents';
import MyEvents from './MyEvents';
import CreateEvent from './CreateEvent';
import { Container, Row, Col, Button } from 'reactstrap';
import Layout from './Layout';
import con from "../config";
import axios from "axios/index";

class Admin extends Component {
    constructor(props) {
        super(props);
        this.state = { isAdmin: null, color1: "#4E729A", color2: "white", color3: "white", color4: "white", st: 1, log: "Loading ..."};
        this.select1 = this.select1.bind(this);
        this.select2 = this.select2.bind(this);
        this.select3 = this.select3.bind(this);
        this.select4 = this.select4.bind(this);
        this.setLogByCategory = this.setLogByCategory.bind(this);
    }

    setLogByCategory(category) {
        let self = this;
        axios(con.addr + '/mainServices/log/admin', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
                categories: category
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                // return response.data;
                // alert(response.data);
                self.setState({log: response.data});
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    componentWillMount(){
        let self = this;
        axios(con.addr + '/mainServices/log/checkAdmin', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                // return response.data;
                // alert(response.data);
                console.log(response.data);
                self.setState({isAdmin: true});
                self.select1();
            })
            .catch(function (error) {
                console.log(error);
                self.setState({isAdmin: false});
            });
    }

    componentDidUpdate() {
        if (this.state.isAdmin) {
            let elem = document.getElementById('logs');
            elem.scrollTop = elem.scrollHeight;
        }
    }

    select1() {
        this.setState({log: "Loading ..."});
        this.setState({ color1: "#4E729A", color2: "white", color3: "white", color4: 'white', st: 1 })
        this.setLogByCategory('api');
    }

    select2() {
        this.setState({log: "Loading ..."});
        this.setState({ color1: "white", color2: "#4E729A", color3: "white", color4: 'white', st: 2 })
        this.setLogByCategory('db');
    }

    select3() {
        this.setState({log: "Loading ..."});
        this.setState({ color1: "white", color2: "white", color3: "#4E729A", color4: 'white', st: 3 })
        this.setLogByCategory('chat');
    }

    select4() {
        this.setState({log: "Loading ..."});
        this.setState({ color1: "white", color2: "white", color3: "white", color4: '#4E729A', st: 4 })
        this.setLogByCategory('util');
    }

    render() {
        let self = this;
        if (this.state.isAdmin == null){
            return <h1>Loading ...</h1>;
        } else if (this.state.isAdmin) {
            if (this.state.st == 1) {
                return (
                    <div>
                        <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                        <Container className="table">
                            <Row>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                            onClick={this.select1}><h4>API</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                            onClick={this.select2}><h4>DB</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                            onClick={this.select3}><h4>CHAT</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color4}}
                                            onClick={this.select4}><h4>UTIL</h4></Button>
                                </Col>
                            </Row>
                            <pre id="logs">{this.state.log}</pre>
                        </Container>
                    </div>
                );
            }
            if (this.state.st == 2) {
                return (
                    <div>
                        <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                        <Container className="table">
                            <Row>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                            onClick={this.select1}><h4>API</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                            onClick={this.select2}><h4>DB</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                            onClick={this.select3}><h4>CHAT</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color4}}
                                            onClick={this.select4}><h4>UTIL</h4></Button>
                                </Col>
                            </Row>
                            <pre id="logs">{this.state.log}</pre>
                        </Container>
                    </div>
                );
            }
            if (this.state.st == 3) {
                return (
                    <div>
                        <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                        <Container className="table">
                            <Row>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                            onClick={this.select1}><h4>API</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                            onClick={this.select2}><h4>DB</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                            onClick={this.select3}><h4>CHAT</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color4}}
                                            onClick={this.select4}><h4>UTIL</h4></Button>
                                </Col>
                            </Row>
                            <pre id="logs">{this.state.log}</pre>
                        </Container>
                    </div>
                );
            }
            if (this.state.st == 4) {
                return (
                    <div>
                        <Layout id="dashboard" auth={true} logout={self.props.logout}/>
                        <Container className="table">
                            <Row>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color1}}
                                            onClick={this.select1}><h4>API</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color2}}
                                            onClick={this.select2}><h4>DB</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color3}}
                                            onClick={this.select3}><h4>CHAT</h4></Button>
                                </Col>
                                <Col sm="3">
                                    <Button color="primary" style={{width: "100%", background: this.state.color4}}
                                            onClick={this.select4}><h4>UTIL</h4></Button>
                                </Col>
                            </Row>
                            <pre id="logs">{this.state.log}</pre>
                        </Container>
                    </div>
                );
            }
        } else {
            return <h2>You are not authorized to access this page</h2>;
        }
    }
}

export default Admin;