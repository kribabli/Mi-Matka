package com.teamup.matka.Models;

import static android.util.Log.*;
import static android.util.Log.wtf;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllActivities.MainActivity;
import com.teamup.matka.AllModules.Admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelSettings {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_settings();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        list.clear();
                        JSONObject object = new JSONObject(response.body()).getJSONObject("result");
                        if (object.length() > 0) {
//                            data available
                            MainActivity.how_to_play = object.getString("how_to_play");

                            MainActivity.min_deposite = Double.valueOf(object.getString("min_deposite"));
                            MainActivity.min_withdraw = Double.valueOf(object.getString("min_withdraw"));
                            response_data.setValue(response.body());
                        } else {
//                            No data available
                        }
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

