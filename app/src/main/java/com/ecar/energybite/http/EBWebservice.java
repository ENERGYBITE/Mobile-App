package com.ecar.energybite.http;


import com.ecar.energybite.AddVehicleResponse;
import com.ecar.energybite.BookingListResponse;
import com.ecar.energybite.BookingResponse;
import com.ecar.energybite.ChargerStationResponse;
import com.ecar.energybite.EVLocationResponse;
import com.ecar.energybite.EVTemplateResponse;
import com.ecar.energybite.LoginResponse;
import com.ecar.energybite.UserRegisterResponse;
import com.ecar.energybite.UserVehicleResponse;
import com.ecar.energybite.booking.PaymentModel;
import com.ecar.energybite.eVehicle.UserVehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EBWebservice {

    String BASE_URL = "https://vapt.energybite.in/energybite/api/app/";
//    String BASE_VERSION_URL = BASE_URL;

    @POST("appuserauth")
    Call<LoginResponse> login(@Body LoginResponse login);

    @POST("appuser")
    Call<UserRegisterResponse> registerUser(@Body UserRegisterResponse registerResponse);

    @GET("stationsnearlocation")
    Call<EVLocationResponse> getEVLocations(@Query("lattitude") String lattitude, @Query("longitude") String longitude);

    @GET("chargersbystation")
    Call<ChargerStationResponse> getChargerStationDetail(@Query("stationId") String stationId);

    @GET("getpasswordlink")
    Call<UserVehicleResponse> getPasswordLink(@Query("username") String username);

    @GET("userevs")
    Call<UserVehicleResponse> getUserVehicles(@Query("username") String username);

    @GET("evtemplates")
    Call<EVTemplateResponse> getEVTemplates();

    @POST("addupdateev")
    Call<AddVehicleResponse> addUserEV(@Body UserVehicle userVehicle);

    @POST("booking")
    Call<BookingResponse> book(@Body BookingResponse bookingResponse);

    @POST("payment")
    Call<BookingResponse> makePayment(@Body PaymentModel paymentModel);

    @GET("bookingstatus")
    Call<BookingResponse> getBookingStatus(@Query("bookingId") String bookingId);

    @GET("bookinglist")
    Call<BookingListResponse> getBookingList(@Query("username") String username);

}
