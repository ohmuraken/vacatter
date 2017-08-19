package com.fernandocejas.android10.sample.presentation.view.activity

import android.content.Context
import com.fernandocejas.android10.sample.presentation.network.APIHelper
import com.github.bassaer.chatmessageview.utils.ChatBot
import java.util.Random
import javax.inject.Inject
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.os.Handler
import android.util.Log


/**
 * Created by apple on 2017/08/18.
 */

class CustomChatBot : ChatBot() {

  companion object {
    var reply = "ウェイ"
    fun customTalk(username: String, message: String, context: Context): String? {
      var reply:String? = "hello"
      if(!netWorkCheck(context)){
        val messages = arrayOfNulls<String>(5)
        messages[0] = "元気出せよ！そういうこともあるって！"
        messages[1] = "急にどうしたんだよ…"
        messages[2] = message + "ってどういう意味？"
        messages[3] = username + "とは仲良くやっていけそうにないな…"
        val rnd = Random()
        reply = messages[rnd.nextInt(4)]
      }else{
        APIHelper.getApiResponce(message, context);
        val sendDelay = 3000
        Handler().postDelayed({ setText(context); }, sendDelay.toLong())
        Log.d("REPLY_MESSAGE", "get text:" + reply)
      }



      Log.d("REPLY_MESSAGE", "reply text:" + reply)
      return reply
    }

    fun netWorkCheck(context: Context): Boolean {
      val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val info = cm.activeNetworkInfo
      if (info != null) {
        return info.isConnected
      } else {
        return false
      }
    }

    fun setText(context: Context) :String?{
      var data = context.getSharedPreferences("Docomo", Context.MODE_PRIVATE)
      reply = data.getString("response", "えらー");
      Log.d("REPLY_MESSAGE", "set text:" + reply)
      return reply
    }
  }
}
