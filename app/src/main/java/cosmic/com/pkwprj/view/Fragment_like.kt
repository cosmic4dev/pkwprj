package cosmic.com.pkwprj.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.adapter.DataAdapter2
import cosmic.com.pkwprj.contract.MainContract
import cosmic.com.pkwprj.model.DbHelper
import cosmic.com.pkwprj.model.GitHubResult
import cosmic.com.pkwprj.model.GithubOwner
import cosmic.com.pkwprj.presenter.MainPresenter


class Fragment_like: Fragment(),MainContract.view {
    override fun sendToAdapter(data: GitHubResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeKeyboard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal lateinit var recyclerView: RecyclerView
    internal var dataList: List<GithubOwner> ?=null
    var presenter: MainPresenter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_like, container,false)

        recyclerView=rootView.findViewById(R.id.recyclerView_search2)

        getLikeData()

        return rootView
    }


    private fun getLikeData() {

        var temList:ArrayList<GithubOwner> = ArrayList()

       var dbHelper = DbHelper(activity,"HUB.db", null, 1)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        for(item in dbHelper.dataList) {
            temList.add(item)
        }

        val adapter = DataAdapter2(context!!,temList)
        recyclerView.adapter = adapter

    }
}