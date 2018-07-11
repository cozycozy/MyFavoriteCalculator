package apps.marketableskill.biz.myfavoritecalculator.common

import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

open class BaseActivity : AppCompatActivity() {

    private  lateinit var application : MyApplication
    private  lateinit var mFBAnalytics : FirebaseAnalytics

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        application = getApplication() as MyApplication
        mFBAnalytics = application.getFBAnalytics()

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        application = getApplication() as MyApplication
        mFBAnalytics = application.getFBAnalytics()


    }

    protected fun trackCustomItemEvent(itemID : String, itemName : String){

        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID,itemID)
            putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
        }

        mFBAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM,bundle)

    }
}