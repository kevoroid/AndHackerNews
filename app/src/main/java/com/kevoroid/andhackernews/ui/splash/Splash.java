package com.kevoroid.andhackernews.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.kevoroid.andhackernews.ui.main.MainActivity;

public class Splash extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}