package com.fernandocejas.android10.sample.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment;
import com.squareup.okhttp.internal.framed.FrameReader;

public class TimeLineActivity extends BaseActivity implements HasComponent<UseCaseComponent> {

  private UseCaseComponent usecaseComponent;
  @Bind(R.id.btn_PicRegister) FloatingActionButton btn_PicRegist;
  @Bind(R.id.swipe_container) SwipeRefreshLayout swipe_container;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    ButterKnife.bind(this);

    swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
    swipe_container.setOnRefreshListener(mOnRefreshListener);
    swipe_container.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

    // Generate Object Tree
    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new TweetCardFragment());
    }
  }

  @OnClick(R.id.btn_PicRegister)
  void clickPicRegister() {
    Intent intent = MainCameraActivity.getCallingIntent(getApplicationContext());
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

  private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    @Override public void onRefresh() {
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          swipe_container.setRefreshing(false);
        }
      }, 2000);
    }
  };

}
