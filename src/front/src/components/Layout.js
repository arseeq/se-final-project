import React, { Component } from 'react';
import {Collapse,Navbar,NavbarToggler,NavbarBrand,Nav,NavItem,NavLink,UncontrolledDropdown,DropdownToggle,DropdownMenu,DropdownItem } from 'reactstrap';
import { Card, CardFooter, Container, Col, Form,FormGroup, Label, Input,Button} from 'reactstrap';
import {Link} from 'react-router-dom';
import con from '../config.js'

export default class Layout extends Component {

	constructor(props) {
    	super(props);
    	this.toggle = this.toggle.bind(this);
    	this.state = {
      		isOpen: false
    	};
  	}

  	toggle() {
    	this.setState({
      		isOpen: !this.state.isOpen
    	});
  	}

  	render() {
  		var self = this;
  		return (
		        <Navbar color="light" light expand="md">
		          <NavbarBrand href="/">AIntrovert</NavbarBrand>
		          <NavbarToggler onClick={this.toggle} />
		          <Collapse isOpen={this.state.isOpen} navbar>
		            <Nav className="ml-auto" navbar>
		            	<UncontrolledDropdown nav inNavbar>
			                <DropdownToggle nav caret>
			                  Options
			                </DropdownToggle>
			                <DropdownMenu right>
			                  <DropdownItem>
			                    Option 1
			                  </DropdownItem>
			                  <DropdownItem>
			                    Option 2
			                  </DropdownItem>
			                  <DropdownItem divider />
			                  <DropdownItem>
			                    Reset
			                  </DropdownItem>
			                </DropdownMenu>
		              	</UncontrolledDropdown>
		              	{
		              		!self.props.auth &&
		              			<>
		              			{self.props.id != "signin" ? (<NavItem>
		                			<Link className = "nav" to={con.addr + '/signin'}>Sign In</Link>
		              			</NavItem>) : ''}
		              			{self.props.id != "signup" ? (<NavItem>
		                			<Link to={con.addr + '/signup'}>Sign Up</Link>
		              			</NavItem>) : ''}
		   		              	</>
		   				}
		   				{
		   		            (this.props.auth && self.props.id != 'dashboard') &&
		   		            	<>
		   		            	<NavItem>
		                			<Link to={con.addr + '/dashboard'}>Dashboard</Link>
		         				</NavItem>
		              			</>
		              	}
		              	{
		   		            this.props.auth &&
		   		            	<>
		   		            	<NavItem className="btn btn-link" onClick={self.props.logout} >Sign Out</NavItem>
		              			</>
		              	}
		            </Nav>
		          </Collapse>
		        </Navbar>
  		);
  	}
}
