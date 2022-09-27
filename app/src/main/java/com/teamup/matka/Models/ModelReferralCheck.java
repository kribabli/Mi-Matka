package com.teamup.matka.Models;

import static android.util.Log.wtf;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllActivities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelReferralCheck {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<String> list = new ArrayList<>();

    public static void load(String referral_check) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.check_referral(referral_check);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        list.clear();
                        JSONArray object = new JSONArray(response.body());

                        response_data.setValue(response.body());

                    } catch (JSONException e) {
                        response_data.setValue("null");
                        wtf("Hulk-59", e.getMessage());
                    }

                } else {
                    //not got response
                    response_data.setValue("null");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-63-err", t.getMessage());
                response_data.setValue("null");
            }
        });
    }

}

