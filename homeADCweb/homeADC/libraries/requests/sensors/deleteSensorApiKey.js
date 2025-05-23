async function deleteApiKeySensorRequest(accountKey, sensorName, apiKeyValue) {
    const baseUrl = API_BASE_URL; // Use your function or variable
    const url = `${baseUrl}/sensors/me/${sensorName}/keys/${apiKeyValue}`; // Match your backend endpoint


    try {
        const response = await fetch(url, {
            method: 'DELETE', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accountKey}`,
            }
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