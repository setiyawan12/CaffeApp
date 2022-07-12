package yayang.setiyawan.caffe.model

class Chekout {
    lateinit var customer_id :String
    lateinit var total_item :String
    lateinit var total_harga :String
    lateinit var name :String
    lateinit var meja:String
    lateinit var order_id:String


    var produks = ArrayList<Item>()

    class Item{
        lateinit var id : String
        lateinit var total_item : String
        lateinit var total_harga : String
        lateinit var catatan : String
    }
}