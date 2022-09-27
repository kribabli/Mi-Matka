package com.teamup.matka.Models;

import static android.util.Log.wtf;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelGetProfile {

    public static MutableLiveData<String> user_balance = new MutableLiveData<>();

    public static void load() {
        Call<String> call = Admin.service.get_profile(Admin.tinyDB.getString("userid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject ooo = new JSONObject(response.body()).getJSONObject("result");
                        String balance = ooo.getString("balance");
                        user_balance.setValue(balance);


                    } catch (JSONException e) {
                        Log.wtf("Hulk-59", e.getMessage());
                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-48-err", t.getMessage());
            }
        });
    }

}
