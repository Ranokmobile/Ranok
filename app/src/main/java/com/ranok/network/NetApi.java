package com.ranok.network;

import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.request.LoginRequest;
import com.ranok.network.request.RfidRequest;
import com.ranok.network.response.BaseResponse;
import com.ranok.network.response.LoginResponse;
import com.ranok.network.response.PackageBarcodeResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetApi {
    //String URL = "http://ebs-dev.localdomain:8008/Conveyor/api/parcelScan/";
    //String URL = "http://dreadnought:8080/Conveyor/api/parcelScan/";
    //String URL = "http://192.168.15.168:8080/Conveyor/api/parcelScan/";
    String URL = "http://192.168.15.133:8080/Conveyor/api/parcelScan/";



    @POST("login")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("logout")
    Single<BaseResponse> logout();

    @POST("rfid")
    Single<BaseResponse> rfid(@Body RfidRequest rfidRequest);

    @POST("packagebarcode")
    Single<PackageBarcodeResponse> packagebarcode(@Body BarcodeRequest barcodeRequest);

}