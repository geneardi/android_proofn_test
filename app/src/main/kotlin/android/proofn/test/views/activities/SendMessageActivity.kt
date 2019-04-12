package android.proofn.test.views.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.SendMessageContract
import android.proofn.test.entities.MessageSentModel
import android.proofn.test.interactors.SendMessageInteractor
import android.proofn.test.interactors.outputs.SendMessageListener
import android.proofn.test.presenters.SendMessagePresenter
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_compose_message.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class SendMessageActivity : BaseActivity<SendMessagePresenter>(), SendMessageContract.View  {

    private lateinit var header : String
    private val textViewSubject: TextView? by lazy { subject }
    private val textViewContent: TextView? by lazy { content }
    private val toolbars : Toolbar? by lazy { toolbar }

    companion object {
        const val TAG: String = "SendMessagelActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                when (command.screenKey) {
                }
            }
        }
    }
    override fun instantiatePresenter(): SendMessagePresenter {
        return SendMessagePresenter(this,SendMessageInteractor())
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getContext(): Context {
        return this
    }

    override fun finishView() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_message)
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        header = "Bearer "+ Hawk.get("token")
        this.initToolbar()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.setNavigator(navigator)
    }

    @SuppressLint("PrivateResource")
    private fun initToolbar() {
        toolbars?.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Send Message"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_chips, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        else if (item.itemId == R.id.action_send) {
            presenter.getMessageDetail(GetMessageDetailEventListener(), header,
                    textViewSubject?.text.toString(), textViewContent?.text.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    inner class GetMessageDetailEventListener : SendMessageListener {
        override fun onResponse(sentModel: MessageSentModel) {
            Toast.makeText(getActivity(), "berhasil",
                    Toast.LENGTH_LONG).show()
            finish()
        }

        override fun onFailure(throwable: Throwable) {
            if(throwable.message != null){
                Log.i("GetCategory-failed", throwable.message)
                Toast.makeText(getActivity(), "gagal",
                        Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }
}