package com.example.ritesh.profilesneva;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FetchData fetchData;
    private ArrayList<ListData> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData = new FetchData();
        fetchData.execute();
        listView = (ListView)findViewById(R.id.profileList);
    }

    @Override
    protected void onDestroy() {
        if(fetchData.getStatus() == AsyncTask.Status.RUNNING){
            fetchData.cancel(true);
        }
        fetchData = null;
        super.onDestroy();

    }
    public class FetchData extends AsyncTask<String, Void, ArrayList<ListData>> {

        private final String LOG_TAG = FetchData.class.getSimpleName();
        FetchData(){
        }

        @Override
        protected ArrayList<ListData> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String response = null;

            try{
                String stringUrl = "https://test-api.nevaventures.com/";
                URL url = new URL(stringUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line).append("\n");
                }

                if(buffer.length() == 0){
                    return null;
                }
                response = buffer.toString();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                return null;
            }
            finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                return resultProcessing(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        private ArrayList<ListData> resultProcessing(String response) throws JSONException{
            final String KEY = "data";
            final String PHOTO_URL = "image";
            final String NAME = "name";
            final String SKILLS = "skills";
            JSONObject rawJson = new JSONObject(response);
            JSONArray dataArray = rawJson.getJSONArray(KEY);
            for(int i =0; i< dataArray.length();i++){
                JSONObject temp = dataArray.getJSONObject(i);
                ListData listData = new ListData();
                listData.setPersonName(temp.getString(NAME));
                listData.setSkills(temp.getString(SKILLS));
                Bitmap btmp = getBitmapFromURL(temp.getString(PHOTO_URL));
                System.out.println("------------------->>"+btmp);
                listData.setImgAddress(getCircularBitmap(btmp));
                arrayList.add(listData);
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList) {
                listView.setAdapter(new ProfileBaseAdapter(MainActivity.this, arrayList));
            Log.d(LOG_TAG,arrayList.toString());
        }

    }
    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    public Bitmap getCircularBitmap(Bitmap bitmap) {
        if(bitmap == null){
            return null;
        }
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
