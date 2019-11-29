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
import cosmic.com.pkwprj.Retrofit.GithubClient
import cosmic.com.pkwprj.adapter.DataAdapter
import cosmic.com.pkwprj.model.GithubOwner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class Fragment_search: Fragment() {


    //    internal lateinit var listAdapter:SearchListAdapter
    internal lateinit var recyclerView: RecyclerView

    var dataList:List<GithubOwner>?=null

    lateinit var searchUserName:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

//        listAdapter= SearchListAdapter(requireContext(),dataList)
        recyclerView=rootView.findViewById(R.id.recyclerView_search1)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=layoutManager
//        recyclerView.adapter=listAdapter


        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG","온뷰크레이트 통")

        searchBtn.setOnClickListener {
            searchUserName=inputText.text.toString()
            searchData(searchUserName)


        }

        delButton.setOnClickListener{
            var searchUserName=inputText.text.clear()
        }


    }




    fun searchData(searchUserName:String){

         val disposable = GithubClient().getApi().getOwners(searchUserName)
             .subscribeOn(Schedulers.io())
             .map { data-> listOf(data) }//주요
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe{data->displaydata(data) }
    }

     fun displaydata(items: List<GithubOwner>) {
        sendToAdapter(items)
    }


    private fun sendToAdapter(dataList: List<GithubOwner>?) {
        Log.d("TAG","확인리스트:->"+dataList)
        var temList=dataList

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = DataAdapter(context,dataList,searchUserName,true)
//        val adapter = DataAdapter( context)
        recyclerView.adapter = adapter

    }

}