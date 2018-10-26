import React, { Component } from 'react';
import Event from './Event';
import { Container, Row, Col, Button } from 'reactstrap';
import con from "../config";
import axios from "axios/index";

class MyEvents extends Component {
    constructor(props){
        super(props);
        this.state = {
            page: 1,
            list: [
                {
                    id: 1,
                    name: 'football',
                    meetingdate: '2018/11/11',
                    meetingtime: '16:16',
                    location: 'pole'
                }
            ]
        }
    }
    componentWillMount(){
        var self = this;
        axios(con.addr+'/mainServices/event/getmyevents', {
            method: "POST",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState({list: response.data});
                console.log(response.data);
            })
            .catch(function (error) {
                console.log(error);
                self.setState({authorized: false});
            });
    }
  render() {
    	var self =this;
    return (
	  	<Row className="border rounded">
	  		<Row style={{width:"100%"}}>
                {
                    self.state.list.map((item, index) => {
                        return (
                            <Col md="4" key = {item.id}>
                                <Event title={item.name} date={item.meetingdate+item.meetingtime} descr={item.description} img="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkEpDkrqjSyqbwBci92SQoZxyNR7eKqoL8b8CBuBJqjsvkkFXgMA"/>
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
