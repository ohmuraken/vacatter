package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.AppLaunchChecker;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Created by apple on 2017/08/13.
 */

public class SplashActivity extends BaseActivity {
  private Handler handler = new Handler();
  private Runnable execute_intent;
  private Intent intent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    final boolean status = AppLaunchChecker.hasStartedFromLauncher(this);
    AppLaunchChecker.onActivityCreate(this);

    execute_intent = new Runnable() {
      @Override public void run() {

        if (status) {
          intent = new Intent(SplashActivity.this, TimeLineActivity.class);
        } else {
          intent = new Intent(SplashActivity.this, TutorialActivity.class);
        }
        startActivity(intent);
        finish();
      }
    };

    handler.postDelayed(execute_intent, 3000);
  }
}
