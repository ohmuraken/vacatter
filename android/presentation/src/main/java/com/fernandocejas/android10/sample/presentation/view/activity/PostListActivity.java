package com.fernandocejas.android10.sample.presentation.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.DaggerPostComponent;
import com.fernandocejas.android10.sample.presentation.internal.di.components.PostComponent;
import com.fernandocejas.android10.sample.presentation.model.PostModel;
import com.fernandocejas.android10.sample.presentation.view.fragment.PostListFragment;

public class PostListActivity extends BaseActivity implements HasComponent<PostComponent>,
        PostListFragment.PostListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PostListActivity.class);
    }

    private PostComponent postComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new PostListFragment());
        }
    }

    private void initializeInjector() {
        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }

    @Override
    public void onPostClicked(PostModel postModel) {
        this.navigator.navigateToUserDetails(this, postModel.getPostId());
    }
}
