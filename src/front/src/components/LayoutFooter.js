import React, { Component } from 'react';
import { Card, CardFooter, Container, Col, Form,FormGroup, Label, Input,Button} from 'reactstrap';

export default class LayoutFooter extends Component {

    render(){
        return (
            <div>
                <Card style={{marginTop: "10px", backgroundColor: "#4E729A"}}>
                    <CardFooter className="text-center" style={{color: "white"}}>© 2018 <span style={{color: 'yellow'}}>AIntrovert</span> All Rights Reserved</CardFooter>
                </Card>
            </div>
        );
    }

}