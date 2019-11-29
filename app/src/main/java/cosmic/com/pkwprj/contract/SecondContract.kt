package cosmic.com.pkwprj.contract

/**
 * 각각 역할을 가진 Contract(계약)를 정의해두는 인터페이스
 */
interface SecondContract {

    interface View {
    }


    interface Presenter {

        fun processConvert2(et:String): Int
        fun processConvert1(st:String): Int
        fun newgetJsonString(time:String)
    }
}


