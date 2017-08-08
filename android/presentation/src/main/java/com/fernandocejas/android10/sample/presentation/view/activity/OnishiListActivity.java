package com.fernandocejas.android10.sample.presentation.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerOnishiComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.OnishiComponent;
import com.fernandocejas.android10.sample.presentation.model.OnishiModel;
import com.fernandocejas.android10.sample.presentation.view.fragment.OnishiListFragment;

public class OnishiListActivity extends BaseActivity implements HasComponent<OnishiComponent>,
        OnishiListFragment.OnishiListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OnishiListActivity.class);
    }

    private OnishiComponent onishiComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new OnishiListFragment());
        }
    }

    private void initializeInjector() {
        this.onishiComponent = DaggerOnishiComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public OnishiComponent getComponent() {
        return onishiComponent;
    }

    @Override
    public void onOnishiClicked(OnishiModel onishiModel) {
        this.navigator.navigateToUserDetails(this, onishiModel.getOnishiId());
    }
}
