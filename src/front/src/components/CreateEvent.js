import React, {Component} from 'react';
import {Button, Form, FormGroup, Label, Input, FormText, Container, Row, Col} from 'reactstrap';
import con from "../config";
import axios from "axios/index";
import {AvForm, AvField, AvInput, AvGroup, AvLabel} from 'availity-reactstrap-validation';
import validator from '../modules/Validator'
import moment from 'moment';
import Layout from './Layout';
import EventsLayout from './EventsLayout';


class CreateEvent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            date: "",
            time: "",
            description: "",
            location: "",
            // category: "Other",
            price: 0,
            points: 0,
            img: "",
            msg: "",
            participants: "",
            errormsg: false,
            createmsg: false,
            datemsg: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.send = this.send.bind(this);
    }

    handleChange(e) {
        switch (e.target.name) {
            case("name"):
                this.setState({name: e.target.value});
                break;
            case("location"):
                this.setState({location: e.target.value});
                break;
            case("date"):
                console.log(" -----  "+e.target.value);
                this.setState({date: e.target.value});
                break;
            case("time"):
                console.log("***********"+e.target.value.toString().split(':').join('-'));
                this.setState({time: e.target.value});

                break;
            case("description"):
                this.setState({description: e.target.value});
                break;
            // case("category"):
            //     this.setState({category: e.target.value});
            //     break;
            case("price"):
                this.setState({price: e.target.value});
                break;
            case("points"):
                this.setState({points: e.target.value});
                break;
            case("img"):
                this.setState({img: e.target.value});
                break;
            case("participants"):
                this.setState({participants: e.target.value});
                break;
            default:
        }
    }

    // isFutureTime(t){
    //     let h = parseInt(t.split(':')[0]);
    //     let m = parseInt(t.split(':')[1]);
    //     let cur = new Date();
    //     alert(h +' '+ cur.getHours() +' '+ m +' '+ cur.getMinutes());
    //     return h > cur.getHours() || (h === cur.getHours() && m > cur.getMinutes());
    // }

    send() {
        let self = this;

        if (Date.parse(self.state.date+'T'+self.state.time) <= Date.now()) {
            //alert("1");
            self.setState({datemsg: true});
            self.setState({errormsg: false});
            self.setState({createmsg: false});
        } else {
            axios(con.addr + '/mainServices/event/create', {
                method: "POST",
                data: JSON.stringify({
                    name: self.state.name,
                    meetingdate: self.state.date + ' ' + self.state.time.toString().split(':').join('-'),
                    description: self.state.description,
                    location: self.state.location,
                    // category: self.state.category+'',
                    price: self.state.price + '',
                    points: self.state.points + '',
                    maxsize: self.state.participants + '',
                    img: self.state.img,
                    token: localStorage.getItem('token')
                }),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(function (response) {
                    console.log(response);
                    self.setState({name: ""});
                    self.setState({createmsg: true});
                    self.setState({datemsg: false});
                    self.setState({errormsg: false});
                })
                .catch(function (error) {
                    console.log(error);
                    self.setState({errormsg: true});
                    self.setState({createmsg: false});
                    self.setState({datemsg: false});
                });
        }
    }

    render() {
        let self = this;
        return (
            <div>
            <Layout id="dashboard" auth={true} logout={self.props.logout}/>
        <Row style={{margin: "0"}}>
    <EventsLayout selected="createevent"/>
            <AvForm id="eventCreate" style={{marginTop: "10px", marginLeft: "10%", marginBottom: "80px", width: "50%"}}>
    <Label for="name">Name of the Event <span>*</span></Label>
        <AvField name="name" id="name" onChange={this.handleChange} type="text"
        required={true} value={this.state.name}
        errorMessage="Name of the event should be at least 1 character"
        validate={{
            required: {value: true},
            minLength: {value: 1}
        }}/>
        <Label for="location">Location <span>*</span></Label>
        <AvField name="location" id="location" onChange={this.handleChange} type="text"
        value={this.state.location} errorMessage="Location should be at least 1 character"
        validate={{
            required: {value: true},
            minLength: {value: 2}
        }} required/>
        <AvField name="description" label="Description" id="description" onChange={this.handleChange}
        type="textarea"
        value={this.state.description}
        />
        <Label for="date">Date <span>*</span></Label>
        <AvField name="date" id="date" onChange={this.handleChange} type="date"
        value={this.state.date} errorMessage="Name should contain at least 2 characters"
            // min={moment().format("YYYY-MM-DD")}
            // validate={{
            //    dateRange: { end: { value: 0, units: 'minutes' } }
            // }}
            // required
            />
            <Label for="time">Time <span>*</span></Label>
        <AvInput name="time" id="time" onChange={this.handleChange} type="time"
        value={this.state.time}
        required/>
        {/*<Label for="category">Category <span>*</span></Label>*/}
        {/*<AvField name="category" id="category" onChange={this.handleChange} type="select"*/}
        {/*value={this.state.category}>*/}
        {/*<option>Sport</option>*/}
        {/*<option>Study</option>*/}
        {/*<option>Outdoors</option>*/}
        {/*<option selected>Other</option>*/}
        {/*</AvField>*/}
    <Label for="price">Price (at least 0)<span>*</span></Label>
        <AvField name="price" id="price" onChange={this.handleChange} type="number"
        value={this.state.price} min="0" required/>
        <Label for="points">Points (at least 0)<span>*</span></Label>
        <AvField name="points" id="points" onChange={this.handleChange} type="number"
        value={this.state.points} min="0" required/>
        <Label for="participants">Maximum number of participants (at least 2)<span>*</span></Label>
        <AvField name="participants" id="participants" onChange={this.handleChange} type="number"
        value={this.state.participants} min="2" required/>
        <AvField name="img" label="Link to image" id="img" onChange={this.handleChange} type="text"
        value={this.state.img} />
        <div className="eventCreated" style={{display: this.state.createmsg ? 'block' : 'none'}}>
    <div className="alert alert-success">
            Event successfully created!
        </div>
        </div>
        <div className="eventExists" style={{display: this.state.errormsg ? 'block' : 'none'}}>
    <div className="alert alert-danger" role="alert">
            Event for this date and time already reserved! Enter another, please!
        </div>
        </div>
        <div className="dateExpired" style={{display: this.state.datemsg ? 'block' : 'none'}}>
    <div className="alert alert-danger" role="alert">
            Date is from past! Choose future date!
        </div>
        </div>
        <Button style={{backgroundColor: "#2A5885"}} onClick={this.send}
        disabled={!validator.validEventParticipants(self.state.participants) || !validator.validEventPoints(self.state.points) || !validator.validEventPrice(self.state.price) || !validator.validEventTime(self.state.time) || !validator.validEventDate(self.state.date) || !validator.validImageURL(self.state.img) || !validator.validEventLocation(self.state.location) || !validator.validEventName(self.state.name)}>Submit</Button>
        </AvForm>
        </Row>
        </div>
    );
    }
}

export default CreateEvent;

// !validator.validEventCategory(self.state.category) ||