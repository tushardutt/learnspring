import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Alert, Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavBar';

class EmployeeEdit extends Component {

    emptyItem = {
        name: '',
        dept: '',
        address: '',
        email: '',
        mobile: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

async componentDidMount(){
    if (this.props.match.params.id !== 'new') {
        const employee = await (await fetch(`/employees/${this.props.match.params.id}`)).json();
        this.setState({item: employee});
    }
}
handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
}
async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/employees' + (item.id ? '/' + item.id : ''), {
        method: (item.id) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/employees');
}
render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Employee' : 'Add Employee'}</h2>;

    return <div>
        <AppNavbar/>
        <Container>
            {title}
            <Form onSubmit={this.handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" id="name" value={item.name || ''}
                           onChange={this.handleChange} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                                    <Label for="dept">Dept</Label>
                                    <Input type="text" name="dept" id="dept" value={item.dept || ''}
                                           onChange={this.handleChange} autoComplete="dept"/>
                                </FormGroup>
                <FormGroup>
                                    <Label for="address">Address</Label>
                                    <Input type="text" name="address" id="address" value={item.address || ''}
                                           onChange={this.handleChange} autoComplete="address"/>
                                </FormGroup>
                <FormGroup>
                    <Label for="email">Email</Label>
                    <Input type="text" name="email" id="email" value={item.email || ''}
                           onChange={this.handleChange} autoComplete="email"/>
                </FormGroup>
                <FormGroup>
                                    <Label for="mobile">Mobile</Label>
                                    <Input type="text" name="mobile" id="mobile" value={item.mobile || ''}
                                           onChange={this.handleChange} autoComplete="mobile"/>
                                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/employees">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
        <Alert color="success">
        <a href="#" className="alert-link">
          link
        </a>{" "}
        message
      </Alert>
    </div>
}
}
export default withRouter(EmployeeEdit);