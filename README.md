# Weather API

An API to query weather data by city and country. Weather data is acquire from open weather API and is updated every 1 hour. This app runs on **port 8080**.

---


## Prerequisites
- **Java 21+** installed
- API key from https://openweathermap.org/ (or just use mine which is already available by default in the project)

## To run the Application
1. Clone the repository:
```
git clone https://github.com/TwoRedBean/weather-api.git
```
2. Set your API key in the application.yml file
```
app:
   api-key: [your_api_key]
```
3. Go to cloned repository folder
```
cd [path_to_weather-api]
```
4. Start application
```
./mvnw spring-boot:run
```

