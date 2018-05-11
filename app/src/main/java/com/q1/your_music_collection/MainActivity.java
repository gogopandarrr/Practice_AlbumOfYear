package com.q1.your_music_collection;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Prompt.SIGN_IN;


public class MainActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener, View.OnClickListener, DiscreteScrollView.ScrollListener{

    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    ArrayList<Lists_Collection> collections= new ArrayList<>();
    ArrayList<Lists_Album> listsAlbums= new ArrayList<>();
    DiscreteScrollView discreteScrollView;
    TextView title, subTitle;
    MyAdapter_Main adapterMain;
    JSONObject obj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);


        mAuth = FirebaseAuth.getInstance();
        title = findViewById(R.id.tv_title_Collection);
        subTitle = findViewById(R.id.tv_subTitle);
        discreteScrollView = findViewById(R.id.myCollections);
        discreteScrollView.setOrientation(DSVOrientation.HORIZONTAL);
        discreteScrollView.addOnItemChangedListener(this);
        adapterMain= new MyAdapter_Main(this, collections);
        discreteScrollView.setAdapter(adapterMain);
        discreteScrollView.setItemTransitionTimeMillis(200);
        discreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
        discreteScrollView.setOverScrollEnabled(true);
        discreteScrollView.setSlideOnFling(true);
        discreteScrollView.removeItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                collections.remove(adapterPosition);

            }
        });



    }

    private void setGoogleLogin(){
        FirebaseAuth.getInstance().signOut();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                            .requestIdToken("1091521200293-b8kjbla50kdngdcttod92iuqi3i1idt8.apps.googleusercontent.com")
                                                            .requestEmail()
                                                            .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this/* FragmentActivity */,
                new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }/* OnConnectionFailedListener */

                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }

    private void loginGoogle(){

        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent, SIGN_IN);


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
// ...
                    }
                });




    }


    public void clickMake(View v){

        Intent intent =  new Intent(this, MakeActivity.class);

        startActivityForResult(intent, 1);

    }

    public void clickCommunity(View v){

        Intent intent = new Intent(this, CommunityActivity.class);


        startActivityForResult(intent, 30);


    }

    public void clickEdit(View v){
        int position = discreteScrollView.getCurrentItem();
        boolean modify = true;

        Intent intent = new Intent(this, MakeActivity.class);
        intent.putParcelableArrayListExtra("listAlbums",collections.get(position).getListsAlbums());
        intent.putExtra("nameList",collections.get(position).getNameList());
        intent.putExtra("modify", modify);
        intent.putExtra("position",position);

        startActivityForResult(intent, 20);
    }



    public void clickDel(View v){

        int position = discreteScrollView.getCurrentItem();

        discreteScrollView.setAdapter(null);
        collections.remove(position);

        adapterMain= new MyAdapter_Main(this, collections);
        discreteScrollView.setAdapter(adapterMain);
        if(position>0)  discreteScrollView.smoothScrollToPosition(position-1);
    }


    public void listToJSON(int position){

        obj = new JSONObject();

        try {

        JSONArray jarray = new JSONArray();

        for (int i = 0; i < collections.get(position).getListsAlbums().size(); i++){

            JSONObject innerObj = new JSONObject();


                innerObj.put("artist", collections.get(position).getListsAlbums().get(i).getArtist());
                innerObj.put("album", collections.get(position).getListsAlbums().get(i).getAlbum());
                innerObj.put("cover", collections.get(position).getListsAlbums().get(i).getCover());
                innerObj.put("rank",collections.get(position).getListsAlbums().get(i).getRankImg());
                if(collections.get(position).getListsAlbums().get(i).getOpinion()!=null)
                innerObj.put("opinion",collections.get(position).getListsAlbums().get(i).getOpinion());
                else
                    innerObj.put("opinion","None");
                innerObj.put("info", collections.get(position).getListsAlbums().get(i).getUrl());

                jarray.put(innerObj);

        }//for

            obj.put("albumLists",jarray);



        }catch (JSONException e) {
            e.printStackTrace();
        }



    }


    public void clickUpload(View v){

        int position = discreteScrollView.getCurrentItem();

        listToJSON(position);



        String serverUrl="http://gogopanda.dothome.co.kr/yourCollections/insertDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(MainActivity.this).setMessage(response).setPositiveButton("ok", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        String title = collections.get(position).getNameList();
        multiPartRequest.addStringParam("MyJson",obj.toString());
        multiPartRequest.addStringParam("Title", title);

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        requestQueue.add(multiPartRequest);



    }







    @Override
    public void onClick(View view) {


    }


    @Override
    public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {


    }



    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

        if(adapterPosition>=0)  title.setText(collections.get(adapterPosition).getNameList());


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==1){

            listsAlbums = data.getParcelableArrayListExtra("myList");

            collections.add(new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemInserted(collections.size());

        }else if(resultCode==RESULT_OK&&requestCode==20){

            int position = data.getIntExtra("position",0);

            listsAlbums = data.getParcelableArrayListExtra( "myList");

            collections.set(position, new Lists_Collection(listsAlbums, data.getStringExtra("nameList")));

            adapterMain.notifyItemChanged(position);
        }else if(requestCode == SIGN_IN){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result!=null){

                if(result.isSuccess()){

                    GoogleSignInAccount acct = result.getSignInAccount();


                    String personName = acct.getDisplayName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    String tokenKey = acct.getServerAuthCode();

                    mGoogleApiClient.disconnect();

                }else {

                    Log.e("GoogleLogin", "login fail cause=" + result.getStatus().getStatusMessage());
                }
            }


        }//else if



    }


}
