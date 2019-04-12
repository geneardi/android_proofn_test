package android.proofn.test

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router


@SuppressLint("Registered")
class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    lateinit var proofnCicerone: Cicerone<Router>

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        this.initCicerone()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initCicerone() {
        this.proofnCicerone = Cicerone.create()
    }
}