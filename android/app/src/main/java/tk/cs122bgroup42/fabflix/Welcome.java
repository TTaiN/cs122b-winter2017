package tk.cs122bgroup42.fabflix;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class Welcome extends AppCompatActivity
{
    public final static String SEARCH_FOR = "tk.cs122bgroup42.SEARCH_FOR";
    public final static String SEARCH_RESULTS = "tk.cs122bgroup42.SEARCH_RESULTS";

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        URL = getResources().getString(R.string.search_url_default);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        String email = intent.getStringExtra(Login.EMAIL);
        TextView userWelcome = (TextView) findViewById(R.id.welcome_username);
        userWelcome.setText("Welcome, " + email + "!");

        String searchFor = intent.getStringExtra(Welcome.SEARCH_FOR);
        if (searchFor != null && !searchFor.isEmpty())
        {
            EditText searchText = (EditText) findViewById(R.id.movieTitleSearch);
            searchText.setText(searchFor);
        }
    }

    @Override
    public void onBackPressed()
    { // http://stackoverflow.com/questions/6413700/android-proper-way-to-use-onbackpressed-with-toast
        new AlertDialog.Builder(this)
                .setTitle("Go back to login page?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Welcome.super.onBackPressed();
                        //Intent intent = getIntent();
                        //String email = intent.getStringExtra(Login.EMAIL);
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        //intent.putExtra(Login.PREVIOUS_EMAIL, email);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.about_us)
        {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.about_us))
                    .setMessage("CS 122B Group 42 | (Winter 2017)" + System.lineSeparator() + "----------------------------------------------------" + System.lineSeparator() +
                            "Thomas T Nguyen" + System.lineSeparator() + "Mohammed Adel Alabdullatif" + System.lineSeparator() + "Yolie Saepung")
                    .setPositiveButton(android.R.string.yes, null).create().show();
            return true;
        }
        else if (id == R.id.logout)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Go back to login page?")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {
                            //Intent intent = getIntent();
                            //String email = intent.getStringExtra(Login.EMAIL);
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            //intent.putExtra(Login.PREVIOUS_EMAIL, email);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(View view)
    { // Credit: http://stackoverflow.com/questions/13398104/android-how-to-put-a-delay-after-a-button-is-pushed
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setEnabled(false);
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Button searchButton = (Button) findViewById(R.id.search_button);
                        searchButton.setEnabled(true);
                    }
                });
            }
        }, 1000);

        EditText editText = (EditText) findViewById(R.id.movieTitleSearch);
        String movieTitle = editText.getText().toString().trim().replace("\"", "");

        if (!movieTitle.isEmpty())
        {
            try
            {
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(buildHttpRequest(movieTitle));
            }
            catch (UnsupportedEncodingException e)
            {
                Toast.makeText(this, "Please enter a valid movie title to search. (Unsupported Characters)", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Please enter a valid movie title to search.", Toast.LENGTH_SHORT).show();
        }
    }

    private StringRequest buildHttpRequest(String movieTitle) throws UnsupportedEncodingException
    {
        String newURL = URL + URLEncoder.encode(movieTitle, "UTF-8");
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                newURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            Intent intent = getIntent();
                            String email = intent.getStringExtra(Login.EMAIL);

                            intent = new Intent(getApplicationContext(), SearchResult.class);
                            JSONObject jsonResponse = new JSONObject(response);
                            String movieTitle = jsonResponse.getString("search");
                            ArrayList<String> movies = new ArrayList<String>();

                            if (jsonResponse.getInt("results") != 0)
                            {
                                JSONArray jsonMovies = jsonResponse.getJSONArray("movies");
                                for (int i = 0; i != jsonMovies.length(); i++)
                                {
                                    movies.add(jsonMovies.getString(i));
                                }
                                intent.putExtra(Welcome.SEARCH_FOR, movieTitle);
                                intent.putExtra(Login.EMAIL, email);
                                intent.putStringArrayListExtra(Welcome.SEARCH_RESULTS, movies);
                                startActivity(intent);
                                finish();
                            }
                            else if (jsonResponse.getInt("results") == 0)
                            {
                                intent.putExtra(Welcome.SEARCH_FOR, movieTitle);
                                intent.putExtra(Login.EMAIL, email);
                                intent.putStringArrayListExtra(Welcome.SEARCH_RESULTS, null);
                                startActivity(intent);
                                finish();
                            }
                            else if (jsonResponse.getInt("results") < 0) // An error occurred
                            {
                                Toast.makeText(getApplicationContext(), "Response from server: " + jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "The response from the server was in an unknown format. (non-JSON)", Toast.LENGTH_SHORT).show();
                            System.out.println(response.toString());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse == null)
                        {
                            Toast.makeText(getApplicationContext(), "The server could not be reached.", Toast.LENGTH_SHORT).show();
                        }
                        else if (networkResponse.statusCode == HttpURLConnection.HTTP_NOT_FOUND)
                        {
                            Toast.makeText(getApplicationContext(), "The server rejected your search request. (404)", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was an unknown error with your search request. ("+networkResponse.statusCode+")", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        return stringRequest;
    }
}
