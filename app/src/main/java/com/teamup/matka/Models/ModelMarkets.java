package com.teamup.matka.Models;

import static android.util.Log.wtf;

import static com.teamup.matka.AllModules.Admin.getLineNumber;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.teamup.matka.AllModules.Admin;
import com.teamup.matka.AllReqs.MarketsReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModelMarkets {

    public static MutableLiveData<String> response_data = new MutableLiveData<>();
    public static ArrayList<MarketsReq> list = new ArrayList<>();

    public static void load() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.get_markets(Admin.tinyDB.getString("userid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    wtf("Hulk-" + getClass().getName() + "-" + getLineNumber(), "" + response.raw().request().url());

                    try {
                        list.clear();
                        JSONObject ooo = new JSONObject(response.body());
                        JSONArray jsonArray = ooo.getJSONArray("result");
                        if (jsonArray.length() > 0) {
//                            data available
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = obj.getString("id");
                                String name = obj.getString("name");
                                String imgpath = obj.getString("imgpath");
                                String o_otime = obj.getString("o_otime");
                                String o_ctime = obj.getString("o_ctime");
                                String c_otime = obj.getString("c_otime");
                                String c_ctime = obj.getString("c_ctime");
                                String dt = obj.getString("dt");
                                String highlight = obj.getString("highlight");
                                String result = obj.getString("result");
                                String type = obj.getString("type");

                                MarketsReq mr = new MarketsReq(id, name, imgpath, o_otime, o_ctime, c_otime, c_ctime, dt, highlight, result, type);
                                list.add(mr);
                            }
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

