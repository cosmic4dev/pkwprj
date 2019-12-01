package cosmic.com.pkwprj.presenter

import cosmic.com.pkwprj.contract.SecondContract
import cosmic.com.pkwprj.model.Office
import cosmic.com.pkwprj.model.OfficeList
import java.util.*

class SecondPresenter(private val secondView:SecondContract.View): SecondContract.Presenter {


    internal lateinit var map: HashMap<String, Int>
    internal lateinit var list: ArrayList<Office>
    internal lateinit var officeList: OfficeList

//     override fun newgetJsonString():ArrayList<Office> {
//
//        list = ArrayList()
//
//        try {
//
//            val assetManager: AssetManager?=null
//            val inputStrem = assetManager?.open("MUSINSA.json")
//            val jsonString=inputStrem?.bufferedReader().use { it?.readText() }
//            val gson = Gson()
//            officeList = gson.fromJson(jsonString, OfficeList::class.java)
//
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//        }
//
//        if (officeList != null) {
//
//            for (i in officeList!!.musinsa.indices) {
//
//                val office = officeList!!.musinsa.get(i)
//                val name = office.name
//                val location = office.location
//                val reservations = office.reservations
//                val timeBar = ProgressDrawable(map, name)
//                list.add(Office(name, location, reservations, timeBar))
//            }
//        }
//
//        return list
//    }

    override fun processConvert2(endTime: String): Int {
        var code2 = -1

        when (endTime) {
            "0900" -> code2 = 0
            "0930" -> code2 = 1
            "1000" -> code2 = 2
            "1030" -> code2 = 3
            "1100" -> code2 = 4
            "1130" -> code2 = 5
            "1200" -> code2 = 6
            "1230" -> code2 = 7
            "1300" -> code2 = 8
            "1330" -> code2 = 9
            "1400" -> code2 = 10
            "1430" -> code2 = 11
            "1500" -> code2 = 12
            "1530" -> code2 = 13
            "1600" -> code2 = 14
            "1630" -> code2 = 15
            "1700" -> code2 = 16
            "1730" -> code2 = 17
            "1800" -> code2 = 18
        }
        return code2

    }

    override fun processConvert1(startTime: String): Int {

        var code1 =-1

        when (startTime) {
            "0900" -> code1 = 0
            "0930" -> code1 = 1
            "1000" -> code1 = 2
            "1030" -> code1 = 3
            "1100" -> code1 = 4
            "1130" -> code1 = 5
            "1200" -> code1 = 6
            "1230" -> code1 = 7
            "1300" -> code1 = 8
            "1330" -> code1 = 9
            "1400" -> code1 = 10
            "1430" -> code1 = 11
            "1500" -> code1 = 12
            "1530" -> code1 = 13
            "1600" -> code1 = 14
            "1630" -> code1 = 15
            "1700" -> code1 = 16
            "1730" -> code1 = 17
            "1800" -> code1 = 18
        }
        return code1
    }

}
