package com.teamup.matka.AllModules;

import static java.lang.Thread.currentThread;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamup.matka.Models.GetDataService;
import com.teamup.matka.Models.RetrofitClientInstance;
import com.teamup.matka.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class Admin {

    public static int getLineNumber() {
        return currentThread().getStackTrace()[3].getLineNumber();
    }

    public static String BASE_URL = "https://adminapp.tech/mimatka/";
    public static TinyDB tinyDB;
    public static String toolTitle = "";
    public static int digitLimit = 1;
    public static String marketId = "";
    public static String date = "";
    public static int position = 0;
    public static String selected = "";
    public static String mtype = "SD";
    public static boolean refresh_needed = false;
    public static boolean starline = false;
    public static boolean open_enbled = false;
    public static String auto_type = "sd";
    public static String paytm = "";
    public static String gpay = "";
    public static String phonepe = "";
    public static String acc_name = "";
    public static String ac_no = "";
    public static String ifsc = "";
    public static String open_end_time = "";
    public static String close_end_time = "";
    public static GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

    public static void intializeLocalRoom(Context context) {
        tinyDB = new TinyDB(context);
    }

    public static void HandleToolBar(Context context, String title, ImageView backImg, TextView titleTxt) {

        titleTxt.setText("" + title);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                OverrideNow(context);
            }
        });

    }

    public static void OverrideNow(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    static String ServerURL1 = BASE_URL + "make_it_query.php";

    public static void  makeItQuery(final String query, final Context context) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("query", query.replace("`", "")));
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL1);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    httpResponse.setHeader("Content-Type", "text/html; charset=utf-8");

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {


                } catch (Exception v) {

                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(query);
    }

    public static String addHour_no_seconds(String myTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, 0);
            cal.add(Calendar.MINUTE, 0);
            String newTime = df.format(cal.getTime());
            return newTime;
        } catch (ParseException e) {
            System.out.println(" Parsing Exception");
        }
        return null;

    }


    public static String addHour(String myTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, 0);
            cal.add(Calendar.MINUTE, 0);
            String newTime = df.format(cal.getTime());
            return newTime;
        } catch (ParseException e) {
            System.out.println(" Parsing Exception");
        }
        return null;

    }

    public static String printDifference_no_seconds(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);


        if (elapsedDays > 0) {
            String withDays = "" + elapsedDays + " day, " + elapsedHours + "hr, " + "" + elapsedMinutes + "mins";
            return withDays;
        } else {
            String withOutDays = "";
            if (elapsedHours >= 1) {
                withOutDays = "" + elapsedHours + "hr, " + "" + elapsedMinutes + "mins";
            } else {
                withOutDays = "" + elapsedMinutes + "mins";
            }
            return withOutDays;
        }

    }


    public static String printDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

//        System.out.println("startDate : " + startDate);
//        System.out.println("endDate : " + endDate);
//        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);


        if (elapsedDays > 0) {
            String withDays = "" + elapsedDays + " day, " + elapsedHours + "hr, " + "" + elapsedMinutes + "mins, " + elapsedSeconds + "sec";
            return withDays;
        } else {
            String withOutDays = "";
            if (elapsedHours >= 1) {
                withOutDays = "" + elapsedHours + "hr, " + "" + elapsedMinutes + "mins, " + elapsedSeconds + "sec";
            } else {
                withOutDays = "" + elapsedMinutes + "mins, " + elapsedSeconds + "sec";
            }
            return withOutDays;
        }

    }
}
