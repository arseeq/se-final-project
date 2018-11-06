import React, { Component } from 'react';
import { Button } from 'reactstrap';
import Layout from './Layout';
import con from '../config';

class Chat extends Component {
    constructor(props) {
        super(props);
        this.state = {
            socket: null
        }
    }

    componentWillMount(){
        this.setState({socket: new WebSocket(con.sockethost)});
    }

    render() {
        var self = this;
        return (
            <div>
                <Layout id = "dashboard" auth = {true} logout = {self.props.logout} />
            </div>
        );
    }
}

export default Chat;
