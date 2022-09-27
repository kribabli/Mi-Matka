package com.teamup.matka.Models;

import static android.util.Log.*;
import static android.util.Log.wtf;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.matka.AllModules.Admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelBank {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_bank(Admin.tinyDB.getString("userid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    try {
                        list.clear();
                        JSONObject obj = new JSONObject(response.body()).getJSONObject("result");
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("paytm"))) {
                            Admin.paytm = obj.getString("paytm");
                        }
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("upi"))) {
                            Admin.gpay = obj.getString("upi");
                        }
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("phonepe"))) {
                            Admin.phonepe = obj.getString("phonepe");
                        }
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("name"))) {
                            Admin.acc_name = obj.getString("name");
                        }
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("ac_no"))) {
                            Admin.ac_no = obj.getString("ac_no");
                        }
                        if (AppSyncTextUtils.check_empty_and_null(obj.getString("ifsc"))) {
                            Admin.ifsc = obj.getString("ifsc");
                        }

                        response_data.setValue(response.body());
                    } catch (JSONException e) {
                        wtf("Hulk-59", e.getMessage());
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

