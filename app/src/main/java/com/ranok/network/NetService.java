package com.ranok.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;
import com.ranok.utils.Consts;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetService {

    public static NetApi getNetApi() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder request = original.newBuilder();
            request.header("Content-Type", "application/json");

            if (Hawk.contains(Consts.TOKEN)) {
                request.header("Token",  Hawk.get(Consts.TOKEN));
            }

            return chain.proceed(request.build());
        });

        httpClient.readTimeout(15, TimeUnit.SECONDS);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        if (BuildConfig.DEBUG) {
//            return new FakeApi();
//        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetApi.URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
            return retrofit.create(NetApi.class);
//        }
    }
}

