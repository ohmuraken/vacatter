package com.fernandocejas.android10.sample.presentation.view.activity

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.View
import com.fernandocejas.android10.sample.presentation.R
import com.github.bassaer.chatmessageview.models.Message
import com.github.bassaer.chatmessageview.models.User
import com.github.bassaer.chatmessageview.utils.ChatBot
import com.github.bassaer.chatmessageview.views.ChatView
import java.util.Random

/**
 * Created by apple on 2017/08/18.
 */

class ChatBotActivity : BaseActivity() {
  private var mChatView: ChatView? = null

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
      val receivedMessage = Message.Builder()
          .setUser(you)
          .setRightMessage(false)
          .setMessageText(CustomChatBot.customTalk(me.name, message.messageText, this.applicationContext))
          .build()

      // This is a demo bot
      // Return within 3 seconds
      val sendDelay = (Random().nextInt(4) + 1) * 2000
      Handler().postDelayed({ mChatView!!.receive(receivedMessage) }, sendDelay.toLong())
    }

  }
}
