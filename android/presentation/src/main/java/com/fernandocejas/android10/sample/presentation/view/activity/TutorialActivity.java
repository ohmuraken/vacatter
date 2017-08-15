package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.CameraTutorialFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.OneButtonFragment;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by apple on 2017/08/14.
 */

public class TutorialActivity extends AppIntro {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Note here that we DO NOT use setContentView();

    // Add your slide fragments here.
    // AppIntro will automatically generate the dots indicator and buttons.
    addSlide(new CameraTutorialFragment());
    addSlide(new OneButtonFragment());
    addSlide(new OneButtonFragment());
    //addSlide(fourthFragment);

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

  @Override
  public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    // Do something when users tap on Skip button.
  }

  @Override
  public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    // Do something when users tap on Done button.
    Intent intent= new Intent(this, SplashActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
    super.onSlideChanged(oldFragment, newFragment);
    // Do something when the slide changes.
  }
}
