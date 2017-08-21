package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.PostFace;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment;
import io.reactivex.observers.DisposableCompletableObserver;
import java.net.URI;

public class TimeLineActivity extends BaseActivity implements HasComponent<UseCaseComponent> {

  private int READ_REQUEST_CODE = 1001;
  private int CAMERA_REQUEST_CODE = 1002;
  private String TAG = TimeLineActivity.class.getName();

  private UseCaseComponent usecaseComponent;
  protected PopupWindow popupWin;
  protected Boolean re_login = false;
  @Bind(R.id.btn_PicRegister) FloatingActionButton btn_PicRegist;
  @Bind(R.id.btn_StartChat) FloatingActionButton btn_StartChat;
  @Bind(R.id.bac_dim_layout) RelativeLayout backDimLayout;
  @Bind(R.id.btn_LogOut) FloatingActionButton bt_LogOut;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    ButterKnife.bind(this);

    // Generate Object Tree
    this.initializeInjector();
    SharedPreferences prefer = getSharedPreferences("login", Context.MODE_PRIVATE);
    //SharedPreferences.Editor editor = prefer.edit();
    Boolean re_login = prefer.getBoolean("re_login", false);
    if (savedInstanceState == null || re_login) {
      addFragment(R.id.fragmentContainer, new TweetCardFragment());
      SharedPreferences.Editor editor = prefer.edit();
      editor.putBoolean("re_login", false);
      editor.apply();
    }

  }

  @OnClick(R.id.btn_PicRegister) void clickPicRegister() {
    RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
    back_dim_layout.setVisibility(View.VISIBLE);
    LinearLayout popLayout =
        (LinearLayout) getLayoutInflater().inflate(R.layout.popup_window, null);
    Drawable drawable = getResources().getDrawable(R.drawable.popup_background);
    popupWin = new PopupWindow(this) {
      @Override public void dismiss() {
        super.dismiss();
        backDimLayout.setVisibility(View.GONE);
      }
    };

    Button cameraButton = (Button) popLayout.findViewById(R.id.btn_camera);
    Button galleryButton = (Button) popLayout.findViewById(R.id.btn_gallery);

    cameraButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intentToLaunch = MainCameraActivity.getCallingIntent(getApplicationContext());
        startActivityForResult(intentToLaunch, CAMERA_REQUEST_CODE);
      }
    });
    galleryButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //ギャラリー選択時の処理
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
      }
    });

    popupWin.setWindowLayoutMode(LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT);
    popupWin.setContentView(popLayout);
    popupWin.setBackgroundDrawable(drawable);
    popupWin.setOutsideTouchable(true);
    popupWin.setFocusable(true);
    popupWin.setAnimationStyle(R.style.animationName);
    popupWin.showAtLocation(popLayout, Gravity.CENTER, 0, 0);
  }


  @OnClick(R.id.btn_StartChat)
  void clickStartChat() {
    Intent intent = new Intent(this.getApplicationContext(), ChatBotActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.btn_LogOut)
  void clickLogOut() {
    Intent intent = new Intent(this.getApplicationContext(), TutorialActivity.class);
    startActivity(intent);
    Fragment fragment = new TweetCardFragment();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.fragmentContainer, fragment);
    transaction.addToBackStack(null);
    transaction.remove(fragment);
    SharedPreferences prefer = getSharedPreferences("login", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefer.edit();
    editor.putBoolean("re_login", true);
    editor.apply();
    //SharedPreferences.Editor editor = prefer.edit();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (data != null) {
      if ((requestCode == READ_REQUEST_CODE || requestCode == CAMERA_REQUEST_CODE)&& resultCode == Activity.RESULT_OK) {
        popupWin.dismiss();
        Uri uri = data.getData();
        if (uri != null) {
          postFaceUseCase.execute(new PostFaceOfIntroCompletable(),
              PostFace.Params.Companion.forFace(URI.create(uri.toString())));
        }
      }
    }

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

  private final class PostFaceOfIntroCompletable extends DisposableCompletableObserver {
    @Override public void onComplete() {
      Log.d(TAG, "Complete");
      SharedPreferences prefer = getSharedPreferences("twitter", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = prefer.edit();
      editor.putBoolean("upload_face", true);
      editor.apply();
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.replace(R.id.fragmentContainer, new TweetCardFragment());
      transaction.addToBackStack(null);
      transaction.commit();
    }

    @Override public void onError(Throwable e) {
      Log.d(TAG, "Error");
    }
  }
}
