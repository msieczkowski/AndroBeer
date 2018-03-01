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

public class EditBeerActivity extends AppCompatActivity {
    private String beer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_beer);

        Intent intent = getIntent();
        final String beerId  = intent.getStringExtra(BeerDetailsActivity.EXTRA_MESSAGE);
        beer = beerId;
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

        String requestUrl = "http://192.168.240.4:3000/beer/";
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
