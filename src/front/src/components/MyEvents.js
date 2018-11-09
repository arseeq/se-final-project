import React, {Component} from 'react';
import Event from './Event';
import {Container, Row, Col, Button} from 'reactstrap';
import con from "../config";
import axios from "axios/index";

class MyEvents extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 1,
            list: []
        };
    }

    componentWillMount() {
        let self = this;
        axios(con.addr + '/mainServices/event/getmyevents', {
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
                console.log(response.data[0]);
                self.setState({list: response.data});
            })
            .catch(function (error) {
                console.log(error);
                self.setState({authorized: false});
            });
    }

    render() {
        let self = this;
        return (
            <Row className="border rounded">
                <Row style={{width: "100%"}}>
                    {
                        self.state.list.map((item, index) => {
                            return (
                                <Col md="4" key={item.id}>
                                    <Event name={item.name} id={item.id}
                                           description={item.description} meetingdate={item.meetingdate}
                                           img="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkEpDkrqjSyqbwBci92SQoZxyNR7eKqoL8b8CBuBJqjsvkkFXgMA"
                                           />
                                </Col>
                            )
                        })
                    }
                </Row>
                <Row className="paging">
                    <Col md={{offset: 10}}>
                        <Button color="primary">previous</Button>{' '}
                        <Button color="info">next</Button>{' '}
                    </Col>
                </Row>
            </Row>
        );
    }
}

export default MyEvents;
