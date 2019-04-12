package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.HomeContract
import android.proofn.test.entities.MessageModel
import android.proofn.test.entities.UserModel
import android.proofn.test.entities.responses.MessageResponse
import android.proofn.test.interactors.HomeInteractor
import android.proofn.test.interactors.outputs.GetMessageListener
import android.proofn.test.interactors.outputs.GetUserProfileListener
import android.proofn.test.presenters.HomePresenter
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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_drawer_content.*
import kotlinx.android.synthetic.main.include_drawer_header_mail_with_account.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {

    private var item : List<MessageModel>? = null
    private lateinit var header : String
    private lateinit var navigation_header: View

    private val textViewName: TextView? by lazy { name }
    private val textViewEmail: TextView? by lazy { email }
    private val textViewPhone: TextView? by lazy { phone }
    private val imageViewAvatar : ImageView by lazy { image }
    private val toolbars : Toolbar? by lazy { toolbar }
    private val nav_views : NavigationView by lazy { nav_view }
    private var actionBar: ActionBar? = null
    private val drawer : DrawerLayout by lazy { drawer_layout }
    var urlImage: String? = null
    private var isImageFitToScreen: Boolean = false

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
                val data = (command.transitionData)
                when (command.screenKey) {
                    MessageDetailActivity.TAG -> startActivity(Intent(this@HomeActivity, MessageDetailActivity::class.java).
                    putExtra("idMessage", data as String))
                    ImageFullScreenActivity.TAG -> startActivity(Intent(this@HomeActivity, ImageFullScreenActivity::class.java).
                            putExtra("url", data as String))
                    SendMessageActivity.TAG -> startActivity(Intent(this@HomeActivity, SendMessageActivity::class.java))
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

    override fun instantiatePresenter(): HomePresenter {
        return HomePresenter(this, HomeInteractor())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.init()
        this.initToolbar()
        this.initNavigationMenu()
    }

    private fun init(){
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        header = "Bearer "+Hawk.get("token")
        presenter.getMessage(GetMessageEventListener(), header)
        presenter.getUserProfile(GetUserProfileEventListener(), header)
        Log.i("token", header)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.adapter = MessageListAdapter(this, item!!)
    }

    private fun initToolbar() {
        toolbars?.setNavigationIcon(R.drawable.ic_menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Proofn Test"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_chips, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_send) {
////            finish()
            presenter.onSendMessageSelected()
        }
        else if(item.itemId == android.R.id.home){
            finish()
            //presenter.getMessageDetail(GetMessageDetailEventListener(), header)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.setNavigator(navigator)
        presenter.onViewCreated()
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
            mAdapter = MessageListAdapter(this@HomeActivity, item!!)
            recyclerView.adapter = this@HomeActivity.mAdapter
            mAdapter.setOnItemClickListener(object : MessageListAdapter.OnItemClickListener {
                override fun onItemLongClick(view: View, obj: MessageModel, pos: Int) {
                    Toast.makeText(getActivity(), obj.contentPreview +"selected",
                            Toast.LENGTH_LONG).show()
                    view.isSelected = !view.isSelected
                }

                override fun onItemClick(view: View, obj: MessageModel, position: Int) {
                    presenter.onItemSelected(obj.id!!)
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

    private fun initNavigationMenu() {
        val toggle = object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
        }
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        nav_views.setNavigationItemSelectedListener { item ->
            Toast.makeText(applicationContext, item.title.toString() + " Selected", Toast.LENGTH_SHORT).show()
            actionBar?.title = item.title
            drawer.closeDrawers()
            true
        }

        navigation_header = nav_view.getHeaderView(0)
        navigation_header.findViewById<CircularImageView>(R.id.avatar).setOnClickListener {
            presenter.onImageSelected(urlImage)
        }
    }
    inner class GetUserProfileEventListener : GetUserProfileListener {
        override fun onResponse(userModel: UserModel) {
            Toast.makeText(getActivity(), "berhasil",
                    Toast.LENGTH_LONG).show()
            textViewName?.text = userModel.email
            textViewEmail?.text = userModel.fullName
            textViewPhone?.text = userModel.phoneNumber
            urlImage = "https://beta.proofn.com/v1/user/profile"+userModel.avatar!!.avatarPathMedium
            Glide
                    .with(getContext())
                    .load(urlImage)
                    .into(imageViewAvatar)
            Log.i("image",urlImage)
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