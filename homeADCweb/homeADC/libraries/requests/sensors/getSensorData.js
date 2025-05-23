async function getSensorDataRequest(apiKey, sensorName, filters=["sort=timestamp,asc"]) {
    
    
    const baseUrl = API_BASE_URL; // Use your function or variable
    const url = `${baseUrl}/records/${sensorName}${filters.length > 0 ? `?${filters.join("&")}` : ""}`; // Match your backend endpoint


    try {
        const response = await fetch(url, {
            method: 'GET', // Specify the method
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `ApiKey ${apiKey}`
            }
            
        }); 

        // Check if the response status is OK (2xx range)
        if (!response.ok) {
            const errorBody = await response.text();
            throw new Error(`HTTP error! status: ${response.status}, body: ${errorBody}`);
        }

        const result = await response.json();

        return result.data;

    } catch (error) {
        console.log("Error")
        console.error('Error during GET request:', error);

        return false
    }
}