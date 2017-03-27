package tk.cs122bgroup42.fabflix;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchResult extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String movie_title = intent.getStringExtra(Welcome.SEARCH_FOR);

        TextView searchResultsFor = (TextView) findViewById(R.id.search_results_for);
        TextView searchResults = (TextView) findViewById(R.id.search_results);

        ArrayList<String> movies = intent.getStringArrayListExtra(Welcome.SEARCH_RESULTS);
        if (movies != null)
        {
            searchResultsFor.setText("Showing " + movies.size() + " result(s) for \"" + movie_title + "\"");
            String result = "";
            int counter = 1;

            for (String movie : movies)
            {
                result += ((counter++) + ". " + movie + System.lineSeparator() + System.lineSeparator());
            }
            searchResults.setText(result);
        }
        else
        {
            searchResultsFor.setText("No results found for \"" + movie_title + "\"");
        }
    }

    public void back(View view) // Custom Back Button
    { // Source: http://stackoverflow.com/questions/13398104/android-how-to-put-a-delay-after-a-button-is-pushed
        Button backButton = (Button) findViewById(R.id.search_results_back);
        backButton.setEnabled(false);
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
                        Button backButton = (Button) findViewById(R.id.search_results_back);
                        backButton.setEnabled(true);
                    }
                });
            }
        }, 1000);

        Intent intent = getIntent();
        String email = intent.getStringExtra(Login.EMAIL);
        String searchFor = intent.getStringExtra(Welcome.SEARCH_FOR);
        intent = new Intent(getApplicationContext(), Welcome.class);
        intent.putExtra(Login.EMAIL, email);
        intent.putExtra(Welcome.SEARCH_FOR, searchFor);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = getIntent();
        String email = intent.getStringExtra(Login.EMAIL);
        String searchFor = intent.getStringExtra(Welcome.SEARCH_FOR);
        intent = new Intent(getApplicationContext(), Welcome.class);
        intent.putExtra(Login.EMAIL, email);
        intent.putExtra(Welcome.SEARCH_FOR, searchFor);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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
}
