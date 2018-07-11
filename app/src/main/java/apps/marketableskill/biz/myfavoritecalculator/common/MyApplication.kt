package apps.marketableskill.biz.myfavoritecalculator.common

import android.app.Application
import apps.marketableskill.biz.myfavoritecalculator.R
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics

class MyApplication : Application() {

    private var mAnalytics : GoogleAnalytics? = null
    private var mTracker: Tracker? = null

    private val sAnalytics: GoogleAnalytics? = null
    private var sTracker: Tracker? = null

    private lateinit var mFBAnalytics : FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()

        mAnalytics = GoogleAnalytics.getInstance(this)
        mFBAnalytics = FirebaseAnalytics.getInstance(this)

    }

    fun getFBAnalytics() : FirebaseAnalytics {

        if (mFBAnalytics == null){
            mFBAnalytics = FirebaseAnalytics.getInstance(this)
        }

        return mFBAnalytics
    }

    @Synchronized
    fun getDefaltTracker() : Tracker? {

        if (mTracker == null) {
            mTracker = mAnalytics?.newTracker(R.xml.global_tracker)
        }

        return mTracker
    }




}
