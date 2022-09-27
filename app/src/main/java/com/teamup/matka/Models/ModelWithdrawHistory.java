package com.teamup.matka.Models;

import static android.util.Log.wtf;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.WithdrawReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelWithdrawHistory {

    public static MutableLiveData<String> message = new MutableLiveData<>();
    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<WithdrawReq> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_withdraw_history(Admin.tinyDB.getString("userid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    try {
                        list.clear();
                        JSONObject object = new JSONObject(response.body()).getJSONObject("result");
                        if (object.length() > 0) {
//                            History available
                            JSONArray jsonArray = object.getJSONArray("history");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String id = obj.getString("id");
                                    String userid = obj.getString("userid");
                                    String withdraw = obj.getString("type");
                                    String amount = obj.getString("amount");
                                    String message = obj.getString("message");
                                    String dt = obj.getString("dt");

                                    WithdrawReq wr = new WithdrawReq(id, userid, withdraw, amount, message, dt);
                                    list.add(wr);


                                }
                            }
                            response_data.setValue(response.body());
                        } else {
//                            No history to show here
//                            message.setValue("No withdraw history available to show");

                        }
                    } catch (JSONException e) {
                        Log.wtf("Hulk-59O22", e.getMessage());
//                        message.setValue("No withdraw history available to show");

                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-63o66-err", t.getMessage());
            }
        });
    }

}

