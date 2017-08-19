package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.widgets.CameraSettingsView;
import com.github.florent37.camerafragment.widgets.CameraSwitchView;
import com.github.florent37.camerafragment.widgets.FlashSwitchView;
import com.github.florent37.camerafragment.widgets.RecordButton;
import java.util.Calendar;

/**
 * Use https://github.com/florent37/CameraFragment
 */
public class MainCameraActivity extends AppCompatActivity {

  public static final String FRAGMENT_TAG = "camera";
  private static final String FILE_NAME_TEMPLATE = "image-%1$tF-%1$tH-%1$tM-%1$tS-%1$tL";
  @Bind(R.id.settings_view) CameraSettingsView settingsView;
  @Bind(R.id.flash_switch_view) FlashSwitchView flashSwitchView;
  @Bind(R.id.front_back_camera_switcher) CameraSwitchView cameraSwitchView;
  @Bind(R.id.record_button) RecordButton recordButton;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainCameraActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_camera);
    ButterKnife.bind(this);

    final Configuration.Builder builder = new Configuration.Builder();
    builder.setCamera(Configuration.CAMERA_FACE_FRONT).setFlashMode(Configuration.FLASH_MODE_ON);
    CameraFragment cameraFragment = CameraFragment.newInstance(builder.build());
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.content, cameraFragment, FRAGMENT_TAG)
        .commit();

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setHomeButtonEnabled(false);
    }
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.flash_switch_view) public void onFlashSwitcClicked() {
    final CameraFragmentApi cameraFragment = getCameraFragment();
    if (cameraFragment != null) {
      cameraFragment.toggleFlashMode();
    }
  }

  @OnClick(R.id.front_back_camera_switcher) public void onSwitchCameraClicked() {
    final CameraFragmentApi cameraFragment = getCameraFragment();
    if (cameraFragment != null) {
      cameraFragment.switchCameraTypeFrontBack();
    }
  }

  @OnClick(R.id.record_button) public void onRecordButtonClicked() {
    final CameraFragmentApi cameraFragment = getCameraFragment();
    if (cameraFragment != null) {
      cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultAdapter() {
        @Override public void onPhotoTaken(byte[] bytes, String filePath) {
          Toast.makeText(getBaseContext(), "onPhotoTaken " + filePath, Toast.LENGTH_SHORT).show();

          Intent intent = new Intent();
          intent.setData(Uri.parse(filePath));

          setResult(RESULT_OK, intent);
          finish();
        }
      }, getApplication().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath(), String.format(FILE_NAME_TEMPLATE, Calendar.getInstance()));
    }
  }

  @OnClick(R.id.settings_view)
  public void onSettingsClicked() {
    final CameraFragmentApi cameraFragment = getCameraFragment();
    if (cameraFragment != null) {
      cameraFragment.openSettingDialog();
    }
  }

  private CameraFragmentApi getCameraFragment() {
    return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
  }
}
