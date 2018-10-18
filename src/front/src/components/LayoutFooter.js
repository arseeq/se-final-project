import React, { Component } from 'react';
import { Card, CardFooter, Container, Col, Form,FormGroup, Label, Input,Button} from 'reactstrap';

export default class LayoutFooter extends Component {

	render(){
		return (
		<div>
			<Card>
	           <CardFooter className="text-muted text-center">© 2018 <span style={{color: '#0000ff'}}>AIntrovert</span> All Rights Reserved</CardFooter>
	       	</Card>
	    </div>
       	);
	}

}
