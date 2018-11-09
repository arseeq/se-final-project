import React, {Component} from 'react';
import {Container, Row, Col, Button} from 'reactstrap';


class Message extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let self = this;
        return (
            <div style={{border: "1px solid #909090", borderRadius: "10px", width: "60%", paddingLeft: "10px", paddingRight: "10px", backgroundColor: this.props.color, display: "inline-block"}}>
                <p className="text-left" style={{marginBottom: 0, color: "#4E729A", fontWeight: "bold"}}>{this.props.author}</p>
                <p className="text-left" style={{marginBottom: 0, paddingLeft: "10px"}}>{this.props.message}</p>
                <p className="text-right" style={{marginBottom: 0, color: "#777777"}}>{this.props.time}</p>
            </div>
        );
    }
}

export default Message;
