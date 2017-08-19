package com.fernandocejas.android10.sample.presentation.view.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.Bind
import butterknife.ButterKnife
import com.fernandocejas.android10.sample.presentation.R
import com.fernandocejas.android10.sample.presentation.internal.di.HasComponent
import com.fernandocejas.android10.sample.presentation.internal.di.components.UseCaseComponent
import com.fernandocejas.android10.sample.presentation.presenter.TwitterLoginIntroPresenter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import javax.inject.Inject


/**
 *
 *
 * Created on 2017/08/15.
 */
class TwitterLoginIntro : Fragment() {

  @Bind(R.id.btn_LoginTwitter) lateinit var btn_LoginTwitter: TwitterLoginButton
  private val consumerKey: String = "UZ07BQX9FRvXK6f0pkRPkQr0D"
  private val consumerSecret: String = "Dw7Dl7w0y0Je5YO07DwjukTliQMazE0sxKfZUnc5PHXovoZjXy"
  @Inject lateinit var twitterloginPresenter: TwitterLoginIntroPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val config = TwitterConfig.Builder(this.context()).logger(DefaultLogger(Log.DEBUG))
        .twitterAuthConfig(TwitterAuthConfig(consumerKey, consumerSecret))
        .debug(true)
        .build()
    Twitter.initialize(config)
    getComponent(UseCaseComponent::class.java).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View {
    val fragmentView = inflater!!.inflate(R.layout.intro_twitter_login, container, false)
    ButterKnife.bind(this, fragmentView)

    btn_LoginTwitter.setCallback(twitterLoginCallback())
    return fragmentView
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 140) {
      btn_LoginTwitter.onActivityResult(requestCode, resultCode, data)
    }
  }

  private fun twitterLoginCallback(): Callback<TwitterSession> {
    val TAG: String = "TWitterKit"
    return object : Callback<TwitterSession>() {
      override fun success(result: Result<TwitterSession>) {
        val session: TwitterSession = result.data
        val msg: String = "@" + session.getUserName() + "logged in! (#" + session.getUserId() + ")"
        Toast.makeText(context(), msg, Toast.LENGTH_LONG).show();

        val prefer: SharedPreferences = context().getSharedPreferences("twitter",
            Context.MODE_PRIVATE);
        val editor: SharedPreferences.Editor = prefer.edit();
        editor.putString("token", session.getAuthToken().token);
        editor.putString("secret", session.getAuthToken().secret);
        editor.putString("user_name", session.getUserName())
        editor.putString("user_id", session.getId().toString());
        editor.apply();
        btn_LoginTwitter.setEnabled(false)

        // PostToken
        twitterloginPresenter.postToken()
      }

      override fun failure(exception: TwitterException?) {
        Log.d(TAG, "Login with Twitter failure", exception)
      }
    }
  }

  /**
   * For Dagger
   */
  fun <C> getComponent(componentType: Class<C>): C {
    return componentType.cast((this.getActivity() as HasComponent<C>).getComponent())
  }

  /**
   * For Dagger
   */
  fun context(): Context {
    return this.getActivity().getApplicationContext()
  }
}