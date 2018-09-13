package com.ranok.network;

import com.ranok.network.request.AcceptListRequest;
import com.ranok.network.request.AcceptOrderRequest;
import com.ranok.network.request.BarcodeRequest;
import com.ranok.network.request.CodeRequest;
import com.ranok.network.request.CreateLotRequest;
import com.ranok.network.request.LoginRequest;
import com.ranok.network.request.MoveLpnRequest;
import com.ranok.network.request.NewFcmTokenRequest;
import com.ranok.network.request.PackToLpnRequest;
import com.ranok.network.request.PlaceRequest;
import com.ranok.network.request.PrintLpnRequest;
import com.ranok.network.request.RecieptListRequest;
import com.ranok.network.request.RecieptOrderRequest;
import com.ranok.network.request.RfidRequest;
import com.ranok.network.request.SplitLpnRequest;
import com.ranok.network.request.UnpackLpnRequest;
import com.ranok.network.response.AcceptListResponse;
import com.ranok.network.response.AcceptOrderResponse;
import com.ranok.network.response.BaseResponse;
import com.ranok.network.response.CreateLotResponse;
import com.ranok.network.response.LoginResponse;
import com.ranok.network.response.LotInfoResponse;
import com.ranok.network.response.LpnInfoResponse;
import com.ranok.network.response.LpnOperationResponse;
import com.ranok.network.response.PackageBarcodeResponse;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.network.response.PlaceInfoResponse;
import com.ranok.network.response.PositionInfoByBarcodeResponse;
import com.ranok.network.response.RecieptListResponse;
import com.ranok.network.response.RecieptOrderResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetApi {
    //String URL = "http://ebs-dev.localdomain:8008/Conveyor/api/parcelScan/";
    //String URL = "http://dreadnought:8080/Conveyor/api/parcelScan/";
    //String URL = "http://192.168.15.168:8080/Conveyor/api/parcelScan/";
    String URL = "http://192.168.15.176:8080/Conveyor/api/parcelScan/";

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

    @POST("split_lpn")
    Single<LpnOperationResponse> splitLpn(@Body SplitLpnRequest splitLpnRequest);

    @POST("unpack_lpn")
    Single<LpnOperationResponse> unpackLpn(@Body UnpackLpnRequest unpackLpnRequest);

    @POST("print_lpn")
    Single<LpnOperationResponse> printLpn(@Body PrintLpnRequest printLpnRequest);

    @POST("pack_lpn")
    Single<LpnOperationResponse> packLpn(@Body PackToLpnRequest packLpnRequest);

    @POST("new_fcm_token")
    Single<BaseResponse> newPushToken(@Body NewFcmTokenRequest newFcmTokenRequest);

    @POST("receipt_list")
    Single<RecieptListResponse> getRecieptList(@Body RecieptListRequest recieptListRequest);

    @POST("accepted_list")
    Single<AcceptListResponse> getAcceptList(@Body AcceptListRequest acceptListRequest);

    @POST("lot_info")
    Single<LotInfoResponse> getLotInfo(@Body CodeRequest codeRequest);

    @POST("create_lot")
    Single<CreateLotResponse> createLot(@Body CreateLotRequest createLotRequest);

    @POST("reciept_order")
    Single<RecieptOrderResponse> recieptOrder(@Body RecieptOrderRequest recieptOrderRequest);

    @POST("accept_order")
    Single<AcceptOrderResponse> acceptOrder(@Body AcceptOrderRequest acceptOrderRequest);

}