package com.fernandocejas.android10.sample.presentation.network;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * API操作の際の、HTTPレイヤーでの処理を記載するベースクラス
 */
abstract class APIHelperBase {
    static OkHttpClient generateClient() {
        //okhttpのclient作成
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                //HTTP headerを変更場合はここ
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        // CookieManager設定
        CookieHandler cookieHandler = new CookieManager();
        httpClient.cookieJar(new JavaNetCookieJar(cookieHandler));

        //ログ出力設定
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.addInterceptor(logging);

        //クライアント生成
        return httpClient.build();
    }
}
