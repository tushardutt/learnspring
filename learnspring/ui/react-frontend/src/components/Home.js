import React, { Component } from 'react';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/employees">Employees</Link></Button>
                    <Button color='alert'><Link to="/users">Users</Link></Button>
                </Container>
            </div>
        );
    }
}
export default Home;