package service.impl;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import model.Weather;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static service.ApiConstants.*;

@Service
public class WeatherServiceImpl {

    @SneakyThrows
    public String[] getByCityName(String city) {


        var gson = new Gson();
        var httpClient = HttpClient.newBuilder()
                .build();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_WEATHER_BY_CITY_URL+city+API_KEY_PARAM))
                .build();


        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.body().equals("")){
            return new String[]{"404"};
        } else {
            WeatherResponse weathers = gson.fromJson(response.body(), WeatherResponse.class);
            return weathers.data[0].toArray();
        }


    }

    //@Override
    @SneakyThrows
    public String[] getByGeolocation(Double longitude, Double latitude) {
        var gson = new Gson();
        var httpClient = HttpClient.newBuilder()
                .build();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GET_WEATHER_BY_GEO_URL+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)+API_KEY_PARAM))
                .build();


        var response_geo = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response_geo.body().equals("")){
            return new String[]{"404"};
        } else {
            WeatherResponse weatherObj = gson.fromJson(response_geo.body(), WeatherResponse.class);
            return weatherObj.data[0].toArray();
        }
    }

    static class WeatherResponse{
        Weather[] data;
    }
}

