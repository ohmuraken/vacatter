package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.OnishiModule;
import com.fernandocejas.android10.sample.presentation.view.fragment.OnishiListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, OnishiModule.class})
public interface OnishiComponent extends ActivityComponent {
    void inject(OnishiListFragment onishiListFragment);
}
