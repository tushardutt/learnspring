import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar'
import { Link } from 'react-router-dom';
class EmployeeList extends Component {

    constructor(props) {
        super(props);
        this.state = {employees: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('api/employees')
            .then(response => response.json())
            .then(data => this.setState({employees: data}));
    }
 async remove(id) {
    await fetch(`/employees/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(() => {
        let updatedEmployees = [...this.state.employees].filter(i => i.id !== id);
        this.setState({employees: updatedEmployees});
        
    });
}

render() {
    const {employees, isLoading} = this.state;

    if (isLoading) {
        return <p>Loading...</p>;
    }

    const employeeList = employees.map(employee => {
        return <tr key={employee.id}>
            <td style={{whiteSpace: 'nowrap'}}>{employee.name}</td>
            <td>{employee.dept}</td>
            <td>{employee.address}</td>
            <td>{employee.email}</td>
            <td>{employee.mobile}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/employees/" + employee.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
            <h3>Employees</h3>
                <div className="addButton">
                    <Button color="success" tag={Link} to="/employees/new">Add Employee</Button>
                </div>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Dept</th>
                        <th width="30%">Address</th>
                        <th width="30%">Email</th>
                        <th width="30%">Mobile</th>
                        <th width="40%">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {employeeList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}
}
export default EmployeeList;