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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beer);
    }

    public void saveBeer(View view){
        //Get all fields
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

        //Create intent

        final Intent intent = new Intent(AddBeerActivity.this, MainActivity.class);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        String requestUrl = "http://andro-beer.jsant.fr/beer/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, requestUrl,
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
                        //Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {         // Adding parameters
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
        requestQueue.add(postRequest);


    }
}
