package com.teamup.matka.Models;

import static android.util.Log.*;
import static android.util.Log.wtf;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelTopData {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static MutableLiveData<String> message = new MutableLiveData<>();
    public static ArrayList<String> img_list = new ArrayList<>();
    public static ArrayList<String> desc_list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_top_data();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        img_list.clear();
                        desc_list.clear();
                        JSONObject obj = new JSONObject(response.body());
                        JSONArray jsonArray = obj.getJSONArray("result");
                        if (obj.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject oo = jsonArray.getJSONObject(i);
                                String imgpath = oo.getString("imgpath");
                                String description = oo.getString("description");
                                img_list.add(imgpath);
                                desc_list.add(description);

                            }

                            response_data.setValue(response.body());
                        } else {
                            message.setValue("no data found");
                        }
                    } catch (JSONException e) {
                        wtf("Hulk-592f", e.getMessage());
                        message.setValue("no data found");
                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-635T-err", t.getMessage());
            }
        });
    }

}

