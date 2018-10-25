import React, { Component } from 'react';
import Event from './Event';
import { Container, Row, Col, Button } from 'reactstrap';

class MyEvents extends Component {
  render() {
    return (
	  	<Row className="border rounded">
	  		<Row>
		    	<Col md="4">
			    	<Event title="Go to Football" date="08.02.2018 17:30" descr = "Play football" img="https://cdn.images.express.co.uk/img/dynamic/67/590x/Cristiano-Ronaldo-1008466.jpg?r=1535218537294"/>
			    </Col>
			    <Col md="4">
			    	<Event title="Study for ACM ICPC" date="08.02.2018 17:30" descr = "Play football" img="https://www.acm.org/binaries/content/gallery/acm/ctas/icpc_finals_2016.jpg"/>
			    </Col>
			    <Col md="4">
			    	<Event title="Watch Venom" date="08.02.2018 17:30" descr = "Play football" img="https://www.film.ru/sites/default/files/filefield_paths/venom-couldnt-include-the-spider-symbol-because-of-origin-change.jpg" />
			    </Col>
			    <Col md="4">
			    	<Event title="Go to NinetyOne concert" date="08.02.2018 17:30" descr = "Play football" img="https://static.zakon.kz/uploads/posts/2017-09/2017090707592274159_1499681380wgzpj-725x.jpg" />
			    </Col>
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
