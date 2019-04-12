package android.proofn.test.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.proofn.test.contracts.BaseContract
import android.proofn.test.presenters.BasePresenter
import android.support.v7.widget.Toolbar

abstract class BaseActivity<P : BasePresenter<BaseContract.View, BaseContract.Interactor>> : AppCompatActivity(){

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

}