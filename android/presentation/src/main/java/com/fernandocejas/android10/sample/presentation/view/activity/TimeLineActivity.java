package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerUseCaseComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent;
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment;

public class TimeLineActivity extends BaseActivity implements HasComponent<UseCaseComponent> {

  private UseCaseComponent usecaseComponent;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);

    // Generate Object Tree
    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new TweetCardFragment());
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
}
