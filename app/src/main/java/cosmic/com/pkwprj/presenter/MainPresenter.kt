package cosmic.com.pkwprj.presenter

import cosmic.com.pkwprj.Retrofit.GithubClient
import cosmic.com.pkwprj.contract.MainContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val mainView: MainContract.view) : MainContract.presenter {



    override fun searchData(searchUserName: String) {

        val disposable = GithubClient().getApi().getUserInfo(searchUserName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    data->mainView.sendToAdapter(data)
            },{ error->
                error.printStackTrace()
            })
    }

    override fun getLikeData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJsonData(searchUserName: String) {

    }


}
