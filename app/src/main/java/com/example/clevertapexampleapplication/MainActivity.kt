package com.example.clevertapexampleapplication

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CTInboxStyleConfig
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.inbox.CTInboxMessage
import com.example.clevertapexampleapplication.application.MyApplication
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import kotlin.collections.HashMap
import org.json.JSONArray
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CTInboxListener {

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var btnLogin: Button
    private lateinit var btnRaiseEvent: Button
    private lateinit var btnAppInbox: Button
    private lateinit var clevertapDefaultInstance: CleverTapAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("TAG", "onCreate: 6")
        clevertapDefaultInstance = (application as MyApplication).getCleverTapInstance()!!
        Log.i("TAG", "onCreate: 7")

        clevertapDefaultInstance?.apply {

            ctNotificationInboxListener = this@MainActivity

            //Initialize the inbox and wait for callbacks on overridden methods
            initializeInbox()
        }


//        clevertapDefaultInstance?.pushProfile(profileUpdate)

        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnRaiseEvent = findViewById<Button>(R.id.btnRaiseEvent)
        btnAppInbox = findViewById<Button>(R.id.btnAppInbox)

        btnLogin.setOnClickListener {

            val rand = (0..1000).random()
            val profileUpdate = HashMap<String, Any>()
            profileUpdate["Name"] = "Clevertap Test $rand" // String

            profileUpdate["Email"] = "test$rand@gmail.com" // Email address of the user

            //        profileUpdate["Phone"] = "+14155534" // Phone (with the country code, starting with +)

            profileUpdate["Gender"] = "M" // Can be either M or F

            profileUpdate["DOB"] = Date()
            Log.i("TAG", "onCreate: 8")
            clevertapDefaultInstance?.onUserLogin(profileUpdate);


            clevertapDefaultInstance?.allInboxMessages
            Log.i("TAG", "onCreate: 9")


        }

        btnRaiseEvent.setOnClickListener {
            val prodViewedAction = HashMap<String, Any>()
            prodViewedAction["Product Name"] = "Casio Chronograph Watch"
            prodViewedAction["Category"] = "Mens Accessories"
            prodViewedAction["Product Url"] = "https://www.amazon.in"
            prodViewedAction["Price"] = 59.99
            prodViewedAction["Date"] = Date()
            clevertapDefaultInstance?.pushEvent("Product viewed", prodViewedAction)

            val email = clevertapDefaultInstance?.getProperty("Email")

        }
    }

    override fun inboxDidInitialize() {
        btnAppInbox.setOnClickListener {
            val inboxTabs =
                arrayListOf(
                    "Promotions",
                    "Offers",
                    "Others"
                )//Anything after the first 2 will be ignored
            CTInboxStyleConfig().apply {
                tabs = inboxTabs //Do not use this if you don't want to use tabs
                tabBackgroundColor = "#FF0000"
                selectedTabIndicatorColor = "#0000FF"
                selectedTabColor = "#000000"
                unselectedTabColor = "#FFFFFF"
                backButtonColor = "#FF0000"
                navBarTitleColor = "#FF0000"
                navBarTitle = "MY INBOX"
                navBarColor = "#FFFFFF"
                inboxBackgroundColor = "#00FF00"
                //firstTabTitle = "First Tab"
                clevertapDefaultInstance?.showAppInbox(this) //Opens activity With Tabs

            }
            //OR
            //clevertapDefaultInstance?.showAppInbox()//Opens Activity with default style config
        }
    }

    override fun inboxMessagesDidUpdate() {
        Log.i("TAG", "inboxMessagesDidUpdate() called")
    }
}