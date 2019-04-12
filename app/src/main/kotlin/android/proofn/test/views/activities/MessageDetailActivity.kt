package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.MessageDetailContract
import android.proofn.test.entities.MessageModel
import android.proofn.test.interactors.MessageDetailInteractor
import android.proofn.test.interactors.outputs.GetMessageDetailListener
import android.proofn.test.presenters.MessageDetailPresenter
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_detail.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class MessageDetailActivity : BaseActivity<MessageDetailPresenter>(), MessageDetailContract.View  {

    private lateinit var header : String
    private var id : String = ""

    private val textViewSender: TextView? by lazy { textView_sender }
    private val textViewReceivedAt: TextView? by lazy { textView_receivedAt }
    private val textViewEmail: TextView? by lazy { textView_email }
    private val textViewContent: TextView? by lazy { textView_content }
    private val textViewSubject: TextView? by lazy { textView_Subject }

    companion object {
        const val TAG: String = "MessageDetailActivity"
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
//                    FormLoginActivity.TAG -> startActivity(Intent(this@HomeActivity, FormLoginActivity::class.java))
//                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }
    override fun instantiatePresenter(): MessageDetailPresenter {
        return MessageDetailPresenter(this,MessageDetailInteractor())
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun getContext(): Context {
        return this
    }

    override fun onResume() {
        super.onResume()
        // add back arrow to toolbar
        supportActionBar?.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // load invoking arguments
        val argument = intent.getStringExtra("idMessage")
        id = argument.toString()

        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun finishView() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        this.init()
    }

    private fun init(){
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        header = "Bearer "+ Hawk.get("token")
        val argument = intent.getStringExtra("idMessage")
        id = argument.toString()
        presenter.getMessageDetail(GetMessageDetailEventListener(), header, id)
        Log.i("token", header)
    }

    inner class GetMessageDetailEventListener : GetMessageDetailListener {
        override fun onResponse(messageModel: MessageModel) {
            Toast.makeText(getActivity(), "berhasil",
                    Toast.LENGTH_LONG).show()
            textViewSender?.text = messageModel.sender?.fullName
            textViewReceivedAt?.text = messageModel.receivedAt
            textViewContent?.text = messageModel.content
            textViewEmail?.text = messageModel.sender?.email
            textViewSubject?.text = messageModel.subject
        }

        override fun onFailure(throwable: Throwable) {
            if(throwable.message != null){
                Log.i("GetCategory-failed", throwable.message)
                Toast.makeText(getActivity(), "gagal",
                        Toast.LENGTH_LONG).show()
            }

        }
    }
}