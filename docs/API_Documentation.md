
  

# API Documentation

  

This is the documentation for the API endpoints and how to interact with them.

  

## General Information

  

### Response Format

All responses from the API follow a consistent structure, indicating whether the request was successful or if an error occurred.

  

**Successful Request:**

  

```json
{
	"status": "success",
	"data": {
		"field1": "data1",
		"field2": "data2"
	}
}
```

  

**Error Response:**

  

```json
{
	"status": "error",
	"message": "Example message"
}
```

  

### Authentication & Authorization

  

Authentication is required for most endpoints to identify the user or client making the request. Two primary methods are used:

  

1. **Bearer Token (for User Accounts):**

* Used for user-related tasks and endpoints under `/api/v1/users/me` and `/api/v1/users` .

* The token is your unique `userAccountKey` obtained during the login process.

* Send the token in the `Authorization` header using the Bearer scheme:

```http

Authorization: Bearer your_user_account_key

```

2. **API Key (for Automated/Sensor Requests):**

* Used for specific endpoints (details provided per endpoint).
*  Send the API key in the `Authorization` header using the Api Key scheme:
```http

Authorization: ApiKey your_api_key_value_here

```

  

### Request Bodies
Additional information needed for specific requests (especially `POST`, `PUT`, `PATCH`) will be included in the request body, typically in `application/json` format. The required fields and their structure vary depending on the endpoint. Refer to the individual endpoint documentation for details.

  

---
---

  

## User Account & API Key Management  `/api/v1/users`

  
This section describes the endpoints related to managing user accounts and their associated API keys. 

  

All requests (except the initial login) require authentication using a **Bearer token**. 

  ---

### Endpoint: `POST /api/v1/users/login`
Login with your username and password to obtain your unique `userAccountKey`. This key is essential for authenticating subsequent requests.

**Request Body (application/json):**

```json
{
	"username": "your_username",
	"password": "your_passwd"
}
```

**Response (application/json):**

```json

{
	"status": "success",
	"data": {
		"userAccountKey": "clave_de_ejemplo"
	}
}
```
  ---
### Endpoint: `GET /api/v1/users/me`

Retrieve the details of your authenticated user account.

**Headers:**

```http
Authorization: Bearer your_user_account_key
```
**Response (application/json):**
  
```json
{
	"status": "success",
	"data": {
		"username": "your_username",
		"role": "your_role",
		"isAdmin": false,
		"accountEnabled": true
		"lastActivity": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59"
	}
}
```

  
---
### Endpoint: `POST /api/v1/users`

Create a new user account. The creator's account must have administrator privileges. The new user's privileges cannot exceed those of the creator.

**Headers:**
  
```http
Authorization: Bearer your_user_account_key
```

  

**Request Body (application/json):**

  

```json
{
	"username": "new_user_username",
	"password": "new_user_password",
	"role": "EDITOR",
	"isAdmin": false,
	"accountEnabled": true 
}
```

* **`username`**: (String) Required. The username for the new account.
* **`password`**: (String) Required. The password for the new account.
* **`role`**: (String) Required. The role assigned to the new user .
* **`isAdmin`**: (Boolean) Optional. Specifies if the new user is an administrator. Defaults to `false`.
* **`accountEnabled`**: (Boolean) Optional. Specifies if the new account is enabled. Defaults to `true`.

**Response (application/json):**


```json
{
	"status": "success",
	"data": {
		"username": "new_user_username",
		"role": "EDITOR",
		"isAdmin": false,
		"accountEnabled": true,
		"lastActivity": "01-01-2000T23:59", 
		"creationDate": "01-01-2000T23:59"
	}
}
```

---
---

  

## Personal API Keys  `/api/v1/users/me/apikey`

This section covers endpoints for viewing, administering, and creating API keys linked to your user account.

Access to these endpoints requires authentication, either with your user account's **Bearer token** or by using an **API Key** itself, as specified for each method.


---

### Endpoint:  `GET /api/v1/users/me/apikey` (List Keys)
Get a list of the API keys linked to your authenticated user account.

**Headers:**


```http
Authorization: Bearer your_user_account_key
```
  
**Response (application/json):**

```json
{
	"status": "success",
	"data": {
		"keys": [
			"api_key_1",
			"api_key_2",
			"api_key_3"
		]
	}
}
```

  
---
### Endpoint:  `GET /api/v1/users/me/apikey`  (Get Key Details)  
 Get the detailed data for a specific API key.
 
**Authentication:** Required. Use API key authorization with the API key value itself.
**Headers:**

```http
Authorization: ApiKey your_api_key_value_here
```

**Response (application/json):**



```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}

```
---
 ### Endpoint:  `POST /api/v1/users/me/apikey` 
Create a new API key linked to your authenticated user account. Returns all the data of the newly created key.

**Headers:**

