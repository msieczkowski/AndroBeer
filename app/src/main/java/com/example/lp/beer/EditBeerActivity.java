package com.example.lp.beer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.lp.beer.MainActivity.API_BASEURL;

public class EditBeerActivity extends AppCompatActivity {
    private String beer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_beer);

        Intent intent = getIntent();
        final String beerId  = intent.getStringExtra(BeerDetailsActivity.EXTRA_MESSAGE);
        beer = beerId;
        String requestUrl = API_BASEURL;
        requestUrl +=  beerId;

        final EditText BeerNameField = (EditText) findViewById(R.id.editTextName);
        final EditText BeerDescriptionField = (EditText) findViewById(R.id.editTextDescription);
        final EditText BeerOriginField = (EditText) findViewById(R.id.editTextOrigin);
        final EditText BeerTypeField = (EditText) findViewById(R.id.editTextType);
        final EditText BeerPriceField = (EditText) findViewById(R.id.editTextPrice);
        final EditText BeerDegreeField = (EditText) findViewById(R.id.editTextDegree);
        final EditText BeerImgUrlField = (EditText) findViewById(R.id.editTextImgUrl);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

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
                                    BeerNameField.setText(aBeer.getName());
                                    BeerTypeField.setText(aBeer.getType());
                                    BeerDescriptionField.setText(aBeer.getDescription());
                                    BeerDegreeField.setText(aBeer.getDegree());
                                    BeerPriceField.setText(aBeer.getPrice());
                                    BeerOriginField.setText(aBeer.getOrigin());
                                    BeerImgUrlField.setText(aBeer.getImgUrl());

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

    public void editBeer(View view){

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        EditText BeerNameField = (EditText) findViewById(R.id.editTextName);
        EditText BeerDescriptionField = (EditText) findViewById(R.id.editTextDescription);
        EditText BeerOriginField = (EditText) findViewById(R.id.editTextOrigin);
        EditText BeerTypeField = (EditText) findViewById(R.id.editTextType);
        EditText BeerPriceField = (EditText) findViewById(R.id.editTextPrice);
        EditText BeerDegreeField = (EditText) findViewById(R.id.editTextDegree);
        EditText BeerImgUrlField = (EditText) findViewById(R.id.editTextImgUrl);

        //Get value of all field

        final String BeerName = BeerNameField.getText().toString();
        final String BeerDescription = BeerDescriptionField.getText().toString();
        final String BeerOrigin = BeerOriginField.getText().toString();
        final String BeerType = BeerTypeField.getText().toString();
        final String BeerPrice = BeerPriceField.getText().toString();
        final String BeerDegree = BeerDegreeField.getText().toString();
        final String BeerImgUrl = BeerImgUrlField.getText().toString();

        String requestUrl = API_BASEURL;
        requestUrl += beer;
        final Intent intent =  new Intent(EditBeerActivity.this, MainActivity.class);
        StringRequest putRequest = new StringRequest(Request.Method.PUT, requestUrl,
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
                        // error
                        Log.d("Error.Response", "erreur");
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", BeerName);
                params.put("description", BeerDescription);
                params.put("origin", BeerOrigin);
                params.put("type", BeerType);
                params.put("price", BeerPrice);
                params.put("degree", BeerDegree);
                params.put("img_url", BeerImgUrl);
                return params;
            }

        };

        requestQueue.add(putRequest);
    }
}
