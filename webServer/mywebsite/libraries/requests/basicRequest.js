async function sendPostRequest() {
    const baseUrl = API_BASE_URL; // Use your function or variable
    const url = `${baseUrl}/users/login`; // Match your backend endpoint

    const dataToSend = {
        username: 'Hello from the frontend!',
        password: 'asdf'
    };

    try {
        const response = await fetch(url, {
            method: 'POST', // Specify the method
            headers: {
                'Content-Type': 'application/json', // Tell the server you're sending JSON
                // Add any other headers your backend expects, e.g., Authorization
                // 'Authorization': 'Bearer your-token',
            },
            body: JSON.stringify(dataToSend) // Convert the JavaScript object to a JSON string
        });

        // Check if the response status is OK (2xx range)
        if (!response.ok) {
            // If the status is not OK, something went wrong (could be CORS or other server error)
            // Check the browser console and network tab for details!
            const errorBody = await response.text(); // Get the error body as text
            throw new Error(`HTTP error! status: ${response.status}, body: ${errorBody}`);
        }

        const result = await response.json();

        console.log('POST request successful:', result);
        return result;

    } catch (error) {
        console.error('Error during POST request:', error);
        document.getElementById('output').textContent = 'POST Error: ' + error.message;
    }
}