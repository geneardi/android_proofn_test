package android.proofn.test.contracts

import android.app.Activity
import android.content.Context

interface BaseContract {
    interface View{
        fun getActivity() : Activity
        fun getContext(): Context
        fun finishView()
    }

    interface Presenter{
        fun onViewCreated()
        fun onViewDestroyed()
    }

    interface Interactor
}