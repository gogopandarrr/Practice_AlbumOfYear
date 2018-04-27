package com.q1.practice_albumofyear;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alfo06-07 on 2018-04-03.
 */

public class SearchActivity extends AppCompatActivity {


    Intent intent;
    ArrayList<Lists_SearchedAlbum> searchedAlbums = new ArrayList<>();
    RecyclerView searchRecycler;
    MyAdapter_Search adapterSearch;
    EditText et_search;


    String apiKey = "&api_key=0a48311e30e7a68d5c206d9566d6c4ce";
    String address = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=";
    String format= "&format=json";
    String[] coverSize =new String[4];
    final int SIZE300 = 3;
    final int SIZE174 = 2;
    final int SIZE64 = 1;
    final int SIZE34 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        intent = getIntent();
        et_search = findViewById(R.id.et_search);
        searchRecycler=findViewById(R.id.search_recycler);
        adapterSearch = new MyAdapter_Search(this, searchedAlbums);
        searchRecycler.setAdapter(adapterSearch);






        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        searchRecycler.setLayoutManager(layoutManager);


        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }
        });



        searchRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


                View child = searchRecycler.findChildViewUnder(e.getX(),e.getY());

                if(child!=null&&gestureDetector.onTouchEvent(e)){

                    int position = searchRecycler.getChildAdapterPosition(child);


                    Lists_SearchedAlbum searchedAlbum= searchedAlbums.get(position);


                    intent.putExtra("cover",searchedAlbum.getCoverXL());
                    intent.putExtra("artist",searchedAlbum.getArtist());
                    intent.putExtra("title",searchedAlbum.getTitle());
                    intent.putExtra("url",searchedAlbum.getUrl());

                    setResult(RESULT_OK, intent);

                    finish();

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {



            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });






    }//oc


    public void clickBtn(View v){


        String keyword = et_search.getText().toString();

        getJSON(keyword);

    }



    public void getJSON(final String keyword){


        if(keyword == null) return;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String result;
                StringBuilder sb = new StringBuilder();


                try {

                    URL url = new URL(address+keyword+apiKey+format);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                    if(urlConnection!=null){

                        urlConnection.setUseCaches(false);
                        urlConnection.setConnectTimeout(10000);
                        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){

                            InputStream is = urlConnection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                            BufferedReader bufferedReader = new BufferedReader(isr);


                            while(true){
                                String line = bufferedReader.readLine();
                                if(line == null)   break;

                                sb.append(line);

                            }
                                bufferedReader.close();
                                urlConnection.disconnect();
                        }
                    }
                     result = sb.toString();

                    jsonParser(result);



                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());

                }





            }
        });

        thread.start();
    }


    public void jsonParser(String jsonString){


        if(jsonString==null) return;


        try {
            JSONObject jsonObject = new JSONObject(jsonString);


            JSONObject results = jsonObject.getJSONObject("results");


            JSONObject albummatches = results.getJSONObject("albummatches");


            JSONArray albums = albummatches.getJSONArray("album");


            searchedAlbums.clear();

            for(int i=0; i < albums.length(); i++){

                JSONObject albumInfo = albums.getJSONObject(i);
                JSONArray images = albumInfo.getJSONArray("image");
                JSONObject image = images.getJSONObject(SIZE174);

                    if(!image.getString("#text").equals("")) {
                    String title = albumInfo.getString("name");
                    String artist = albumInfo.getString("artist");
                    String urlInfo = albumInfo.getString("url");


                    coverSize[SIZE174] = image.getString("#text");

                    image = images.getJSONObject(SIZE300);
                    coverSize[SIZE300] = image.getString("#text");

                    searchedAlbums.add(new Lists_SearchedAlbum(title, artist, coverSize[SIZE174], coverSize[SIZE300], urlInfo));

                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterSearch.notifyDataSetChanged();
                    }
                });
            }



        } catch (JSONException e) {

            Log.e("Exception", e.getMessage());


        }




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }



}
