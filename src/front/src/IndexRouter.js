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
                    self.login(response.data);
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
                        <Route exact path={con.projectName +  "/groups"} render={() => self.state.authorized ? <Groups user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to={con.projectName + '/signin'} /> }/>
                        <Route exact path={con.projectName + "/signin"} render={() => self.state.authorized ? <Redirect to={con.projectName + '/groups'} /> : <SignIn login={self.login.bind(this)} />} />
                        <Route exact path={con.projectName + "/signup"} render={() => self.state.authorized ? <Redirect to={con.projectName + '/groups'} /> : <SignUp login={self.login.bind(this)} />} />
                        <Route path={con.projectName + '/'} render={() => <Redirect to={con.projectName + '/signin'} />} />
                    </Switch>
                </Router>
                <LayoutFooter />
            </div>
        );
    }
}

export default IndexRouter;
