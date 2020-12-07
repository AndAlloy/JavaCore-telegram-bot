package service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import model.NasaArticle;
import model.NasaDayInHistory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Random;

import static service.ApiConstants.GET_ARTICLE_URL;
import static service.ApiConstants.GET_TODAY_INFO_URL;

@Service
public class NasaImpl {

    @SneakyThrows
    public String [] getTodayNasaInfo() {

        var gson = new Gson();
        var httpClient = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(GET_TODAY_INFO_URL)).build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.body().equals("")){
            return new String[]{"404"};
        }
        else {
            var nasaObj = gson.fromJson(response.body(), NasaDayInHistory.class);
            return nasaObj.toArray();
        }
    }


    @SneakyThrows
    public String [] getArticleNasaInfo() {
        var gson = new Gson();
        var httpClient = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().GET()
                .uri(URI.create(GET_ARTICLE_URL)).build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        var tst = response.body();
        JsonObject convertedObject = new Gson().fromJson(response.body(), JsonObject.class);
        var tst1 = convertedObject.getAsJsonObject("projects").getAsJsonArray("projects");
        ArrayList<Integer> ids1 = new ArrayList<>();
        for (int i = 0;i < tst1.size();i++) {
            var tpt = tst1.get(i);
            ids1.add(Integer.valueOf(tpt.getAsJsonObject().get("id").toString()));
        }
        int rnd = new Random().nextInt(ids1.size());
        var rand = String.valueOf(ids1.get(rnd));
        System.out.println(rand);
        /*------------------------------------------------------------------------------------------*/
        var gson1 = new Gson();
        var httpClient1 = HttpClient.newBuilder().build();
        var request1 = HttpRequest.newBuilder().GET()
                .uri(URI.create("https://api.nasa.gov/techport/api/projects/"+rand+"?api_key=6b4dcOwlR7LAXd0s1ki44kPP0vtYj7gLysZ1zWwy")).build();

        var response1 = httpClient1.send(request1, HttpResponse.BodyHandlers.ofString());
        if (response1.body().equals("")){
            return new String[]{"404"};
        }
        else {
            var nasaObj1 = gson1.fromJson(response1.body(), ArticleResponse.class);
            return nasaObj1.project.toArray();
        }
    }
    static class ArticleResponse{
        NasaArticle project;
    }
}
