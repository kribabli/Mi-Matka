package com.teamup.matka.Models;

import static android.util.Log.wtf;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.TransactionReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelTransactions {

    public static MutableLiveData<String> message = new MutableLiveData<>();
    public static MutableLiveData<String> balance = new MutableLiveData<>();
    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<TransactionReq> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_transactions(Admin.tinyDB.getString("userid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        list.clear();
                        JSONObject oo = new JSONObject(response.body());
                        JSONArray jsonArray = oo.getJSONObject("result").getJSONArray("history");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = obj.getString("id");
                                String userid = obj.getString("userid");
                                String amount = obj.getString("amount");
                                String message = obj.getString("message");
                                String dt = obj.getString("dt");

                                TransactionReq tr = new TransactionReq(id, userid, amount, message, dt);
                                list.add(tr);

                            }

                            response_data.setValue(response.body());
                        } else {
//                            No data avaial
                            message.setValue("No data avaialable");
                        }


                    } catch (JSONException e) {
                        Log.wtf("Hulk-h88", e.getMessage());
                        message.setValue("No data avaialable");

                    }

                } else {
                    //not got response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                wtf("Hulk-63H33-err", t.getMessage());
            }
        });
    }

}

