package com.fernandocejas.android10.sample.presentation.network;


import com.fernandocejas.android10.sample.presentation.model.API.DocomoApiResponse;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * APIの構造を表現するインターフェース
 * アノテーションにより通信方法がRetrofitに対して提供される
 * 直接実装されることはなく、Retrofit内部にて実体化される
 */
public interface APIService {


    @Headers({
            "Content-Type: application/json; charset=UTF-8"
    })

    @POST("dialogue/v1/dialogue")
    Call<DocomoApiResponse> postApiResponce(
        @Query("APIKEY") String piyo,
        @Body HashMap<String, String> body);
}
