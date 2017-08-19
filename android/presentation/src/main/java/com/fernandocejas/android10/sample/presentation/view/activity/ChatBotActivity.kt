package com.fernandocejas.android10.sample.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.fernandocejas.android10.sample.domain.interactor.PostFace
import com.fernandocejas.android10.sample.presentation.R
import com.github.bassaer.chatmessageview.models.Message
import com.github.bassaer.chatmessageview.models.User
import com.github.bassaer.chatmessageview.utils.ChatBot
import com.github.bassaer.chatmessageview.views.ChatView
import java.io.IOException
import java.net.URI
import java.util.Random

/**
 * Created by apple on 2017/08/18.
 */

class ChatBotActivity : BaseActivity() {
  private var mChatView: ChatView? = null
  private val READ_REQUEST_CODE = 1001

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_chat_bot)

    //User id
    val myId = 0
    //User icon
    val myIcon = BitmapFactory.decodeResource(resources, R.drawable.logo)
    //User name
    val prefer = getSharedPreferences("twitter", Context.MODE_PRIVATE)
    val myName = prefer.getString("user_name", "me")

    val yourId = 1
    val yourIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_icon_vacatter)
    val yourName = "Vacatterバード"

    val me = User(myId, myName, myIcon)
    val you = User(yourId, yourName, yourIcon)

    mChatView = findViewById(R.id.chat_view) as ChatView

    //Set UI parameters if you need
    mChatView!!.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500))
    mChatView!!.setLeftBubbleColor(Color.WHITE)
    mChatView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500))
    mChatView!!.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900))
    mChatView!!.setSendIcon(R.drawable.ic_action_send)
    mChatView!!.setRightMessageTextColor(Color.WHITE)
    mChatView!!.setLeftMessageTextColor(Color.BLACK)
    mChatView!!.setUsernameTextColor(Color.WHITE)
    mChatView!!.setSendTimeTextColor(Color.WHITE)
    mChatView!!.setDateSeparatorColor(Color.WHITE)
    mChatView!!.setInputTextHint("new message...")
    mChatView!!.setMessageMarginTop(5)
    mChatView!!.setMessageMarginBottom(5)

    val firstMessage = Message.Builder()
        .setUser(you)
        .setRightMessage(false)
        .setMessageText("やあ！僕の名前はVacatterバード。楽しく会話しよう")
        .build()
    mChatView!!.send(firstMessage)

    //画像を送った場合
    mChatView!!.setOnClickOptionButtonListener {
      val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
      intent.addCategory(Intent.CATEGORY_OPENABLE)
      intent.type = "image/*"
      startActivityForResult(intent, READ_REQUEST_CODE)
    }

    //Click Send Button
    mChatView!!.setOnClickSendButtonListener {
      //new message
      val message = Message.Builder()
          .setUser(me)
          .setRightMessage(true)
          .setMessageText(mChatView!!.inputText)
          .hideIcon(true)
          .build()
      //Set to chat view
      mChatView!!.send(message)
      //Reset edit text
      mChatView!!.inputText = ""
      //Receive message
      var receivedMessage = Message.Builder()
          .setUser(you)
          .setRightMessage(false)
          .setMessageText(CustomChatBot.customTalk(me.name, message.messageText, this.applicationContext))
      // This is a demo bot
      // Return within 3 seconds
      Handler().postDelayed({
        receivedMessage
            .setMessageText(updateTalk(this.applicationContext))
            .build()
      }, 2000)

      Handler().postDelayed({
        mChatView!!.receive(receivedMessage.build())
      }, 3000)
    }

  }

  fun updateTalk(context: Context): String? {
    var data = context.getSharedPreferences("Docomo", Context.MODE_PRIVATE)
    val reply = data.getString("response", "えらー");
    return reply
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val yourId = 1
    val yourIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_icon_vacatter)
    val yourName = "Vacatterバード"
    val you = User(yourId, yourName, yourIcon)
    var bitmap: Bitmap? = null
    if (data != null) {
      if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        val uri = data.data
        if (uri != null) {
          try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
          } catch (e: IOException) {
            e.printStackTrace();
          }
        }
        Log.d("BITMAP", bitmap.toString())
        val imageResponse = Message.Builder()
            .setUser(you)
            .setRightMessage(false)
            .setMessageText("お、画像を投稿したね？変換して返すよ！")
            .build()
        val imageMessage = Message.Builder()
            .setUser(you)
            .setRightMessage(false)
            .setPicture(bitmap)
            .setType(Message.Type.PICTURE)
            .build()
        mChatView!!.receive(imageResponse)
        Handler().postDelayed({
          mChatView!!.receive(imageMessage)
        }, 3000)
      }
    }
  }

}

