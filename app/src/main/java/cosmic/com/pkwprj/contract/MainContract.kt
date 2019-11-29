package cosmic.com.pkwprj.contract

interface MainContract {

    interface view

    interface presenter {
        //        fun searchData(searchUserName:String)
        fun getJsonData(searchUserName: String)

        //        void searchData(String searchUserName);
    }
}
