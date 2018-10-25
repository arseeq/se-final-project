import React, { Component } from 'react';
import Event from './Event';
import { Container, Row, Col, Table, Button } from 'reactstrap';

class AllEvents extends Component {
  render() {
    return (
    	<div className="border rounded">
	      <Table hover>
	        <thead>
	          <tr>
	            <th>Group #</th>
	            <th>Group Name</th>
	            <th>Date</th>
	            <th>Time</th>
	            <th>Place</th>
	            <th>Action</th>
	          </tr>
	        </thead>
	        <tbody>
	          <tr>
	            <th scope="row">1</th>
	            <td>Go to football</td>
	            <td>08.02.2018</td>
	            <td>17:00</td>
	            <td>Football field</td>
	            <td>
		            <Button color="danger">details</Button>{' '}
		            <Button color="success">join</Button>{' '}
	            </td>
	          </tr>
	          <tr>
	            <th scope="row">2</th>
	            <td>Study for ACM ICPC</td>
	            <td>08.02.2018</td>
	            <td>17:00</td>
	            <td>Library</td>
	            <td>
		            <Button color="danger">details</Button>{' '}
		            <Button color="success">join</Button>{' '}
	            </td>
	          </tr>
	          <tr>
	            <th scope="row">3</th>
	            <td>Watch Venom</td>
	            <td>08.02.2018</td>
	            <td>17:00</td>
	            <td>Cinema</td>
	            <td>
		            <Button color="danger">details</Button>{' '}
		            <Button color="success">join</Button>{' '}
	            </td>
	          </tr>
	        </tbody>
	      </Table>
	      <Row className="paging">
			<Col md={{offset: 10}}>
				<Button color="primary">previous</Button>{' '}
	            <Button color="info">next</Button>{' '}
			</Col>
		  </Row>
	</div>
    );
  }
}

export default AllEvents;
