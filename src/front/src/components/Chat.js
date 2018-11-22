import React, { Component } from 'react';
import Layout from './Layout';
import Message from './Message';
import {Container, Row, Col, Button} from 'reactstrap';
import {withRouter} from 'react-router-dom';
import EventsLayout from './EventsLayout';
import con from "../config";
import axios from "axios/index";
// import SocketMethods from '../modules/SocketMethods';

function sendMessage(ws, msg){
    // Wait until the state of the socket is not ready and send the message when it is...
    waitForSocketConnection(ws, function(){
        console.log("message sent!!!");
        ws.send(msg);
    });
}


function waitForSocketConnection(socket, callback){
    let self = this;
    setTimeout(
        function () {
            if (socket.readyState === 1) {
                console.log("Connection is made");
                if(callback != null){
                    callback();
                }
                return;

            } else {
                console.log("wait for connection...");
                waitForSocketConnection(socket, callback);
            }
        }, 5); // wait 5 milisecond for the connection...
}

class Chat extends Component {
    constructor(props) {
        super(props);
        this.state = {
            msg: "",
            history: [],
            data: {
                name: ""
            }
        };
        this.sendMsg = this.sendMsg.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        let elem = document.getElementById('chatContainer');
        elem.scrollTop = elem.scrollHeight;
    }
    componentDidUpdate() {
        let elem = document.getElementById('chatContainer');
        elem.scrollTop = elem.scrollHeight;
    }
    componentWillMount() {
        let { match: { params } } = this.props;
        let self = this;

        axios(con.addr + '/mainServices/event/getEventById', {
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
                self.setState({data: response.data});
            })
            .catch(function (error) {
                console.log(error);
            });

        this.props.setOnMsg((msgEvent) => {
            let msg = JSON.parse(msgEvent.data);
            console.log("    -");
            //console.log(msg);
            if (msg.type === "history" && msg.eventId === parseInt(params.id) && msg.data.length > 0){
                // console.log(msg.data[0].author.email);
                self.setState({history: msg.data});
                // console.log(self.state.history);
            } else if (msg.type === "message" && msg.eventId === parseInt(params.id)){
                console.log(msg.data[0]);
                // self.state.history.push(msg.data[0]);
                self.setState({history: self.state.history.concat(msg.data)});
            }
        });
        sendMessage(this.props.socket, JSON.stringify({
            type: "history",
            token: localStorage.getItem('token'),
            eventId: params.id
        }));
    }

    sendMsg(){
        let self = this;
        let { match: { params } } = this.props;
        let mySocketMsg = {
            type: "message",
            token: localStorage.getItem('token'),
            eventId: params.id,
            msg: self.state.msg
        };
        sendMessage(this.props.socket, JSON.stringify(mySocketMsg));
        this.setState({msg: ""});
        console.log("sent");
    }
    sendMsg2(event) {
        let self = this;
        if(event.keyCode == 13) {
            //alert(self.state.msg.length);
                let { match: { params } } = this.props;
                let mySocketMsg = {
                    type: "message",
                    token: localStorage.getItem('token'),
                    eventId: params.id,
                    msg: self.state.msg
                };
                sendMessage(this.props.socket, JSON.stringify(mySocketMsg));
                this.setState({msg: ""});
                console.log("sent");
        }
    }
    handleChange(e){
        switch(e.target.name) {
            case("newMsg"):
                this.setState({msg: e.target.value});
                break;
            default:
        }
    }

    render() {
        let self = this;
        return (
            <div>
                <Layout id = "dashboard" auth = {true} logout = {self.props.logout} />
                <Row style={{margin:"0"}}>
                    <EventsLayout selected="" />
                    <Container style={{border: "1px solid #909090", borderRadius: "5px", padding: "0px", width: "100%"}}>
                        <Row style={{borderBottom: "1px solid #C3CBD4", backgroundColor: "#4E729A",
                            color: "white", paddingLeft:"420px", fontSize: "40px", fontFamily: "bold", width:"100%", margin: "0px"}}>{self.state.data.name}</Row>
                        <Container id="chatContainer" className="text-center">
                            {
                                self.state.history.map((item) => {
                                    // console.log(item);
                                    return (
                                        <div key={item.id} className={item.author.email === self.props.user ? "text-right" : "text-left"} style={{margin: "10px"}}>
                                            <Message color={item.author.email === self.props.user ? "#edf0f5" : "white"} author={item.author.email} message={item.msg} time={item.date}/>
                                        </div>
                                    )
                                })
                            }
                        </Container>
                        <Row style={{borderTop: "1px solid #C3CBD4", backgroundColor: "#F3F3F3", paddingTop: "10px", paddingBottom: "5px", margin: "0px"}}>
                            <Col md="10">
                                    <textarea onKeyUp={self.sendMsg2.bind(self)} onChange={self.handleChange} name="newMsg" style={{width: "90%", padding: "5px", borderRadius: "5px"}}
                                              value={self.state.msg}/>
                            </Col>
                            <Button disabled={self.state.msg === ""}  onClick={self.sendMsg} type="submit" style={{height: "45px", marginTop: "7px", backgroundColor: "#4E729A"}}>Send message</Button>
                        </Row>
                    </Container>
                </Row>
            </div>
        );
    }
}

export default withRouter(Chat);