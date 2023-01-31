package yayang.setiyawan.caffe.contract

interface CancleTransactionContract {
    interface CancleTransactionContractView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message:String)
    }
    interface CancleTransactionContractPresenter{
        fun cancle(id:String)
        fun destroy()
    }
}