package yayang.setiyawan.caffe.contract

import android.content.Context
import android.content.Intent
import yayang.setiyawan.caffe.model.Chekout

interface PembayaranActivityContract {
    interface View{
        fun successBayarCash(pindah: Intent)
    }
    interface Presenter{
        fun bayarCash(context: Context, body : Chekout)
        fun bayarCashless(context: Context,body: Chekout)
    }
}