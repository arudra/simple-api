import React, {Component} from 'react';
import './App.css';

class App extends Component {

	constructor(props) {
		super(props);
		this.state = {drink: '', location: '', stores: [], loggedIn: '', username: '', password: ''};

		this.handleDrinkChange = this.handleDrinkChange.bind(this);
		this.handleLocationChange = this.handleLocationChange.bind(this);
		this.handleStoresSubmit = this.handleStoresSubmit.bind(this);

		this.handleUsernameChange = this.handleUsernameChange.bind(this);
		this.handlePasswordChange = this.handlePasswordChange.bind(this);
		this.handleUserLoginSubmit = this.handleUserLoginSubmit.bind(this);
		this.handleUserSignupSubmit = this.handleUserSignupSubmit.bind(this);
	}

	handleDrinkChange(event) {
		this.setState({drink: event.target.value});
	}

	handleLocationChange(event) {
		this.setState({location: event.target.value});
	}

	handleStoresSubmit(event) {
		if(this.state.drink && this.state.location) {
			fetch('http://localhost:8080/v1/lcbo/stores?drink=' + this.state.drink + "&location=" + this.state.location)
			.then((r) => {
				r.text().then((text) => {
					const json = text ? JSON.parse(text) : {};
					const results = json.result.length > 10 ? json.result.slice(0, 10) : json.result;
					this.setState({stores: results, drink: '', location: ''});
				})
			})
			.catch((error) => {
				console.error(error);
			});

			event.target.reset();
		} else {
			alert('Please fill out both fields.');
		}

		event.preventDefault();
	}

	handleUsernameChange(event) {
		this.setState({username: event.target.value});
	}

	handlePasswordChange(event) {
		this.setState({password: event.target.value});
	}

	handleUserLoginSubmit(event) {
		fetch('http://localhost:8080/v1/user/login', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				username: this.state.username,
				password: this.state.password,
			}),
		})
			.then((response) => {
				if(response.status===200)
				{
					this.setState({loggedIn: 'true', username: '', password: '', value: ''});
				} else {
					response.text().then((text) => {
						alert(text);

		event.target.reset();});
				}
			})
			.catch((error) => {
				console.error(error);
			});


		event.preventDefault();
		event.target.reset();
	}

	handleUserSignupSubmit(event) {
		fetch('http://localhost:8080/v1/user/add', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				username: this.state.username,
				password: this.state.password,
			}),
		})
			.then((response) => {
				if(response.status===200)
				{
					this.setState({loggedIn: 'true', username: '', password: '', value: ''});
				} else {
					response.text().then((text) => {
						alert(text);
					});
				}
			})
			.catch((error) => {
				console.error(error);
			});
		
		event.preventDefault();
	}


	render() {
		const list = this.state.stores.map((s) => <li>{s.address_line_1}, {s.city}</li> );
		const entries = this.state.stores.length>0 ? (<ul className="App-list-entries">{list}</ul>) : <div>No Results</div>;

		const drinksPage = <div className="App">
			<header className="App-header">
				<h1>Awesome Drink Locator</h1>
			</header>

			<form className="App-form" onSubmit={this.handleStoresSubmit}>
				<label className="App-label">
					Enter a drink name:
					<input className="App-input" type="text" value={this.state.value} onChange={this.handleDrinkChange}/>
				</label>

				<label className="App-label">
					Enter a location:
					<input className="App-input" type="text" value={this.state.value} onChange={this.handleLocationChange}/>
				</label>
				<input className="App-button" type="submit" value="Submit"/>
			</form>

			<div className="App-list">
				<label className="App-list-title">Store Locations</label>
				{entries}
			</div>
		</div>;

		const loginPage = <div className="App">
			<header className="App-header">
				<h1>Awesome Drink Locator</h1>
			</header>
			<form className="App-form" onSubmit={this.handleUserLoginSubmit}>
				<label className="App-label">
					Username:
					<input className="App-input" type="text" value={this.state.value} onChange={this.handleUsernameChange}/>
				</label>
				<label className="App-label">
					Password:
					<input className="App-input" type="password" value={this.state.value} onChange={this.handlePasswordChange}/>
				</label>
				<input className="App-button" type="submit" value="Login"/>
				<input className="App-button" type="button" value="Sign Up" onClick={this.handleUserSignupSubmit}/>
			</form>
		</div>;

		return (this.state.loggedIn ? drinksPage : loginPage);
	}
}

export default App;
