package com.adut.pasar.app.feature.exception

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Looper
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adut.pasar.app.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.PrintWriter
import java.io.StringWriter

class UncaughtExceptionActivity : AppCompatActivity, Thread.UncaughtExceptionHandler {
    private var myContext: Context? = null
    private var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null

    // Need empty constructor for AndroidManifest
    constructor() {}
    constructor(
        context: Context?,
        defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler?
    ) {
        myContext = context
        this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler
    }

    public override fun uncaughtException(
        thread: Thread,
        exception: Throwable
    ): Unit {
        object : Thread() {
            public override fun run(): Unit {
                try {
                    Looper.prepare()
                    val builder: AlertDialog.Builder = AlertDialog.Builder(myContext!!)
                    val inflater: LayoutInflater = myContext!!.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val dialogView: View = inflater.inflate(R.layout.activity_exception, null)
                    val tv: TextView = dialogView.findViewById<TextView>(R.id.errorMessage)
                    val appVersion: TextView = dialogView.findViewById<TextView>(R.id.tvAppVersion)
                    tv.setText("Terjadi Error di Aplikasi ini\nMohon dicoba lagi lain kali")
                    setVerisonNameText(appVersion)
                    builder.setView(dialogView)
                    builder.setCancelable(false)
                    if (!isFinishing()) {
                        builder.show()
                    }
                    Looper.loop()
                } catch (e: Exception) {
                    FirebaseCrashlytics.getInstance().log(Log.getStackTraceString(e))
                }
            }
        }.start()
        try {
            FirebaseCrashlytics.getInstance().log(Log.getStackTraceString(exception))
            Thread.sleep(3000)
            val stackTrace: StringWriter = StringWriter()
            exception.printStackTrace(PrintWriter(stackTrace))
            System.err.println(stackTrace)
            killProcess(object : preKillProcess{
                override fun onKillProcess() {
                    defaultUncaughtExceptionHandler?.uncaughtException(
                        thread,
                        exception
                    )
                }
            })
            finish()
        } catch (e: InterruptedException) {
            FirebaseCrashlytics.getInstance().log(Log.getStackTraceString(e))
        }
    }

    private fun setVerisonNameText(appVersion: TextView) {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = myContext!!.packageManager
                .getPackageInfo(myContext!!.packageName, PackageManager.GET_ACTIVITIES)
            val versionName = "App Version " + packageInfo.versionName
            appVersion.text = versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun killProcess(action: preKillProcess): Unit {
        action.onKillProcess()
        Process.killProcess(Process.myPid())
        System.exit(10)
    }

    private open interface preKillProcess {
       open abstract fun onKillProcess()
    }
}