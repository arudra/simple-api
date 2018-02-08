import React, { Component } from 'react';
import './App.css';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {drink: '', location: '', stores: []};

    this.handleDrinkChange = this.handleDrinkChange.bind(this);
    this.handleLocationChange = this.handleLocationChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleDrinkChange(event) {
    this.setState({drink: event.target.value});
  }

  handleLocationChange(event) {
    this.setState({location: event.target.value});
  }

  handleSubmit(event) {
    fetch('http://localhost:8080/v1/lcbo/stores?drink='+this.state.drink +"&location=" + this.state.location, {
      mode: 'cors'
    })
    .then((r) => {
      r.text().then((text) => {
          const json = text ? JSON.parse(text) : {};
          console.log(json);

          const results = json.result.length > 10 ? json.result.slice(0,10) : json.result;
          console.log(results);
          this.setState({stores: results});
      })
    })
    .catch((error) => {
      console.error(error);
    });

    event.preventDefault();
  }


  render() {
    return (
      <div className="App">
        <header className="App-header">
        <h1>Awesome Drink Locator</h1>
        </header>
        
        <form onSubmit={this.handleSubmit}>
          <label className="App-label">
            Enter a drink name: 
            <input className="App-input" type="text" value={this.state.value} onChange={this.handleDrinkChange} />
          </label>

          <label className="App-label">
            Enter a locaiton: 
            <input className="App-input" type="text" value={this.state.value} onChange={this.handleLocationChange} />
          </label>
          <input className="App-button" type="submit" value="Submit" />
        </form>
      </div>
    );
  }
}

export default App;
