package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import twitter4j.auth.RequestToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.TwitterException;

import com.fernandocejas.android10.sample.presentation.R;


/**
 * Created by anna on 2017/08/10.
 */

public class OauthActivity extends BaseActivity {
    public static RequestToken _req = null;
    public static OAuthAuthorization _oauth = null;
    private static final String CALLBACK_URI ="Callback://callback";
    private static final String Consumer_Key = "";
    private static final String Consumer_Secret = "";



    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OauthActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Button btn =(Button)findViewById(R.id.btn_LoginTwitter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOauth();
            }
        });
         **/
        this.executeOauth();
    }

    private void executeOauth() {
        //twitter4Jの設定を読み込む
        Configuration conf = ConfigurationContext.getInstance();
        //Oauth認証オブジェクト作成
        _oauth = new OAuthAuthorization(conf);

        //Oauth認証オブジェクトにconsumerKeyとconsumerSecretを設定
        _oauth.setOAuthConsumer(Consumer_Key, Consumer_Secret);

        //アプリの認証オブジェクト作成
        try {
            _req = _oauth.getOAuthRequestToken(CALLBACK_URI);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        String _uri;
        _uri = _req.getAuthorizationURL();
        startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(_uri)), 0);
    }

}
