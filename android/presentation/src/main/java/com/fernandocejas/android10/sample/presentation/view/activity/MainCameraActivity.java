package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.camera.CameraFragment;
import com.fernandocejas.android10.sample.presentation.camera.CameraLegacyFragment;
import com.fernandocejas.android10.sample.presentation.camera.PictureFragment;

public class MainCameraActivity extends AppCompatActivity
        implements View.OnClickListener {

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainCameraActivity.class);
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);

        // 本日の日付文字列
        String date = String.format("%1$tF", System.currentTimeMillis());

        // ギャラリーフラグメントを生成する
        PictureFragment pictureFragment = PictureFragment.newInstance(date);

        // 両フラグメントを追加する
        getFragmentManager().beginTransaction()
                .replace(R.id.GalleryContainer, pictureFragment)
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        findViewById(R.id.CameraButton).setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // 「撮影する」ボタンが押された
        if (v.getId() == R.id.CameraButton) {
            Fragment cameraFragment;

            // カメラフラグメントを表示する
            if (Build.VERSION.SDK_INT > 21) {
                cameraFragment = new CameraFragment();
            } else {
                cameraFragment = new CameraLegacyFragment();
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.CameraContainer, cameraFragment)
                    .addToBackStack(null) // バックスタックに入れることで、戻るキーで戻れる
                    .commit();

        }
    }
}
