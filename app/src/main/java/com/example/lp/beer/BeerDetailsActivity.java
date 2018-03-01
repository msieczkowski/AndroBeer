package com.example.lp.beer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

public class BeerDetailsActivity extends AppCompatActivity {
    private Beer beer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        final String beerId  = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final TextView BeerTitle = (TextView)findViewById(R.id.textViewTitle);
        final TextView BeerType = (TextView)findViewById(R.id.textViewType);
        final TextView BeerDescription = (TextView)findViewById(R.id.textViewDescription);
        final TextView BeerDegree = (TextView)findViewById(R.id.textViewDegree);
        final TextView BeerPrice = (TextView)findViewById(R.id.textViewPrice);
        final TextView BeerOrigin = (TextView)findViewById(R.id.textViewOrigin);
        final ImageView imgUrl = (ImageView)findViewById(R.id.imageBeer);


        String requestUrl = "http://192.168.240.4:3000/beer/";
        requestUrl +=  beerId;

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, requestUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String Id = beerId;
                                    String Name = jsonObj.get("name").toString();
                                    String Degree = jsonObj.get("degree").toString();
                                    String Description = jsonObj.get("description").toString();
                                    String Origin = jsonObj.get("origin").toString();
                                    String Price = jsonObj.get("price").toString();
                                    String Type = jsonObj.get("type").toString();
                                    String ImgUrl = jsonObj.get("img_url").toString();
                                    Beer aBeer = new Beer(Id, Name, Degree, Description, Origin, Price, Type, ImgUrl);
                                    beer = aBeer;
                                    BeerTitle.setText(aBeer.getName());
                                    BeerType.setText(aBeer.getType());
                                    BeerDescription.setText(aBeer.getDescription());
                                    BeerDegree.setText(aBeer.getDegree()+" °");
                                    BeerPrice.setText(aBeer.getPrice()+" €");
                                    BeerOrigin.setText(aBeer.getOrigin());
                                    Picasso.with(BeerDetailsActivity.this).load(aBeer.getImgUrl()).into(imgUrl);

                                    //BeerTitle.setText(aBeer.getName());
                                    //addToRepoList(Name, Price);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            // The user didn't have any repos.
                            //setRepoListText("No repos found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        //setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);



    }

    public void deleteBeer(View view){
        final Intent intent = new Intent(BeerDetailsActivity.this, MainActivity.class);
        String id = beer.getId();
        String requestUrl = "http://192.168.240.4:3000/beer/";
        requestUrl += id;
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, requestUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Log.d("error", "erreur");
                    }
                }
        );
        requestQueue.add(deleteRequest);
    }

}