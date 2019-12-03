package cosmic.com.pkwprj.contract

import android.content.res.Resources
import cosmic.com.pkwprj.model.Office

interface SecondContract {

    interface View {
        fun showAvailableOfficeCode()
        fun showList()
    }


    interface Presenter {

        fun processConvert2(et:String): Int
        fun processConvert1(st:String): Int
        fun newgetJsonString(time:String,resouce: Resources): ArrayList<Office>
        fun MakeMapData(startPoint: Int, endPoint: Int, officeName: String?)
    }
}


