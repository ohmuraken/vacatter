package com.fernandocejas.android10.sample.presentation.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.net.Uri;
import android.widget.TextView;
import com.fernandocejas.android10.sample.presentation.R;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;


/**
 * Created by anna on 2017/08/10.
 */

public class CallBackActivity extends BaseActivity{
    private static final String VERIFIER = "oauth_verifier";
    private static final String CALLBACK_URI ="Callback://callback";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callback);

        AccessToken token = null;
        //twitterの認証画面から発行されるIntentからUriを取得
        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(CALLBACK_URI)) {
            //oauth _verifireを取得
            String verifier = uri.getQueryParameter(VERIFIER);
            try{
                //AccessTokenオブジェクトを取得
                token = OauthActivity._oauth.getOAuthAccessToken(OauthActivity._req,verifier);
            }catch(TwitterException e){
                e.printStackTrace();
            }
        }
        TextView tv = (TextView)findViewById(R.id.textView1);
        CharSequence cs = "token:" + token.getToken() + "\r\n";
        tv.setText(cs);
    }
}
