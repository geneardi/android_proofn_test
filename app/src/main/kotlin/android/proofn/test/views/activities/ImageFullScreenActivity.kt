package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.HomeContract
import android.proofn.test.contracts.ImageFullScreenContract
import android.proofn.test.entities.MessageModel
import android.proofn.test.entities.UserModel
import android.proofn.test.entities.responses.MessageResponse
import android.proofn.test.interactors.HomeInteractor
import android.proofn.test.interactors.ImageFullScreenInteractor
import android.proofn.test.interactors.outputs.GetMessageListener
import android.proofn.test.interactors.outputs.GetUserProfileListener
import android.proofn.test.presenters.HomePresenter
import android.proofn.test.presenters.ImageFullScreenPresenter
import android.proofn.test.views.adapters.MessageListAdapter
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_image_fullscreen.*
import kotlinx.android.synthetic.main.include_drawer_content.*
import kotlinx.android.synthetic.main.include_drawer_header_mail_with_account.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class ImageFullScreenActivity : BaseActivity<ImageFullScreenPresenter>(), ImageFullScreenContract.View {

    private var item : List<MessageModel>? = null
    private lateinit var header : String
    private lateinit var id : String

    private val imageView: ImageView? by lazy { fullScreenImage }
    private val editImageButton: ImageButton? by lazy { bt_accounts }
    var urlImage: String? = null

    private lateinit var mAdapter: MessageListAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager
    companion object {
        const val TAG: String = "HomeActivity"
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
                    MessageDetailActivity.TAG -> startActivity(Intent(this@ImageFullScreenActivity, MessageDetailActivity::class.java))
                    SendMessageActivity.TAG -> startActivity(Intent(this@ImageFullScreenActivity, SendMessageActivity::class.java))
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
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

    override fun onResume() {
        super.onResume()
        // add back arrow to toolbar
        supportActionBar?.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // load invoking arguments
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun instantiatePresenter(): ImageFullScreenPresenter {
        return ImageFullScreenPresenter(this, ImageFullScreenInteractor())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_fullscreen)
        val argument = intent.getStringExtra("url")
        id = argument.toString()
        Glide
                .with(getContext())
                .load(urlImage)
                .into(imageView!!)
        this.init()
    }

    private fun init(){
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        header = "Bearer "+Hawk.get("token")
        //presenter.getMessage(GetMessageEventListener(), header)
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.removeNavigator()
    }

    inner class GetMessageEventListener : GetMessageListener {
        override fun onResponse(messageResponse: MessageResponse) {
            Toast.makeText(getActivity(), "berhasil",
                    Toast.LENGTH_LONG).show()
            item = messageResponse.categorydata
            mAdapter = MessageListAdapter(this@ImageFullScreenActivity, item!!)
            recyclerView.adapter = this@ImageFullScreenActivity.mAdapter
            mAdapter.setOnItemClickListener(object : MessageListAdapter.OnItemClickListener {
                override fun onItemLongClick(view: View, obj: MessageModel, pos: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemClick(view: View, obj: MessageModel, position: Int) {
                    presenter.onItemSelected()
//                    Snackbar.make(lytParent, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show()
                }
            })
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