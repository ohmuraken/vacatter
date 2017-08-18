package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;

import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment;

public class TimeLineActivity extends BaseActivity implements HasComponent<UseCaseComponent> {

  private UseCaseComponent usecaseComponent;
  @Bind(R.id.btn_PicRegister) FloatingActionButton btn_PicRegist;
  @Bind(R.id.btn_StartChat) FloatingActionButton btn_StartChat;
  @Bind(R.id.bac_dim_layout) RelativeLayout backDimLayout;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    ButterKnife.bind(this);

    // Generate Object Tree
    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new TweetCardFragment());
    }
  }

  @OnClick(R.id.btn_PicRegister)
  void clickPicRegister() {
    PopupWindow popupWin;
    RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
    back_dim_layout.setVisibility(View.VISIBLE);
    LinearLayout popLayout
        = (LinearLayout)getLayoutInflater().inflate(
        R.layout.popup_window, null);
    Drawable drawable = getResources().getDrawable(R.drawable.popup_background);
    popupWin = new PopupWindow(this){
      @Override
      public void dismiss(){
        super.dismiss();
        backDimLayout.setVisibility(View.GONE);
      }
    };

    Button cameraButton = (Button)popLayout.findViewById(R.id.btn_camera);
    Button galleryButton = (Button)popLayout.findViewById(R.id.btn_gallery);

    cameraButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = MainCameraActivity.getCallingIntent(getApplicationContext());
        startActivity(intent);
        Log.d("CLICKED_BUTTON","camera!");
      }
    });
    galleryButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //ギャラリー選択時の処理
        Log.d("CLICKED_BUTTON","gallery!");
      }
    });

    popupWin.setWindowLayoutMode(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    popupWin.setContentView(popLayout);
    popupWin.setBackgroundDrawable(drawable);
    popupWin.setOutsideTouchable(true);
    popupWin.setFocusable(true);
    popupWin.setAnimationStyle(R.style.animationName);
    popupWin.showAtLocation(popLayout, Gravity.CENTER, 0, 0);
  }

  @OnClick(R.id.btn_StartChat)
  void clickStartChat(){
    Intent intent = new Intent(this.getApplicationContext(), ChatBotActivity.class);
    startActivity(intent);
  }

  private void initializeInjector() {
    this.usecaseComponent = DaggerUseCaseComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public UseCaseComponent getComponent() {
    return usecaseComponent;
  }
}
