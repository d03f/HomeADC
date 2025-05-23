async function postNewDataUnit(accountKey, data) {
    const baseUrl = API_BASE_URL;
    const url = `${baseUrl}/dataunits`;


    try {
        const response = await fetch(url, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accountKey}`,
            },
            body: JSON.stringify(data)
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