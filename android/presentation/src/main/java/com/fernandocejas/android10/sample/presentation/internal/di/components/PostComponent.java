package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.PostModule;
import com.fernandocejas.android10.sample.presentation.view.fragment.PostListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PostModule.class})
public interface PostComponent extends ActivityComponent {
    void inject(PostListFragment postListFragment);
}
