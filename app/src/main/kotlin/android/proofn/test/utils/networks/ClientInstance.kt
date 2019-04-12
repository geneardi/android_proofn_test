package android.proofn.test.utils.networks

import com.google.gson.GsonBuilder
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.https.HttpsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ClientInstance class
 */
class ClientInstance {

    /**
     *
     * @param baseUrl The consistent root of the web address
     * @return Instance of retrofit to execute network requests
     */
    fun getRetrofitInstance(baseUrl: String): Retrofit {

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)

        val client = OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build()
        OkHttpUtils.initClient(client)

        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(client)
                .build()
    }


}