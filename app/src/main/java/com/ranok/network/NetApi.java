package com.ranok.network;

import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.request.LoginRequest;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.request.MoveLpnRequest;
import com.ranok.network.request.PlaceRequest;
import com.ranok.network.request.RfidRequest;
import com.ranok.network.response.BaseResponse;
import com.ranok.network.response.LoginResponse;
import com.ranok.network.response.LpnInfoResponse;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.network.response.PackageBarcodeResponse;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.network.response.PlaceInfoResponse;
import com.ranok.network.response.PositionInfoByBarcodeResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetApi {
    //String URL = "http://ebs-dev.localdomain:8008/Conveyor/api/parcelScan/";
    //String URL = "http://dreadnought:8080/Conveyor/api/parcelScan/";
    //String URL = "http://192.168.15.168:8080/Conveyor/api/parcelScan/";
    String URL = "http://192.168.15.244:8080/Conveyor/api/parcelScan/";

    @POST("login")
    Single<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("logout")
    Single<BaseResponse> logout();

    @POST("rfid")
    Single<BaseResponse> rfid(@Body RfidRequest rfidRequest);

    @POST("packagebarcode")
    Single<PackageBarcodeResponse> packagebarcode(@Body BarcodeRequest barcodeRequest);

    @POST("add_movement")
    Single<PackageBarcodeResponseData> addMovement();

    @POST("get_position_info_by_barcode")
    Single<PositionInfoByBarcodeResponse> getPositionsByBarcode(@Body BarcodeRequest barcodeRequest);

    @POST("get_position_info_by_code")
    Single<PositionInfoByBarcodeResponse> getPositionsByCode(@Body CodeRequest codeRequest);

    @POST("get_lpn_info_by_code")
    Single<LpnInfoResponse> getLpnByCode(@Body CodeRequest codeRequest);

    @POST("get_items_by_place")
    Single<PlaceInfoResponse> getPlaceItemsByCode(@Body PlaceRequest placeRequest);

    @POST("move_lpn")
    Single<LpnOperationResponse> moveLpn(@Body MoveLpnRequest moveLpnRequest);
}