// Tell JavaScript to invoke the `handleClick` function, when a `click` event is received by the element with id 'submit-button'
document.getElementById('submit-button').addEventListener('click', handleClick);

const APPLICATION_JSON = 'application/json';

// Event Handler Function
function handleClick(event) {
	// Grab reference element with id 'terminal-command' (In our case the input tag)
	const terminalCommand = document.getElementById('terminal-command').value;
	
	// Create Simple JS Object to send in the Request Body to our server
	const terminalCommandObj = { terminalCommand: terminalCommand };
	
	// Make an HTTP Request using XMLHttpRequest - 1990's way
	// const xhr = new XMLHttpRequest();
	
	
	// Fetch API is new school - be careful!! Returns a Promise
	// When you promise somebody something, nothing happens until the event occurs. THEN, you can act appropriately
	// If I promise to bring you donuts, if I bring them, THEN you eat them
	// If I don't, THEN you won't eat them.
	fetch("http://localhost:8080/demo/terminal.do", {
		method: 'POST',
		body: JSON.stringify(terminalCommandObj),
		headers: {
			'Content-Type': APPLICATION_JSON
		}
	}).then(res => {
		if (res.ok) {
			return res.json();
		}
		console.error('Failed to complete response');
	}).then(respJson => {
		const responseObject = respJson;
		const p = document.createElement('p');
		p.textContent = responseObject.message;
		
		document.getElementById('demo-fieldset').appendChild(p);
	})
}