package cosmic.com.pkwprj.contract

interface MainContract {

    interface view{
        fun showToast(msg:String)
        fun closeKeyboard()
    }

    interface presenter {
        //        fun searchData(searchUserName:String)
        fun getJsonData(searchUserName: String)
        //        void searchData(String searchUserName);
    }
}
