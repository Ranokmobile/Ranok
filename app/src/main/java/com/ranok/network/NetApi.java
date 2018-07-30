package com.ranok.network;

import com.ranok.network.request.LoginRequest;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetApi {
    //String URL = "http://ebs-dev.localdomain:8008/Conveyor/api/parcelScan/";
    //String URL = "http://dreadnought:8080/Conveyor/api/parcelScan/";
    String URL = "http://192.168.15.168:8080/Conveyor/api/parcelScan/";


    @POST("trylogin")
    Single<String> login(@Body LoginRequest loginRequest);
}