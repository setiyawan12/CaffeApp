package yayang.setiyawan.caffe.contract

import yayang.setiyawan.caffe.model.Produk

class ProductDrinkContract {
    interface View{
        fun attachToRecycler(listProduk : List<Produk>)
        fun emptydata(status:Boolean)
        fun toast(message:String)
    }
    interface Presenter{
        fun getProdukDrink()
        fun destroy()
    }
}