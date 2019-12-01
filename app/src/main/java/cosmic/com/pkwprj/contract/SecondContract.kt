package cosmic.com.pkwprj.contract

interface SecondContract {

    interface View {
    }


    interface Presenter {

        fun processConvert2(et:String): Int
        fun processConvert1(st:String): Int
//        fun newgetJsonString(): ArrayList<Office>
    }
}


