package com.fernandocejas.android10.sample.presentation.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.fernandocejas.android10.sample.presentation.model.API.DocomoApiResponse;
import com.fernandocejas.android10.sample.presentation.model.API.TwitterApiResponse;
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
public class APIHelper2 extends APIHelperBase {
  /**
   * APIリクエストの送信先ベースURL
   */
  private static final String API_BASE_URL = "YOUR_API_SERVER/vacatter/api/v1.0/";
  private static final APIHelper2 sharedInstance = new APIHelper2();

  private APIService2 mAPI;

  public APIHelper2() {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(generateClient())
        .build();
    mAPI = retrofit.create(APIService2.class);
  }

  public static APIService2 getAPI() {
    return sharedInstance.mAPI;
  }

  //アクセストークン取得APIの呼び出し
  public static void getApiResponce(String tweetId, Context context, String favorited) {
    SharedPreferences twitter = context.getSharedPreferences("twitter", Context.MODE_PRIVATE);
    String userId = twitter.getString("user_id", "");
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("user_id",userId);
    hashMap.put("tweet_id", tweetId);
    hashMap.put("favorited", favorited);
    Call<TwitterApiResponse> call = APIHelper2.getAPI().postApiResponce(hashMap);

    call.enqueue(new Callback<TwitterApiResponse>() {
      @Override
      public void onResponse(
          Call<TwitterApiResponse> call, Response<TwitterApiResponse> response) {
        if (response.code() == 200) {
          if (response.body() != null) {
            Log.d("API_RESPONCE", response.body().getStatus());
          }
        } else {
          Log.d("API_RESPONCE", response.errorBody().toString());
        }
      }

      @Override
      public void onFailure(Call<TwitterApiResponse> call, Throwable t) {
        Log.d("AAA", "Failed to request");
      }
    });
  }
}
