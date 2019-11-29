package cosmic.com.pkwprj.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cosmic.com.pkwprj.R
import kotlinx.android.synthetic.main.fragment_search.*

class Main2Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setupView()

    }

    private fun setupView() {


        recyclerView_search.setLayoutManager(LinearLayoutManager(this))
        val repositoryAdapter = RepositoryAdapter(
            this as Context,
            this as RepositoryAdapter.OnRepositoryItemClickListener
        )
        recyclerView_search.setAdapter(repositoryAdapter)
    }
}