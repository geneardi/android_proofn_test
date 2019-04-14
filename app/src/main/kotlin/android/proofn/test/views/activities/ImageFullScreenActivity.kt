package android.proofn.test.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.proofn.test.BaseApplication
import android.proofn.test.R
import android.proofn.test.contracts.ImageFullScreenContract
import android.proofn.test.entities.AvatarModel
import android.proofn.test.entities.MessageModel
import android.proofn.test.entities.responses.DefaultResponse
import android.proofn.test.interactors.ImageFullScreenInteractor
import android.proofn.test.interactors.outputs.SendImageListener
import android.proofn.test.presenters.ImageFullScreenPresenter
import android.proofn.test.views.adapters.MessageListAdapter
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlinx.android.synthetic.main.activity_image_fullscreen.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ImageFullScreenActivity : BaseActivity<ImageFullScreenPresenter>(), ImageFullScreenContract.View {

    private var item : List<MessageModel>? = null
    private lateinit var header : String
    private lateinit var id : String
    private val GALLERY = 1
    private val CAMERA = 2
    private val imageView: ImageView? by lazy { fullScreenImage }
    private val editImageButton: ImageButton? by lazy { bt_accounts }
    private var urlImage: String? = null

    private lateinit var mAdapter: MessageListAdapter
    companion object {
        const val TAG: String = "HomeActivity"
        private val IMAGE_DIRECTORY = "/Pictures"
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
//        Glide
//                .with(getContext())
//                .load(urlImage)
//                .into(imageView!!)
        editImageButton!!.setOnClickListener { choosePhotoFromGallary() }
        this.init()
    }

    private fun init(){
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
        header = "Bearer "+Hawk.get("token")
    }

    private fun choosePhotoFromGallary() {
        val checkSelfPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
        else{
            openAlbum()
        }
    }

    private fun openAlbum(){
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    imageView?.setImageBitmap(bitmap)
                    saveImage(bitmap, contentURI)
                    Toast.makeText(this@ImageFullScreenActivity, "Image Saved!", Toast.LENGTH_SHORT).show()

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@ImageFullScreenActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 ->
                if (grantResults.isNotEmpty() && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    openAlbum()
                }
                else {
                    Toast.makeText(this, "You deied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveImage(myBitmap: Bitmap, uri : Uri?):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                    .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val requestfile = RequestBody.create(MediaType.parse(contentResolver.getType(uri)), f)
            val body = MultipartBody.Part.createFormData("avatar", f.name, requestfile)
            presenter.sendImage(this.SendImageEventListener(), "Bearer "+Hawk.get("token") ,body)
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    override fun onPause() {
        super.onPause()
        BaseApplication.INSTANCE.proofnCicerone.navigatorHolder.removeNavigator()
    }

    inner class SendImageEventListener : SendImageListener {
        override fun onResponse(avatarModel: AvatarModel) {
            Toast.makeText(getActivity(), avatarModel.avatarPathMedium,
                    Toast.LENGTH_LONG).show()


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