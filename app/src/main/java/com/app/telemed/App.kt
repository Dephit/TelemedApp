package com.app.telemed

import android.app.Application
import com.shakebugs.shake.Shake
import dagger.hilt.android.HiltAndroidApp
import io.cobrowse.CobrowseIO
import kotlinx.coroutines.CoroutineScope

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate();

        /*Instabug.Builder(this, "f0a1e677b6138e45621db7111ca967c9")
                .setInvocationEvents(
                        InstabugInvocationEvent.SHAKE,
                        InstabugInvocationEvent.FLOATING_BUTTON)
                .build()*/
      /*  Shake.start(this, "j7bhDmezMssQXiQwPxDApLE09RXv4WBnbkPMYft8", "9bVGMN64IewZaTt3pcvssIz2zLjmFqI92SCVAm1ywLTmp2doDVMHclP")
        Shake.getReportConfiguration().isShowFloatingReportButton = true*/
        /*CobrowseIO.instance().license("3eE44hHfPpDm_w");
        CobrowseIO.instance().start(this);*/
    }

}

