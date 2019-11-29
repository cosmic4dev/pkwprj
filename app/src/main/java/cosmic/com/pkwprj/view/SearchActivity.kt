package cosmic.com.pkwprj.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cosmic.com.pkwprj.R
import cosmic.com.pkwprj.adapter.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        supportFragmentManager.beginTransaction()
            .replace(R.id.viewPager,Fragment_search())
            .commit()

        val fragmentAdapter= MyPagerAdapter(supportFragmentManager)
        viewPager.adapter=fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)
    }

}