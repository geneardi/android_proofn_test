package android.proofn.test.utils.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import android.proofn.test.BASE_URL
import android.proofn.test.utils.networks.ClientInstance
import android.proofn.test.utils.networks.ProofnRequestService
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Module which provides all required dependencies about network
 */
@Module
object NetworkModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideProofnRequestService(@Named("RetrofitInstance")
                                                 retrofit:  Retrofit): ProofnRequestService {
        return retrofit.create(ProofnRequestService::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    @Named("RetrofitInstance")
    internal fun provideUnsafetyRetrofitInstance(): Retrofit {
        return ClientInstance().getRetrofitInstance(BASE_URL)
    }
}