```http
Authorization: Bearer your_user_account_key
```


**Request Body (application/json):**

  

```json
{
	"name": "name_new_api_key",
	"access": "access_level_new_api_key",
	"expirationDate": "01-01-2000T23:59", 
	"keyEnabled": true 

}
```
* `name`: (String) Required. A human-readable name for the new API key.
* `access`: (String) Required. The access level or role granted to this API key.
* `expirationDate`: (String, ISO 8601 format) Optional. The date and time when the API key expires. Defaults to `null` (no expiration).
* `keyEnabled`: (Boolean) Optional. Specifies if the API key is enabled upon creation. Defaults to `true`.

  

**Response (application/json):**

```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}
```

  
---
 ### Endpoint: `DELETE /api/v1/users/me/apikey`
 Delete the API key used for authorization in this request. Returns all the data of the deleted key.


**Headers:**

```http
Authorization: ApiKey your_api_key_value_here
```

  

**Response (application/json):**

  

```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}
```

  

---

  

### Endpoint: `GET /api/v1/users/me/apikey/cleanup`

 Trigger the removal of expired API keys from the database. This is also handled by a scheduled task on the server.
  

**Response (application/json):**

  

```json
{
	"status": "success",
	"data": "Cleaned all the outdated apiKeys!"
}
```

  

---

  

### Endpoint: PATCH /api/v1/users/me/apikey/name`
   Update the human-readable name of an API key.
**Headers:**

  
```http
Authorization: ApiKey your_api_key_value_here
```

  

**Request Body (application/json):**

  

```json
{
	"name": "new_api_key_name"
}
```

  

* `name`: (String) Required. The new name for the API key.

  

**Response (application/json):**

  

```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
}
```

  

---

  

### Endpoint: `PATCH /api/v1/users/me/apikey/enabled`
  

Enable or disable a specific API key.


**Headers:**
```http
Authorization: ApiKey your_api_key_value_here
```


**Request Body (application/json):**

```json
{
	"apiKeyValue": "api_key_to_search", 
	"enable": true
}
```

  

* `apiKeyValue`: (String) Required. The value of the API key to enable or disable.
* `enable`: (Boolean) Required. Set to `true` to enable the key, `false` to disable it.

  

**Response (application/json):**

 

  

```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}

```

 
---

  

### Endpoint: `PATCH /api/v1/users/me/apikey/expirationdate`

Set or update the expiration date for a specific API key.

  

**Headers:**

```http
Authorization: ApiKey your_api_key_value_here
```

  

**Request Body (application/json):**

  

```json
{
	"apiKeyValue": "api_key_to_search", 
	"expirationDate": "01-01-2000T23:59" 
}
```

  

* `apiKeyValue`: (String) Required. The value of the API key to update the expiration date for.
* `expirationDate`: (String, ISO 8601 format, or `null`) Required. The new expiration date and time. Can be set to `null` to remove the expiration.

  

**Response (application/json):**

  

```json
{
	"status": "success",
	"data": {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}
```

---

---


  

## DataUnit API  `/api/v1/dataunits`

  

This section allows you to manage the data unit types used by sensors.


  

Access to these endpoints requires authentication, typically with your user account's **Bearer token**.

  

### Endpoint: `GET /api/v1/dataunits`

 Get a list of all available data unit types.

**Headers:**

  
```http
Authorization: ApiKey your_api_key_value_here
```

  

**Response (application/json):**

```json
{
	"status": "success",
	"data": [
		{"name": "Celsius", "symbol": "ºC"},
		{"name": "Humidity Percent", "symbol": "%"}
	]
	
}
```

  ---

### Endpoint: `POST /api/v1/dataunits`

Create a new custom data unit type.



**Headers:**


```http
Authorization: ApiKey your_api_key_value_here
```
  

**Request Body (application/json):**
```json
{
	"symbol": "choosen_symbol",
	"name": "choosen_name" 
}

```
* `symbol`: (String) Required. The symbol for the new data unit (e.g., "ºC", "%", "m/s").
* `name`: (String) Required. The full name of the data unit (e.g., "Celsius", "Percentage", "Meters per Second").
  

**Response (application/json):**
  

```json
{
	"status": "success",
	"data": {
		"name": "choosen_name",
		"symbol": "choosen_symbol"
	}
}

```

  
---
---

  

## Sensors API  `/api/v1/sensors`

  

A Sensor represents a physical device that reports data. Each Sensor has its information, linked `SensorRecord`s, and defines which API keys can access its data.



  

Access to these endpoints requires authentication, typically with your user account's **Bearer token**. Some operations may require specific ownership or permissions.

  ---

### Endpoint: `GET /api/v1/sensors/me`  
Get a list of all sensors that are linked to your authenticated user account.

**Headers:**

```http
Authorization: Bearer your_user_account_key
```
  

**Response (application/json):**
 
