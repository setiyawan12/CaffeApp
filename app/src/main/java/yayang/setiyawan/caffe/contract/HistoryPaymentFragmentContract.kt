package yayang.setiyawan.caffe.contract

import yayang.setiyawan.caffe.model.Transaksi

interface HistoryPaymentFragmentContract {
    interface view{
        fun attachtoRecyle(data : ArrayList<Transaksi>)
        fun emptyData()
        fun toast(message:String)
    }
    interface presenter{
        fun getPayment(id:String)
    }
}