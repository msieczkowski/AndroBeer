package com.example.lp.beer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.lp.beer.BeerDetails";
   // EditText etGitHubUser; // This will be a reference to our GitHub username input.
    //Button btnGetRepos;  // This is a reference to the "Get Repos" button.
    TextView tvBeerEmpty;  // This will reference our repo list text box.
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    public static final String API_BASEURL = "http://andro-beer.jsant.fr/beer/";  // This is the API base URL (GitHub API)
    String url;  // This will hold the full URL which will include the username entered in the etGitHubUser.
    private List<Beer> beers = new ArrayList<Beer>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // This is some magic for Android to load a previously saved state for when you are switching between actvities.
        setContentView(R.layout.activity_main);  // This links our code to our layout which we defined earlier.
        mListView = (ListView) findViewById(R.id.listView);
        tvBeerEmpty = (TextView) findViewById(R.id.beerEmpty);

        if(!isConnectingToInternet(MainActivity.this))
        {
            Toast.makeText(getApplicationContext(),"Veuillez vérifier votre connexion à internet", Toast.LENGTH_LONG).show();
        }

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        this.getRepoList();


        BeerAdapter adapter = new BeerAdapter(MainActivity.this, this.beers);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Beer beer = (Beer) mListView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, BeerDetailsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, beer.getId());
                startActivity(intent);
            }
        });
    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.tvBeerEmpty.setText(str);
    }

    private void getRepoList() {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        this.url = API_BASEURL;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
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
                                    String Id = jsonObj.get("_id").toString();
                                    String Name = jsonObj.get("name").toString();
                                    String Degree = jsonObj.get("degree").toString();
                                    String Description = jsonObj.get("description").toString();
                                    String Origin = jsonObj.get("origin").toString();
                                    String Price = jsonObj.get("price").toString();
                                    String Type = jsonObj.get("type").toString();
                                    String ImgUrl = jsonObj.get("img_url").toString();
                                    Beer aBeer = new Beer(Id, Name, Degree, Description, Origin, Price, Type, ImgUrl);
                                    addBeerToList(aBeer);
                                    //addToRepoList(Name, Price);
                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            // The user didn't have any repos.
                            setRepoListText("Il n'y a pas de bière à afficher :(");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        setRepoListText("Veuillez vous connecter à internet pour lister les bières");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }

    public void addBeerToList(Beer aBeer)
    {
        this.beers.add(aBeer);
    }

    public void addBeerActivity(View view){
        final Intent intent = new Intent(MainActivity.this, AddBeerActivity.class);
        startActivity(intent);
    }

    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
}