```json
{
	"status": "success",
	"data": [
		{
			"name": "name_of_sensor_1",
			"description": "sensor_description_1",
			"location": "where the sensor is located 1",
			"dataUnit": {"name": "Celsius", "symbol": "ºC"},
			"creationDate": "01-01-2000T23:59",
			"lastActivity": "01-01-2000T23:59",
			"allowedApiKeysCount": 0,
			"recordsCount": 0
		},
	]
}
```

  
---

  

### Endpoint: `GET /api/v1/sensors/me/{name}`
 Get the detailed information for a specific sensor linked to your authenticated user account.
**Headers:**

```http
Authorization: Bearer your_user_account_key
```
**Path Parameters:**
* `name`: (String) Required. The name of the sensor to retrieve.

**Response (application/json):**
  

```json
{
	"status": "success",
	"data": {
		"name": "name_of_sensor",
		"description": "sensor_description",
		"location": "where the sensor is located",
		"dataUnit": {"name": "Celsius", "symbol": "ºC"},
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59",
		"allowedApiKeysCount": 0,
		"recordsCount": 0
	}
}
```

  ---
  
### Endpoint: `POST /api/v1/sensors/me`
 Create a new sensor linked to your authenticated user account.
 
**Headers:**

```http
Authorization: Bearer your_user_account_key
```
  
**Request Body (application/json):**

```json
{
	"name": "new_sensor_name",
	"description": "new_sensor_description", 
	"location": "where the sensor is located",
	"dataUnit": "Celsius" 
}
```
* `name`: (String) Required. A unique name for the new sensor.
* `description`: (String) Optional. A description for the sensor. Defaults to `null`.
* `location`: (String) Optional. The physical location of the sensor. Defaults to `null`.
* `dataUnit`: (String) Required. The name or symbol of an existing DataUnit that this sensor's records will use.


**Response (application/json):**
  

```json
{
	"status": "success",
	"data": {
		"name": "new_sensor_name",
		"description": "new_sensor_description",
		"location": "where the sensor is located",
		"dataUnit": {"name": "Celsius", "symbol": "ºC"},
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59",
		"allowedApiKeysCount": 0,
		"recordsCount": 0
	}
}
```

  
---

### Endpoint: `DELETE /api/v1/sensors/me/{name}`
 Delete an existing sensor that you own.

**Headers:**

```http
Authorization: Bearer your_user_account_key
```

**Path Parameters:**
* `name`: (String) Required. The name of the sensor to be deleted.
  
**Request Body (application/json):**


```json
{
	"name": "to_delete_sensor_name"
}
```
* `name`: (String) Required. The name of the sensor to confirm for deletion.

**Response (application/json):**
  

```json
{
"status": "success",
	"data": {
		"name": "deleted_sensor_name",
		"description": "deleted_sensor_description",
		"location": "where the sensor is located",
		"dataUnit": { "name": "Celsius", "symbol": "ºC" },
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59",
		"allowedApiKeysCount": 0,
		"recordsCount": 0
	}
}
```

  

---

  

### Endpoint: `POST /api/v1/sensors/me/{name}/keys`

 Add a specific API key to a sensor's allowed list. You can only add keys to sensors that you own. If the API key belongs to another user, you still use your `userAccountKey` for authorization.

  

**Headers:**
```http
Authorization: Bearer your_user_account_key
```

**Path Parameters:**
* `name`: (String) Required. The name of the sensor to which the key will be added.

**Request Body (application/json):**

```json
{
	"apiKeyValue": "new_api_key_to_add"
}
```

* `apiKeyValue`: (String) Required. The value of the API key to add to the sensor's allowed list.

**Response (application/json):**
```json
{
	"status": "success",
	"data":  {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
}	
}

```

  

### Endpoint:  `DELETE /api/v1/sensors/me/{name}/keys/{apikey}`
Remove a specific API key from a sensor's allowed list. You can only remove keys from sensors that you own. If the API key belongs to another user, you still use your `userAccountKey` for authorization.


**Headers:**

```http
Authorization: Bearer your_user_account_key
```

  

**Path Parameters:**
* `name`: (String) Required. The name of the sensor from which the key will be removed.
* `apikey`: (String) Required. The value of the API key to remove.


  

**Response (application/json):**
```json
{
	"status": "success",
	"data":  {
		"apiKeyValue": "api_key_value",
		"name": "api_key_name",
		"access": "key_access_level",
		"keyEnabled": true,
		"expirationDate": "01-01-2000T23:59",
		"creationDate": "01-01-2000T23:59",
		"lastActivity": "01-01-2000T23:59"
	}
}

```

  

---

----



## Sensor Records API `/api/v1/records`

  

These are the primary endpoints for uploading and retrieving data collected by sensors.
Authentication for all endpoints in this section is done using **API Keys**.

  ---
