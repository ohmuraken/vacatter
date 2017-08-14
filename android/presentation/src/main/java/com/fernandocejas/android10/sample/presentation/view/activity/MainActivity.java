package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private TwitterLoginButton loginButton;
  @Bind(R.id.btn_LoadData) Button btn_LoadData;
  @Bind(R.id.btn_LoadTimeTine) Button btn_LoadTimeLine;
  @Bind(R.id.btn_LoginTwitter) TwitterLoginButton btn_LoginTwitter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TwitterConfig config = new TwitterConfig.Builder(this).logger(new DefaultLogger(Log.DEBUG))
        .twitterAuthConfig(new TwitterAuthConfig("UZ07BQX9FRvXK6f0pkRPkQr0D",
            "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"))
        .debug(true)
        .build();
    Twitter.initialize(config);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    btn_LoginTwitter.setCallback(new Callback<TwitterSession>() {
      @Override public void success(Result<TwitterSession> result) {
        TwitterSession session = result.data;
        String msg = "@" + session.getUserName() + "logged in! (#" + session.getUserId() + ")";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        SharedPreferences prefer = getSharedPreferences("twitter",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putString("token", session.getAuthToken().token);
        editor.apply();
    }

      @Override public void failure(TwitterException exception) {
        Log.d("TwitterKit", "Login with Twitter failure", exception);
      }
    });
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData) void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }

  @OnClick(R.id.btn_LoadTimeTine) public void submit(View view) {
    Intent intent = new Intent(MainActivity.this, TimeLineActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    btn_LoginTwitter.onActivityResult(requestCode, resultCode, data);
  }
}
