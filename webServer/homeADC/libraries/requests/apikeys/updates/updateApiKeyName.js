async function updateApiKeyName(apiKey, newName) {

    const baseUrl = API_BASE_URL; // Use your function or variable
    const url = `${baseUrl}/users/me/apikey/name`; // Match your backend endpoint


    try {
        const response = await fetch(url, {
            method: 'PATCH', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `ApiKey ${apiKey}`
            },
            body: JSON.stringify({"name" : newName})
        });

        if (!response.ok) {
            const errorBody = await response.text();
            throw new Error(`HTTP error! status: ${response.status}, body: ${errorBody}`);
        

        }

        const result = await response.json();
        return result.data

    } catch (error) {
        console.log("Error")
        console.error('Error during POST request:', error);

        return false
    }
}