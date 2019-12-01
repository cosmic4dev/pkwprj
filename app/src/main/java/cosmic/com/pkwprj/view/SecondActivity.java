package cosmic.com.pkwprj.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.adapter.ProgressAdapter
import cosmic.com.pkwprj.contract.SecondContract
import cosmic.com.pkwprj.model.Office
import cosmic.com.pkwprj.model.OfficeList
import cosmic.com.pkwprj.presenter.SecondPresenter
import java.text.SimpleDateFormat
import java.util.*

class SecondActivity : AppCompatActivity(), SecondContract.View {

    internal lateinit var progressBar: ProgressBar

    internal lateinit var officeList: OfficeList
    internal lateinit var list: ArrayList<Office>

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adjustTime: String

    internal lateinit var map: HashMap<String, Int>
    internal lateinit var drawables: ArrayList<Drawable>

    internal lateinit var secondPresenter: SecondPresenter
    internal lateinit var tv_office1: TextView
    internal lateinit var tv_office2: TextView
    internal lateinit var tv_office3: TextView
    internal lateinit var tv_move: TextView

    private val currentTime: String
        get() {
            val dateFormat = SimpleDateFormat("HHmm")
            val cTime = System.currentTimeMillis()

            return dateFormat.format(cTime)

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        secondPresenter = SecondPresenter(this)

        map = HashMap()
        drawables = ArrayList()

        progressBar = findViewById(R.id.progressBar3)

        tv_office1 = findViewById(R.id.tv_office1)
        tv_office2 = findViewById(R.id.tv_office2)
        tv_office3 = findViewById(R.id.tv_office3)

        tv_move = findViewById(R.id.tv_move)

        recyclerView = findViewById(R.id.recyclerView_office)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val getTime = currentTime

        //        String getTime = "1739";     //test시간
        avaibleTimeCheck(getTime)

                list=secondPresenter.newgetJsonString();//조정된 시간 넣어주기
//        newgetJsonString()//조정된 시간 넣어주기
        showOfficeTimeTable(adjustTime)

    }


    private fun showOfficeTimeTable(adjustTime: String) {

        for (i in list.indices) {
            val office = list[i]
            val name = office.name
            val location = office.location
            val reservationList = office.reservations

            for (j in reservationList!!.indices) {
                val reservationStaus = reservationList[j]

                val startTime = reservationStaus.startTime

                val compare1 = Integer.valueOf(startTime!!)
                val compare2 = Integer.valueOf(adjustTime)

                if (compare1 >= compare2 || compare1 == 1700 && compare2 < 1800) {

                    if (compare1 == compare2) {
                        nonShowTopOfficeList(name!!)
                    } else if (compare1 == 1700 && compare2 == 1730) {
                        nonShowTopOfficeList(name!!)
                    }
                    val endTime = reservationStaus.endTime

                    Log.d(TAG, "name->" + name!!)
                    Log.d(TAG, "location->" + location!!)
                    Log.d(TAG, "reser start to end=$startTime~$endTime")

                    val a = secondPresenter.processConvert1(startTime)
                    val b = secondPresenter.processConvert2(endTime!!)
                    MakeMapData(a, b, name)
                } else if (compare2 >= 1800) {
                    tv_office1.visibility = View.GONE
                    tv_office2.visibility = View.GONE
                    tv_office3.visibility = View.GONE
                }
            }
        }

    }

    private fun nonShowTopOfficeList(officename: String) {

        when (officename) {
            "대회의실1" -> tv_office1.visibility = View.GONE
            "회의실2" -> tv_office2.visibility = View.GONE
            "회의실3" -> tv_office3.visibility = View.GONE
        }

    }


    private fun avaibleTimeCheck(time: String) {

        val hourTime = time.substring(0, 2)
        val minuteTime = time.substring(2)
        val mTime = Integer.valueOf(minuteTime)

        if (mTime < 30) {
            val preMtime = "00"
            adjustTime = hourTime + preMtime

        } else if (30 <= mTime && mTime <= 59) {
            val preMtime = "30"
            adjustTime = hourTime + preMtime

        }

        val TimeConvertToInt = Integer.valueOf(time)
        if (TimeConvertToInt < 900) {
            Log.d(TAG, "아직개업전")
        } else if (900 <= TimeConvertToInt && TimeConvertToInt < 1800) {
            Log.d(TAG, "영업중")
        } else {
            Log.d(TAG, "영업마감")
        }

    }

//    private fun newgetJsonString(): List<*> {
//
//        list = ArrayList()
//
//        try {
//            val `is` = assets.open("MUSINSA.json")
//            val buffer = ByteArray(`is`.available())
//            `is`.read(buffer)
//            `is`.close()
//            val json = String(buffer, "UTF-8")
//            val gson = Gson()
//            officeList = gson.fromJson(json, OfficeList::class.java)
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//        }
//
//        if (officeList != null) {
//            for (i in 0 until officeList!!.musinsa.size) {
//
//                val office = officeList!!.musinsa[i]
//                val name = office.name
//                val location = office.location
//                val reservations = office.reservations
//                val d = ProgressDrawable(map, name)
//                list.add(Office(name, location, reservations, d)) //여기서 두 시간 포인트가 일치하지않으면 list에 못담
//            }
//        }
//
//        return list
//    }


    private fun MakeMapData(startPoint: Int, endPoint: Int, officeName: String) {
        var inputKey: String
        for (l in list.indices) {
            if (endPoint - startPoint == 1) {
                inputKey = l.toString() + officeName
                map[inputKey] = startPoint
            } else if (endPoint - startPoint > 1) {
                for (i in startPoint until endPoint) {
                    inputKey = i.toString() + officeName
                    map[inputKey] = i
                }
            }
        }
        showAvailableOfficeCode()
        showList()
    }

    private fun showAvailableOfficeCode() {

        convertedKey = secondPresenter.processConvert1(adjustTime)

    }

    private fun showList() {

        val adapter = ProgressAdapter(this, list)
        recyclerView.adapter = adapter
    }

    companion object {

        internal val TAG = "Main"
        var convertedKey: Int = 0
    }
}
