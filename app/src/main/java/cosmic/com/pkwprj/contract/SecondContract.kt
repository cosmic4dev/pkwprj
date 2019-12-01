package cosmic.com.pkwprj.contract

import cosmic.com.pkwprj.model.Office

interface SecondContract {

    interface View {
    }


    interface Presenter {

        fun processConvert2(et:String): Int
        fun processConvert1(st:String): Int
        fun newgetJsonString(): ArrayList<Office>
    }
}


