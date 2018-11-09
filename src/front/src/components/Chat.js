import React, { Component } from 'react';
import Layout from './Layout';
import Message from './Message';
import {Container, Row, Col, Button} from 'reactstrap';
import {withRouter} from 'react-router-dom';
import con from '../config';
import SocketMethods from '../modules/SocketMethods';

class Chat extends Component {
    constructor(props) {
        super(props);
        this.state = {
            msg: "",
            history: []
        };
        this.sendMsg = this.sendMsg.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }
    componentWillMount() {
        let { match: { params } } = this.props;
        let self = this;
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
        SocketMethods.sendMessage(this.props.socket, JSON.stringify({
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
        SocketMethods.sendMessage(this.props.socket, JSON.stringify(mySocketMsg));
        console.log("sent");
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
                <Container className="text-center" style={{border: "1px solid #909090", borderRadius: "5px", marginTop: "10px", marginBottom: "10px",
                    width:"70%"}}>
                    <Row style={{borderBottom: "1px solid #C3CBD4", backgroundColor: "#4E729A",
                        color: "white", paddingLeft:"420px", fontSize: "40px", fontFamily: "bold"}}>Chat</Row>
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
                    <Row style={{borderTop: "1px solid #C3CBD4", backgroundColor: "#F3F3F3", paddingTop: "10px", paddingBottom: "5px"}}>
                        <Col md="10">
                                <textarea onChange={self.handleChange} name="newMsg" style={{width: "90%", padding: "5px", borderRadius: "5px"}}
                                value={self.state.msg}/>
                        </Col>
                        <Button onClick={self.sendMsg} type="submit" style={{height: "45px", marginTop: "7px", backgroundColor: "#4E729A"}} >Send message</Button>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default withRouter(Chat);
