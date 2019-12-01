package cosmic.com.pkwprj.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.Retrofit.GithubClient
import cosmic.com.pkwprj.adapter.DataAdapter
import cosmic.com.pkwprj.model.GitHubResult
import cosmic.com.pkwprj.presenter.MainPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class Fragment_search: Fragment() {

    internal lateinit var recyclerView: RecyclerView

    lateinit var searchUserName:String

     var mainPresenter:MainPresenter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView=rootView.findViewById(R.id.recyclerView_search1)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=layoutManager

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBtn.setOnClickListener {
            searchUserName=inputText.text.toString()
            searchData(searchUserName)
        }

        delButton.setOnClickListener{
            inputText.text.clear()
        }

    }




    fun searchData(searchUserName:String){

        val disposable = GithubClient().getApi().getUserInfo(searchUserName)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({
                        data->sendToAdapter(data)
             },{ error->
                 error.printStackTrace()
             })

    }


    private fun sendToAdapter(dataList: GitHubResult) {
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = DataAdapter(context,dataList,searchUserName)
//        val adapter = DataAdapter( context)
        recyclerView.adapter = adapter

    }

}