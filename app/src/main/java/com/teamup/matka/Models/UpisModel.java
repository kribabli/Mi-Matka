package com.teamup.matka.Models;

import static android.util.Log.wtf;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.UpiReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpisModel {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static void load() {
        Call<String> call = Admin.service.get_upis();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    response_data.setValue(response.body());
                    try {
                        list.clear();
                        JSONArray jsonArray = new JSONObject(response.body()).getJSONArray("result");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String name = obj.getString("name");

                                list.add(name);
                            }
                        }
                    } catch (JSONException e) {
                        Log.wtf("Hulk-47", e.getMessage());
                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-57-err", t.getMessage());
            }
        });
    }

}
