import React from 'react';
import {
    BrowserRouter as Router,
    Route,
    Link,
    Redirect,
    withRouter,
    Switch
} from 'react-router-dom';
import axios from 'axios';
import con from './config';

import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Dashboard from './components/Dashboard';
import LayoutFooter from './components/LayoutFooter';
import Groups from './components/Groups';
import Chat from './components/Chat';
import MyEvents from "./components/MyEvents";
import AllEvents from "./components/AllEvents";
import CreateEvent from "./components/CreateEvent";
import Group from"./components/Group";
import User from "./components/User";

class IndexRouter extends React.Component{
    constructor(props){
        super(props);
        this.state={authorized: null, user: "", socketReady: false, onMsg: (message) =>{
            console.log(message);
        }};
        this.login = this.login.bind(this);
        this.logout = this.logout.bind(this);
    }
    componentWillMount(){
        let self = this;
        this.socket = new WebSocket(con.sockethost);
        this.socket.onmessage = function(message) {
            self.state.onMsg(message);
        };

        console.log(con.addr+'/mainServices/auth/checktoken');
        if (localStorage.getItem('token')!=null){
            axios(con.addr+'/mainServices/auth/checktoken', {
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
                    self.login(response.data.toLowerCase());
                })
                .catch(function (error) {
                    console.log(error);
                    self.setState({authorized: false});
                });
        } else {
            self.setState({authorized: false});
        }

    }

    setOnMsg(onMsg){
        this.setState({onMsg: onMsg});
    }

    login(usr){
        console.log(usr);
        localStorage.setItem('email', usr);
        localStorage.setItem('userId', );
        this.setState({authorized: true, user: usr});
    }

    logout(){
        localStorage.setItem('token', '');
        this.setState({authorized: false});
    }

    render(){
        let self = this;
        if (this.state.authorized==null){
            return <h1>Loading ... </h1>;
        }
        return (
            <div>
                <Router>
                    <Switch>
                        <Route exact path={con.projectName +  "/dashboard"} render={() => self.state.authorized ? <Dashboard user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName + "/chat/:id"} render={() => self.state.authorized ? <Chat setOnMsg={self.setOnMsg.bind(this)} socket={this.socket} user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName + "/group/:id"} render={() => self.state.authorized ? <Group user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName + "/user/:id"} render={() => self.state.authorized ? <User user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName +  "/myevents"} render={() => self.state.authorized ? <MyEvents logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName + "/signin"} render={() => self.state.authorized ? <Redirect to={con.projectName + '/myevents'} /> : <SignIn login={self.login.bind(this)} />} />
                        <Route exact path={con.projectName + "/signup"} render={() => self.state.authorized ? <Redirect to={con.projectName + '/myevents'} /> : <SignUp login={self.login.bind(this)} />} />
                        <Route exact path={con.projectName +  "/allevents"} render={() => self.state.authorized ? <AllEvents logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName +  "/createevent"} render={() => self.state.authorized ? <CreateEvent logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route path={con.projectName + '/'} render={() => <Redirect to={con.projectName + '/signin'} />} />
                    </Switch>
                </Router>
                <LayoutFooter />
            </div>
        );
    }
}

export default IndexRouter;
