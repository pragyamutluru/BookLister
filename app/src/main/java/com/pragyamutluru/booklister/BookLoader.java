package com.pragyamutluru.booklister;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suresh on 7/2/18.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String queryString;
    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        this.queryString=queryString;
    }



    @Override
    protected void onStartLoading() {
       // Log.i(LOG_TAG,"Loader onStartLoading");
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
       // Log.i(LOG_TAG,"Loader loadInBack started");
        URL url= null;
        try {

            url = createURL(queryString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        Log.i("HELLO",jsonResponse);
        List<Book> result= QueryUtils.extractBooks(jsonResponse);
        Log.i("RESULT_SIZE", Integer.toString(result.size()));
        return result;
    }

    private URL createURL(String stringURL) throws MalformedURLException {
        URL url= new URL(stringURL);
        return url;
    }

    private String makeHttpRequest(URL url){
       // Log.i(LOG_TAG,"Loader makeHttpReq");
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);}
            else{
                Log.i("RES_CODE",Integer.toString(urlConnection.getResponseCode()));
            }





        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.i("HELLO",jsonResponse.toString());
        return jsonResponse;

    }
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.i("HELLO",output.toString());
        return output.toString();
    }

}



