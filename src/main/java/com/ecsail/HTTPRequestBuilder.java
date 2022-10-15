package com.ecsail;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HTTPRequestBuilder {

    public HTTPRequestBuilder(String URL, String shortLink) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder queryUrlBuilder = HttpUrl.get("https://eaglecreeksailing.com/url/yourls-api.php").newBuilder();
        queryUrlBuilder.addQueryParameter("signature", "e1073de782");
        queryUrlBuilder.addQueryParameter("action","shorturl");
        queryUrlBuilder.addQueryParameter("keyword", shortLink);
        queryUrlBuilder.addQueryParameter("format","json");
        queryUrlBuilder.addQueryParameter("url",URL);

        Request request = new Request.Builder()
                .url(queryUrlBuilder.build())
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
