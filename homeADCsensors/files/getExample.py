import urequests
import wifi

wifi.connect("Livebox6-CB03", "ulb3uxS(5D.A7")

url = "http://192.168.1.121:8080/api/v1/dataunits"  # Replace with your API endpoint

response = urequests.get(url)

print("Status code:", response.status_code)
print("Response text:", response.text)

response.close()  # Always close the response