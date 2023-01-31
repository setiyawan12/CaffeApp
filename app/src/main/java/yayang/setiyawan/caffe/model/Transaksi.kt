package yayang.setiyawan.caffe.model

class Transaksi {
    var id = 0
    var name = ""
    var total_harga = ""
    var total_item = ""
    var kode_payment = ""
    var payment_method = ""
    var kode_trx =""
    var kode_unik = 0
    var status = ""
    var meja =""
    var created_at = ""
    var order_id = ""
    val details = ArrayList<DetailTransaksi>()
}