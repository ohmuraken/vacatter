package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.provider.Settings;

import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  @Bind(R.id.btn_LoadData) Button btn_LoadData;

  @Bind(R.id.btn_LoadTimeTine) Button btn_LoadTimeLine;
  @Bind(R.id.btn_LoginTwitter)
  Button btn_LoginTwitter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
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
}
