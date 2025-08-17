# Weather API

An API to query weather data by city and country. Weather data is acquired from open weather API and is updated every 1 hour. Rate limit is imposed at 5 calls per hour for each API key. This app runs on **port 8080**.

---


## Prerequisites
- **Java 21+** installed
- Open weather map API key from https://openweathermap.org/ (or just use mine which is already available by default in the project)

## To run the Application
1. Clone the repository:
```
git clone https://github.com/TwoRedBean/weather-api.git
```
2. Set your API key in the application.yml file
```
app:
   api-key: [your_open_weather_map_api_key]
```
3. Go to cloned repository folder
```
cd [path_to_weather-api]
```
4. Start application
```
./mvnw spring-boot:run
```

## To get weather description
Perform a GET request with the following endpoint http://localhost:8080/api/v1/weathers/cities/:city/countries/:country with "X-API-Key" in the header. Any of the following api-keys can be used:
1. key1
2. key2
3. key3
4. key4
5. key5

Examples:
```
curl -X GET "http://localhost:8080/api/v1/weathers/cities/london/countries/uk" -H "X-API-Key: key1"
```
```
curl -X GET "http://localhost:8080/api/v1/weathers/cities/singapore/countries/singapore" -H "X-API-Key: key2"
```