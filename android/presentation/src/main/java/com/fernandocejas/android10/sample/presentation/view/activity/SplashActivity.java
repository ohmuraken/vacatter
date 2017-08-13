package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Created by apple on 2017/08/13.
 */

public class SplashActivity extends BaseActivity{
  private Handler handler = new Handler();
  private Runnable execute_intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    execute_intent = new Runnable() {
      @Override
      public void run() {
        Intent intent;
        SharedPreferences prefer = getSharedPreferences("twitter", getApplicationContext().MODE_PRIVATE);
        String token = prefer.getString("token", "");
        if (!token.equals("")) {
          intent = new Intent(SplashActivity.this, TimeLineActivity.class);
        } else {
          intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();

      }
    };

    handler.postDelayed(execute_intent, 3000);

  }
}
