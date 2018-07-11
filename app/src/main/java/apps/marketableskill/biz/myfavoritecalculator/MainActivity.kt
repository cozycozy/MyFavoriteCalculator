package apps.marketableskill.biz.myfavoritecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import apps.marketableskill.biz.myfavoritecalculator.common.BaseActivity
import apps.marketableskill.biz.myfavoritecalculator.common.MyApplication
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mTracker : Tracker? = null
    private var mFBAnalytics : FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        trackCustomItemEvent("10","ViewHomeInfo")

        button.setOnClickListener {

            trackCustomItemEvent("20","ButtonTapped")

//            mTracker?.send(HitBuilders.EventBuilder()
//                    .setCategory("Button")
//                    .setAction("Clicked")
//                    .build()
//            )
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var application = getApplication() as MyApplication
//
//        //GoogleAnalytics
//        mTracker = application.getDefaltTracker()
//        mTracker?.setScreenName("MainActivity")
//        mTracker?.send(HitBuilders.ScreenViewBuilder().build())
//
//
//        //Firebase
//        mFBAnalytics = application.getFBAnalytics()
//
//        val bundle = Bundle().apply {
//            putString(FirebaseAnalytics.Param.ITEM_ID, "10")
//            putString(FirebaseAnalytics.Param.ITEM_NAME, "testName")
//        }
//
//        mFBAnalytics!!.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)

        trackCustomItemEvent("10","ViewHomeInfo")

        button.setOnClickListener {

            trackCustomItemEvent("20","ButtonTapped")

//            mTracker?.send(HitBuilders.EventBuilder()
//                    .setCategory("Button")
//                    .setAction("Clicked")
//                    .build()
//            )
        }

    }

}
