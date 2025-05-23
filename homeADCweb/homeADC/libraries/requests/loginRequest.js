async function sendLoginRequest(username, password, storage) {
    const baseUrl = API_BASE_URL; // Use your function or variable
    const url = `${baseUrl}/users/login`; // Match your backend endpoint

    const dataToSend = {
        username: username,
        password: password
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
            const errorBody = await response.text();
            throw new Error(`HTTP error! status: ${response.status}, body: ${errorBody}`);
        

        }

        const result = await response.json();
        storage.setItem("accountKey", result.data.userAccountKey)
        console.log(result.data.userAccountKey)

        return true;

    } catch (error) {
        console.log("Error")
        console.error('Error during POST request:', error);

        return false
    }
}