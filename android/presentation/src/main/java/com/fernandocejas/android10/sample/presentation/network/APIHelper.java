package com.fernandocejas.android10.sample.presentation.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.fernandocejas.android10.sample.presentation.model.API.DocomoApiResponse;
import com.fernandocejas.android10.sample.presentation.view.activity.CustomChatBot;
import java.io.IOException;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * APIへのリクエストを担うヘルパークラス
 * 使用例についてはexampleAPICall関数内を参照。
 */
public class APIHelper extends APIHelperBase {
  /**
   * レスポンスをモックにすげ替えるか否か
   * 各レスポンスのモックはAPIMockクラスにて実装される
   */
  private final static boolean IS_MOCK = true;

  /**
   * APIリクエストの送信先ベースURL
   */
  private static final String API_BASE_URL = "https://api.apigw.smt.docomo.ne.jp/";
  private static final APIHelper sharedInstance = new APIHelper();

  private APIService mAPI;

  public APIHelper() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(generateClient())
        .build();
    mAPI = retrofit.create(APIService.class);
  }

  public static APIService getAPI() {
    return sharedInstance.mAPI;
  }

  //アクセストークン取得APIの呼び出し
  public static void getApiResponce(String utt, Context context) {
    final String APIKEY =
        "33564d64434f423342786a474970644f785434706c4652344441477373415955462e7554326c61594c5332";
    final Context app_context = context;
    SharedPreferences twitter = context.getSharedPreferences("twitter", Context.MODE_PRIVATE);
    String userName = twitter.getString("user_name", "me");
    SharedPreferences docomo = context.getSharedPreferences("Docomo", Context.MODE_PRIVATE);
    String docomo_context = docomo.getString("docomo_context", "");
    String mode = docomo.getString("mode", "dialog");
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("utt", utt);
    hashMap.put("t", "30");
    hashMap.put("nickname", userName);
    hashMap.put("sex", "男");
    hashMap.put("mode", mode);
    hashMap.put("context", docomo_context);
    Call<DocomoApiResponse> call = APIHelper.getAPI().postApiResponce(APIKEY, hashMap);

    call.enqueue(new Callback<DocomoApiResponse>() {
      @Override
      public void onResponse(
          Call<DocomoApiResponse> call, Response<DocomoApiResponse> response) {
        if (response.code() == 200) {
          if (response.body() != null) {
            Log.d("API_RESPONCE", response.body().getUtt());
            Log.d("REPLY_MESSAGE", "save text:" + response.body().getUtt());
            SharedPreferences docomo =
                app_context.getSharedPreferences("Docomo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = docomo.edit();
            editor.putString("response", response.body().getUtt());
            editor.putString("docomo_context",response.body().getContext());
            editor.putString("mode", response.body().getMode());
            editor.apply();
          }
        } else {
          Log.d("API_RESPONCE", response.errorBody().toString());
        }
      }

      @Override
      public void onFailure(Call<DocomoApiResponse> call, Throwable t) {
        Log.d("AAA", "Failed to request");
      }
    });
  }
}
