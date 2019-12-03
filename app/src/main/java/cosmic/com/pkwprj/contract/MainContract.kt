package cosmic.com.pkwprj.contract

import cosmic.com.pkwprj.model.GitHubResult

interface MainContract {

    interface view{
        fun showToast(msg:String)
        fun closeKeyboard()
        fun sendToAdapter(data: GitHubResult)
    }

    interface presenter {
                fun searchData(searchUserName:String)
        fun getJsonData(searchUserName: String)
        //        void searchData(String searchUserName);
        fun getLikeData()
    }
}
