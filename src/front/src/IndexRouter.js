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

import Home from './components/Home';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import Dashboard from './components/Dashboard';
import LayoutFooter from './components/LayoutFooter';
//const jwt = require('jsonwebtoken');
//const config = require('../../config');

class IndexRouter extends React.Component{
    constructor(props){
        super(props);
        this.state={authorized: null, user: ""};
        //this.signin = this.signin.bind(this);
    }
    componentWillMount(){
      // axios.get('/api/check',{
      // }).then((res) => {
      //     this.setState({authorized: res.data});
      // });

      // setTimeout(() => {this.setState({authorized: false})} , 1000);
      var self = this;
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

    login(usr){
      console.log(usr)
      this.setState({authorized: true, user: usr});
    }

    logout(){
      localStorage.setItem('token', '');
      this.setState({authorized: false});
    }

    render(){
      var self = this;
      if (this.state.authorized==null){
        return <h1>Loading ... </h1>;
      }
      return (
          <div>
              <Router>
                <Switch>
                  <Route exact path="/dashboard" render={() => self.state.authorized ? <Dashboard user={self.state.user} logout={self.logout.bind(this)} /> : <Redirect to='/signin' /> }/>
                  <Route exact path="/signin" render={() => self.state.authorized ? <Redirect to='/dashboard' /> : <SignIn login={self.login.bind(this)} />} />
                  <Route exact path="/signup" render={() => self.state.authorized ? <Redirect to='/dashboard' /> : <SignUp login={self.login.bind(this)} />} />
                  <Route path='/' render={() => <Redirect to='/signin' />} />
                </Switch>
              </Router>
              <LayoutFooter />
          </div>
      );
    }
};

export default IndexRouter;
