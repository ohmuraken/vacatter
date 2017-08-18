package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.fernandocejas.android10.sample.presentation.AndroidApplication;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.ApplicationComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.view.intro.CameraIntro;
import com.fernandocejas.android10.sample.presentation.view.intro.TwitterLoginIntro;
import com.fernandocejas.android10.sample.presentation.view.intro.WelcomeIntro;
import com.github.paolorotolo.appintro.AppIntro;

public class TutorialActivity extends AppIntro implements HasComponent<UseCaseComponent> {

  private UseCaseComponent usecaseComponent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Note here that we DO NOT use setContentView();

    // Do Dependency Injection
    this.initializeInjector();

    // Add your slide fragments here.
    // AppIntro will automatically generate the dots indicator and buttons.
    addSlide(new WelcomeIntro());
    addSlide(new TwitterLoginIntro());
    addSlide(new CameraIntro());

    // Instead of fragments, you can also use our default slide
    // Just set a title, description, background and image. AppIntro will do the rest.
    //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

    // OPTIONAL METHODS
    // Override bar/separator color.
    setBarColor(Color.parseColor("#3F51B5"));
    setSeparatorColor(Color.parseColor("#2196F3"));

    // Hide Skip/Done button.
    showSkipButton(false);
    setProgressButtonEnabled(true);

    // Turn vibration on and set intensity.
    // NOTE: you will probably need to ask VIBRATE permission in Manifest.
    setVibrate(true);
    setVibrateIntensity(30);
  }

  @Override public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    // Do something when users tap on Skip button.
  }

  @Override public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    // ログインチェックor画像アップロードチェック
    SharedPreferences shared = getSharedPreferences("twitter", Context.MODE_PRIVATE);

    if (!shared.getBoolean("upload_face", false)
        || shared.getString("token", "").equals("")
        || shared.getString("secret", "").equals("")) {
      Toast.makeText(getApplicationContext(), "Twitterのログインと画像のアップロードが必要です", Toast.LENGTH_LONG)
          .show();
    } else {
      // Do something when users tap on Done button.
      SharedPreferences.Editor editor = shared.edit();
      editor.putBoolean("complete_tutorial", true);
      editor.apply();
      Intent intent = new Intent(this, TimeLineActivity.class);
      startActivity(intent);
      finish();
    }
  }

  @Override
  public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
    super.onSlideChanged(oldFragment, newFragment);
    // Do something when the slide changes.
  }

  private void initializeInjector() {
    this.usecaseComponent = DaggerUseCaseComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();
  }

  private ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  public UseCaseComponent getComponent() {
    return usecaseComponent;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      for (Fragment fragment : fragments) {
        fragment.onActivityResult(requestCode, resultCode, data);
      }
    }
  }
}
