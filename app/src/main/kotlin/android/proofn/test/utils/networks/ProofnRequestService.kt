package android.proofn.test.utils.networks

import android.proofn.test.entities.MessageModel
import android.proofn.test.entities.MessageSentModel
import android.proofn.test.entities.UserModel
import android.proofn.test.entities.messages.*
import android.proofn.test.entities.responses.*
import io.reactivex.Observable
import retrofit2.http.*

interface ProofnRequestService {


    @Headers("Content-Type: application/json")
    @POST("/v1/auth/login")
    fun login(@Body message: LoginMessage): Observable<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/v1/message/send")
    fun sendMessage(@Header("Authorization") value: String, @Body message: SendMessageMessage)
            : Observable<MessageSentModel>

    @GET("/v1/messages/inbox")
    fun getMessage(@Header("Authorization") value: String): Observable<MessageResponse>

    @GET("/v1/messages/inbox/{id}")
    fun getMessageDetail(@Header("Authorization") value: String,
                         @Path("id") id: String): Observable<MessageModel>

    @GET("/v1/user/profile")
    fun getUserProfile(@Header("Authorization") value: String): Observable<UserModel>
}