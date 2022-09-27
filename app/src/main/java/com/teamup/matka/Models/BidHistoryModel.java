package com.teamup.matka.Models;

import static android.util.Log.wtf;

import static com.teamup.matka.AllModules.Admin.getLineNumber;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.BidHistoryReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BidHistoryModel {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<BidHistoryReq> list = new ArrayList<>();

    public static void load(String start_date, String end_date) {
        Call<String> call = Admin.service.get_bid_history(Admin.tinyDB.getString("userid"), start_date, end_date);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.wtf("Hulk-" + getClass().getName() + "-" + getLineNumber(), "" + response.raw().request().url().toString());
                    response_data.setValue(response.body());
                    try {
                        list.clear();
                        JSONArray jsonArray = new JSONObject(response.body()).getJSONArray("result");
                        if (jsonArray.length() > 0) {
//                            data available
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = obj.getString("id");
                                String name = obj.getString("marketName");
                                String type = obj.getString("type");
                                String message = "Digit : " + obj.getString("digit");
                                String amount = obj.getString("points");
                                String userid = obj.getString("userid");
                                String dt = obj.getString("dt");
                                String digit = obj.getString("digit");
                                String mtype = obj.getString("mtype");
                                String pana = obj.getString("pana");
                                String win = obj.getString("win");
                                String winAmt = obj.getString("winAmt");

                                BidHistoryReq sgr = new BidHistoryReq(id, name, type, message, amount, userid, dt, pana, mtype, win, winAmt);
                                list.add(sgr);

                            }

                        } else {
//                            No data available
                        }
                    } catch (JSONException e) {
                        wtf("Hulk-63", e.getMessage() + "\n" + response.body());
                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-63-err", t.getMessage());
            }
        });
    }

}