### Endpoint: `POST /api/v1/records/{name}`
Upload a new data record linked to a specific sensor.

**Headers:**
```http
Authorization: ApiKey your_api_key_value_here
```


**Path Parameters:**
* `name`: (String) Required. The name of the sensor to which the record will be added.

**Request Body (application/json):**
```json
{
	"value": 1,
	"metadata": "This is info about the report"
}
```
* `value`: (Number) Required. The sensor reading value. Type should match the sensor's data unit (e.g., integer, decimal).
* `metadata`: (String) Optional. Additional descriptive information about the record.

**Response (application/json):**
  

```json
{
	"status": "success",
	"data": {
		"sensorRecordId": "******-***-****-****-**********",
		"value": 1,
		"sensorId": "******-***-****-****-**********",
		"timestamp": "01-01-2000T23:59",
		"metadata": "This is info about the report"
	}
}
```
---

### Endpoint: `GET /api/v1/records/{name}`
Retrieve a list of records linked to a specific sensor, with extensive filtering, pagination, and sorting options.

**Headers:**
```http
Authorization: ApiKey your_api_key_value_here
```

**Path Parameters:**
* `name`: (String) Required. The name of the sensor whose records you want to retrieve.

**Request Parameters (Query Parameters):**

  


| Parameter        | Example Value        | Description                                       |
| :--------------- | :------------------- | :------------------------------------------------ |
| `minValue`       | `1.0`                | Filter records with value >= this minimum.        |
| `maxValue`       | `99.99`              | Filter records with value <= this maximum.        |
| `startDate`      | `2020-05-10T01:00:00`| Filter records with timestamp >= this date/time (ISO 8601). |
| `endDate`        | `2020-05-10T23:00:00`| Filter records with timestamp <= this date/time (ISO 8601). |
| `metadataContains`| `Example`            | Filter records where the `metadata` contains this substring (case-insensitive). |
| `size`           | `10`                 | Maximum number of records to return per page.     |
| `page`           | `2`                  | The specific page number to retrieve (0-indexed). |
| `sort`           | `timestamp,desc`     | Sort the results by field(s). Format: `fieldName,direction`. Direction is `asc` or `desc`. Can be repeated for multi-field sort. |

**Response (application/json):**
  
```json
{
	"status": "success",
	"data": [
		{
			"value": 1.00,
			"timestamp": "2000-01-01T23:59:59",
			"metadata": "This is info about the report"
		},
		{
			"value": 1.00,
			"timestamp": "2000-01-01T23:59:58",
			"metadata": "This is info about the report"
		}
	]
}
```

  

### Endpoint: `GET /api/v1/records/{name}/{option}`

 Get a single specific sensor record or an aggregate value for a sensor.

**Headers:**
```http
Authorization: ApiKey your_api_key_value_here
```

**Path Parameters:**
* `name`: (String) Required. The name of the sensor.
* `option`: (String) Required. Specifies the type of record or aggregate to retrieve.

**Option Values:**


| Option | Description                                         |
| :----- | :-------------------------------------------------- |
| `min`  | Retrieve the record with the minimum value.         |
| `max`  | Retrieve the record with the maximum value.         |
| `avg`  | Get the average value of all records for the sensor.|
  
  

**Response (application/json):**
 
```json
{
	"status": "success",
	"data": {
		"value": 1.00, 
		"timestamp": "2000-01-01T23:59:59",
		"metadata": "..."
	}
}
```
  

---
---

  

## Exceptions

  

This section lists the common error messages that can be returned by API methods. The general format for error responses is as specified at the [beginning of this document](###response-format).



  

**Common Error Messages:**

  


| Error Message                 | Explanation                                                     |
| :---------------------------- | :-------------------------------------------------------------- |
| `The credentials are not valid!`| Indicates that the provided username/password combination is incorrect during login. |
| `The user Account Key used is not valid!`| The Bearer token provided in the Authorization header is not a recognized or valid user account key. |
| `The api key is not valid!`   | The API key provided in the Authorization header is not recognized, invalid, or not allowed for the requested resource/operation. |
| `Your user can't do this action!`| Access Denied. The authenticated user/API key does not have the necessary privileges or ownership to perform the requested action. |
| `The data is not valid!`    | The format or content of the request body or parameters is incorrect according to the API's expectations. Check the documentation for the specific endpoint. |
| `The dataUnit type is not valid!`| The specified data unit name or symbol in a request body does not exist. |
| `The sensor specified is not valid!`| The sensor name provided in the path or request body does not exist, or the authenticated user/API key does not have access to it. |
| `The values chosen already exist!`| Indicates an attempt to create a resource (like a user or sensor) with identifiers (e.g., username, sensor name) that are already in use. Duplicated entry on the database. |
| `General custom exception happened!`| A generic error message indicating an unexpected issue occurred on the server side. |

