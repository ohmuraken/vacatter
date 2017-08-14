package com.fernandocejas.android10.sample.presentation.internal.di.components

import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule
import com.fernandocejas.android10.sample.presentation.internal.di.modules.UseCaseModule
import com.fernandocejas.android10.sample.presentation.view.fragment.TweetCardFragment
import dagger.Component

/**
 * Component for using Demo UseCase class
 *
 * Created on 8/14/17.
 */
@PerActivity
@Component(
    dependencies = arrayOf(
        ApplicationComponent::class
    ),
    modules = arrayOf(
        ActivityModule::class,
        UseCaseModule::class
    )
)
public interface UseCaseComponent {
    fun inject(tweetCardFragment: TweetCardFragment)
}
