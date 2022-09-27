package com.teamup.matka.Models;

import static android.util.Log.wtf;

import static com.teamup.matka.AllModules.Admin.getLineNumber;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.app_sync.AppSyncTextUtils;
import com.teamup.matka.AllActivities.MainActivity;
import com.teamup.matka.AllModules.Admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelSettingsValue {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_settings_value();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    try {
                        list.clear();
                        JSONArray obj = new JSONArray(response.body());
                        MainActivity.whatsapp_mobile_number = obj.getJSONObject(0).getString("value");


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

