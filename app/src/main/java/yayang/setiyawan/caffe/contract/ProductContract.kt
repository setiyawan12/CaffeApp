package yayang.setiyawan.caffe.contract

import yayang.setiyawan.caffe.model.Produk

class ProductContract {
    interface View{
        fun attachToRecycler(listProduk : List<Produk>)
        fun attachToRecyclerFood(listProduk: List<Produk>)
        fun attachToRecyclerSnack(listProduk: List<Produk>)
        fun emptydata(status:Boolean)
        fun toast(message:String)
    }

    interface Presenter{
        fun getAllProduct()
        fun getProductFood()
        fun getProductSnack()
        fun destroy()
    }
}