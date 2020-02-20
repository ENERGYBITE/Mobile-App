package com.ecar.energybite.http;

import com.ecar.energybite.json.JsonUtility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class APIClient {
    private static Retrofit retrofit = null;


    private static final int CONNECTION_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 10;

    private static OkHttpClient client(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

                // time between each byte read from the server
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

                // time between each byte sent to server
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)

                .retryOnConnectionFailure(false)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(EBWebservice.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(JsonUtility.getJsonObjectMapper()))
                .client(client())
                .build();
        return retrofit;
    }
}
