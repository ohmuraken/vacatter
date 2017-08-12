package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.provider.Settings;

import android.widget.Button;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.Result;



/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private TwitterLoginButton loginButton;
  @Bind(R.id.btn_LoadData) Button btn_LoadData;
  @Bind(R.id.btn_LoadTimeTine) Button btn_LoadTimeLine;
  @Bind(R.id.btn_LoginTwitter)
  Button btn_LoginTwitter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    loginButton = (TwitterLoginButton) findViewById(R.id.btn_LoginTwitter);
    setContentView(R.layout.activity_main);
    TwitterConfig config = new TwitterConfig.Builder(this)
        .logger(new DefaultLogger(Log.DEBUG))
        .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
        .debug(true)
        .build();
    Twitter.initialize(this);
    ButterKnife.bind(this);

    /**loginButton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        // Do something with result, which provides a TwitterSession for making API calls
        //TwitterSession session = result.data;
        //String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
      }

      @Override
      public void failure(TwitterException exception) {
        // Do something on failure
        //Log.d("TwitterKit", "Login with Twitter failure", exception);
      }
    });
     **/

  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData) void navigateToUserList() {
    this.navigator.navigateToUserList(this);
  }

  @OnClick(R.id.btn_LoadTimeTine)
  public void submit(View view) {
    Intent intent = new Intent(MainActivity.this, TimeLineActivity.class);
    startActivity(intent);
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // Make sure that the loginButton hears the result from any
    // Activity that it triggered.
    loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
