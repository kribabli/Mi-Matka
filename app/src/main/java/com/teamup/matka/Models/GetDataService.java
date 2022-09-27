package com.teamup.matka.Models;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("api/anouncment")
    Call<String> get_anouncements();

    @GET("api/banners")
    Call<String> get_top_data();

    @GET("api/settings")
    Call<String> get_settings();

    @POST("api/bidHistory")
    Call<String> get_bid_history(@Query("userid") String userid,
                                 @Query("sdate") String sdate,
                                 @Query("edate") String edate);

    @POST("api/transactions")
    @FormUrlEncoded
    Call<String> get_withdraw_history(@Field("userid") String userid);

    @POST("api/getBank")
    @FormUrlEncoded
    Call<String> get_bank(@Field("userid") String userid);

    @POST("api/transactions")
    @FormUrlEncoded
    Call<String> get_transactions(@Field("userid") String userid);

    @POST("api/winningHistory")
    @FormUrlEncoded
    Call<String> get_winning_history(@Field("userid") String userid);

    @GET("api/getProfile")
    Call<String> get_profile(@Query("userid") String userid);

    @GET("api/markets")
    Call<String> get_markets(@Query("userid") String userid);

    @GET("api/upis")
    Call<String> get_upis();

    @GET("all_apis.php?func=get_settings&type=whatsapp")
    Call<String> get_settings_value();

    @GET("all_apis.php?func=check_referral")
    Call<String> check_referral(@Query("referral_check") String referral_check);
}