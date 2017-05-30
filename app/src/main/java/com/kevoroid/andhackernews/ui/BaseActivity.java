package com.kevoroid.andhackernews.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kevoroid.andhackernews.AndHackerNewsController;
import com.kevoroid.andhackernews.R;

/**
 * Created by kevin on 5/27/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_about:
                Toast.makeText(this, "Hey this is sample HackerNew app for PropertyGuru!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_menu_settings:
                Toast.makeText(this, "Bingo! you reached Settings, leave a message after the BEEP!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AndHackerNewsController.getInstance(this).getRequestQueue().cancelAll("GET");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
