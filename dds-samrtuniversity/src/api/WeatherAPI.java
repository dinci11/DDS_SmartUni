package api;

import com.rti.routingservice.infrastructure.OutOfResourcesException;
import gen.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.DataTruncation;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public abstract class WeatherAPI {
    private static final String apiBaseURL = "http://api.openweathermap.org/data/2.5";
    private static final String apiForecastURL = apiBaseURL + "/forecast";
    private static final String apiCurrentWeatherURL = apiBaseURL + "/weather";
    private static final String locationID = "7284824";
    private static final String APIkey = "a781dfda3860da15ed194ae35cf888dd";

    private static HttpClient client;
    private static HttpRequest currentWeatherRequest;
    private static HttpRequest forecastRequest;

    public static void Init() {
        client = HttpClient.newHttpClient();
        currentWeatherRequest = (HttpRequest) HttpRequest.newBuilder(URI.create(apiCurrentWeatherURL+"?id="+locationID+"&APPID="+APIkey)).build();
        forecastRequest = (HttpRequest) HttpRequest.newBuilder(URI.create(apiForecastURL+"?id="+locationID+"&APPID="+APIkey)).build();
    }

    public static WeatherData GetCurrentWeatherData() throws IOException, InterruptedException {
        WeatherData weatherData = new WeatherData();
        HttpResponse<String> response = client.send(currentWeatherRequest,HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        JSONObject weatherResponse = new JSONObject(json);
        weatherData.outTemperature = (int) (weatherResponse.getJSONObject("main").getDouble("temp")-273.15);
        weatherData.outHumidity = weatherResponse.getJSONObject("main").getInt("humidity");
        weatherData.windSpeed = weatherResponse.getJSONObject("wind").getInt("speed");

        return weatherData;
    }

    public static WeatherData GetForecast(Date date) throws IOException, InterruptedException {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        if(date.getTime() < System.currentTimeMillis() || (date.getTime() - 5 * DAY_IN_MS) > System.currentTimeMillis()){
            throw new OutOfResourcesException("The date is later than 5 days");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String dateString = dateFormat.format(date);
        int hour = Integer.parseInt(hourFormat.format(date));
        WeatherData weatherData = new WeatherData();
        HttpResponse<String> response = client.send(forecastRequest,HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        JSONObject weatherResponse = new JSONObject(json);
        JSONArray days = weatherResponse.getJSONArray("list");

        for (Object obj : days) {
            if(obj instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) obj;
                String dt_txt = jsonObject.getString("dt_txt");
                String weatherDay = dt_txt.split(" ")[0];
                int weatherHour = Integer.parseInt(dt_txt.split("[ :]")[1]);
                if(weatherDay.equals(dateString) && hour/3 == weatherHour/3){
                    System.out.println(hour/3+""+ (int)weatherHour/3);
                    weatherData.outTemperature = (int) (jsonObject.getJSONObject("main").getDouble("temp")-273.15);
                    weatherData.outHumidity = jsonObject.getJSONObject("main").getInt("humidity");
                    weatherData.windSpeed = jsonObject.getJSONObject("wind").getInt("speed");
                    return weatherData;
                }

            }
        }
        JSONObject jsonObject = (JSONObject) days.get(days.length()-1);
        weatherData.outTemperature = (int) (jsonObject.getJSONObject("main").getDouble("temp")-273.15);
        weatherData.outHumidity = jsonObject.getJSONObject("main").getInt("humidity");
        weatherData.windSpeed = jsonObject.getJSONObject("wind").getInt("speed");
        return weatherData;
    }

    public static WeatherData GetWeatherData(Date date) {
        WeatherData weatherData = new WeatherData();
        //TODO
        return weatherData;
    }


}
