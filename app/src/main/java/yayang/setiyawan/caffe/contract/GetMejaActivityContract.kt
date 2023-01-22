package yayang.setiyawan.caffe.contract

import android.content.Context
import yayang.setiyawan.caffe.model.Meja

interface GetMejaActivityContract {
    interface GetMejaActivityView{
        fun showToast(message:String)
        fun successMeja()
        fun showLoading()
        fun hideLoading()
    }
    interface  GetMejaPresenter{
        fun meja(no_meja:String,context: Context)
        fun destroy()
    }

    interface AllMejaActivityView{
        fun attachToRecycler(listMeja : List<Meja>)
        fun showToast(message: String)
        fun showLoading()
        fun hideLoading()
    }
    interface AllMejaPresenter{
        fun allmeja()
        fun destroy()
    }
}