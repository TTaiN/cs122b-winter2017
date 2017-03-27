package tk.cs122bgroup42.fabflix;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.NetworkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity
{
    public final static String PREF_EMAIL = "tk.cs122group42.PREF_EMAIL";
    public final static String EMAIL = "tk.cs122group42.EMAIL";

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        URL = getString(R.string.login_url_default);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = this.getSharedPreferences(getResources().getString(R.string.preferences), Context.MODE_PRIVATE);
        String email = prefs.getString(Login.PREF_EMAIL, null);
        if (email != null && !email.isEmpty())
        {
            EditText emailText = (EditText) findViewById(R.id.username);
            emailText.setText(email);
        }

        /*
        Intent intent = getIntent();
        String previousEmail = intent.getStringExtra(Login.PREVIOUS_EMAIL);
        if (previousEmail != null && !previousEmail.isEmpty())
        {
            EditText searchText = (EditText) findViewById(R.id.username);
            searchText.setText(previousEmail);
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Login button */
    public void login(View view)
    { // Source: http://stackoverflow.com/questions/13398104/android-how-to-put-a-delay-after-a-button-is-pushed
        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setEnabled(false);
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
                        Button loginButton = (Button) findViewById(R.id.login);
                        loginButton.setEnabled(true);
                    }
                });
            }
        }, 1000);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(buildHttpRequest());
    }

    private StringRequest buildHttpRequest()
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            Intent intent = new Intent(getApplicationContext(), Welcome.class);
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getBoolean("login"))
                            {
                                SharedPreferences prefs = getApplicationContext().getSharedPreferences(getResources().getString(R.string.preferences), Context.MODE_PRIVATE);
                                intent.putExtra(Login.EMAIL, (String) jsonResponse.get("email"));
                                prefs.edit().putString(Login.PREF_EMAIL, (String) jsonResponse.get("email")).apply();
                                startActivity(intent);
                                finish();
                            }
                            else if (!jsonResponse.getBoolean("login") && jsonResponse.getString("error").isEmpty())
                            {
                                Toast.makeText(getApplicationContext(), "Incorrect username and/or password.", Toast.LENGTH_SHORT).show();
                            }
                            else if  (!jsonResponse.getBoolean("login") && !jsonResponse.getString("error").isEmpty())
                            {
                                String error = jsonResponse.getString("error");
                                Toast.makeText(getApplicationContext(), "Response from server: " + error, Toast.LENGTH_LONG).show();
                            }
                            System.out.println(response);
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext(), "The response from the server was in an unknown format. (non-JSON)", Toast.LENGTH_SHORT).show();
                            System.out.println(e.toString());
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
                            Toast.makeText(getApplicationContext(), "The server rejected your login request. (404)", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was an unknown error with your request. ("+networkResponse.statusCode+")", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                EditText editText = (EditText) findViewById(R.id.username);
                String username = editText.getText().toString();
                editText = (EditText) findViewById(R.id.password);
                String password = editText.getText().toString();

                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile", "true");
                params.put("email", username);
                params.put("pwd", password);
                return params;
            }
        };
        return stringRequest;
    }
}
