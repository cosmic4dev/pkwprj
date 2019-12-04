package cosmic.com.pkwprj.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.adapter.DataAdapter
import cosmic.com.pkwprj.contract.MainContract
import cosmic.com.pkwprj.databinding.ItemSearchBinding
import cosmic.com.pkwprj.model.GitHubResult
import cosmic.com.pkwprj.presenter.MainPresenter
import kotlinx.android.synthetic.main.fragment_search.*


class Fragment_search: Fragment(),MainContract.view {

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var mainPresenter:MainPresenter

    lateinit var searchUserName:String
    lateinit var binding:ItemSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding=DataBindingUtil.inflate(inflater, R.layout.item_search,container,false)
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        mainPresenter=MainPresenter(this)

        recyclerView=rootView.findViewById(cosmic.com.pkwprj.R.id.recyclerView_search1)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=layoutManager

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchUserName=inputText.text.toString()
                mainPresenter.searchData(searchUserName)
                true
            } else {
                false
            }
        }

        searchBtn.setOnClickListener {
            searchUserName=inputText.text.toString()
            mainPresenter.searchData(searchUserName)

        }

        delButton.setOnClickListener{
            inputText.text.clear()
        }

    }


    override fun sendToAdapter(dataList: GitHubResult) {
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = DataAdapter(context,dataList,searchUserName)
        recyclerView.adapter = adapter

    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

     override fun closeKeyboard() {
         val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
    }
}