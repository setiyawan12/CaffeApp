package yayang.setiyawan.caffe.contract

import android.content.Context
import yayang.setiyawan.caffe.model.Midtrans

interface DetailHistoryTransactionMidtrans {
    interface view{
        fun success (result:Midtrans)
        fun toast(message: String)
    }
    interface presenter{
        fun detailMidtrans(id:String,context: Context)
    }
}