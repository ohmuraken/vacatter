package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.activity.BaseActivity;
import com.fernandocejas.android10.sample.presentation.view.activity.TimeLineActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by seiya on 2017/08/14.
 */

public class LoginTutorialFragment extends Fragment {
  private TwitterLoginButton loginButton;
  private Context context;

  @Bind(R.id.btn_LoginTwitter) TwitterLoginButton btn_LoginTwitter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    context = this.getContext();
    View view = inflater.inflate(R.layout.activity_login, container, false);
    ButterKnife.inject(this, view);
    return inflater.inflate(R.layout.activity_login, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final TwitterConfig config =
        new TwitterConfig.Builder(context).logger(new DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(new TwitterAuthConfig("UZ07BQX9FRvXK6f0pkRPkQr0D",
                "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"))
            .debug(true)
            .build();
    Twitter.initialize(config);

    btn_LoginTwitter.setCallback(new Callback<TwitterSession>() {
      @Override public void success(Result<TwitterSession> result) {
        TwitterSession session = result.data;
        String msg = "@" + session.getUserName() + "logged in! (#" + session.getUserId() + ")";
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        SharedPreferences prefer = context.getSharedPreferences("twitter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putString("token", session.getAuthToken().token);
        editor.putString("secret", session.getAuthToken().secret);
        editor.putLong("user_id", session.getId());
        editor.apply();
      }

      @Override public void failure(TwitterException exception) {
        Log.d("TwitterKit", "Login with Twitter failure", exception);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    btn_LoginTwitter.onActivityResult(requestCode, resultCode, data);
  }
}
