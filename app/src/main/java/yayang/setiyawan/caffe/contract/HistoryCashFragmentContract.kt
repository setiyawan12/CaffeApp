package yayang.setiyawan.caffe.contract

import yayang.setiyawan.caffe.model.Transaksi

interface HistoryCashFragmentContract {
    interface view{
        fun attachtoRecyle(data : ArrayList<Transaksi>)
        fun emptyData()
        fun toast(message:String)
    }
    interface presenter{
        fun getcash(id:String)
    }
}