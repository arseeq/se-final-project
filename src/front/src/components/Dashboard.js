import React, { Component } from 'react';
import { Button } from 'reactstrap';
import Layout from './Layout';

class Dashboard extends Component {
	constructor(props) {
		super(props);
	}

  	render() {
	  	var self = this;
	    return (
	      <div>
	      	<Layout id = "dashboard" auth = {true} logout = {self.props.logout} />
	        <h1>Dashboard Page. Wellcome, {this.props.user}!</h1>
	      </div>
	    );
  	}
}

export default Dashboard;
