package cosmic.com.pkwprj.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.adapter.DataAdapter
import cosmic.com.pkwprj.contract.MainContract
import cosmic.com.pkwprj.model.DbHelper
import cosmic.com.pkwprj.model.GithubOwner
import cosmic.com.pkwprj.presenter.MainPresenter


class Fragment_like: Fragment(),MainContract.view {

    internal lateinit var recyclerView: RecyclerView
    internal var dataList: List<GithubOwner> ?=null
    internal var TAG:String="라이크프래그먼"

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
            Log.d(TAG,"item 확인:->"+item.login)
        }

        val adapter = DataAdapter(context,temList)
        recyclerView.adapter = adapter

    }


    fun displaydata(items: List<GithubOwner>) {

        Log.d("TAG","확인2::"+items.toString()) //ok!


        Log.d("TAG","사이즈2::"+items.size)
//        dataToview(ownerList)
        sendToAdapter(items)
    }

//    override fun onItemClicked(githubOwner: GithubOwner) {
//
//        val intent = Intent(context,DetailActivity::class.java)
//        startActivity(intent)
//
//    }

    private fun sendToAdapter(dataList: List<GithubOwner>?) {
        Log.d("TAG","확인리스트:->"+dataList)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = DataAdapter(context,dataList)
//        val adapter = DataAdapter( context)
        recyclerView.adapter = adapter
//        adapter.setOnClickListener(this)

    }


}