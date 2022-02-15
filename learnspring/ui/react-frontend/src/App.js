import React, { Component } from 'react';
import './App.css';
import Home from './components/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmployeeList from './components/EmployeeList';
import EmployeeEdit from './components/EmployeeEdit';
import PaginationResult from './components/PaginatedResult';
class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/employees/' exact={true} component={EmployeeList}/>
            <Route path='/employees/:id/' component={EmployeeEdit}/>
            <Route path='/users' component={PaginationResult}/>
          </Switch>
         
        </Router>
    )
  }
}
export default App;