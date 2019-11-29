package cosmic.com.pkwprj.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cosmic.com.pkwprj.view.Fragment_like
import cosmic.com.pkwprj.view.Fragment_search

class MyPagerAdapter:FragmentPagerAdapter {

    // 객체 생성
    var data1 : Fragment = Fragment_search()
    var data2 : Fragment = Fragment_like()

    private val tabTitles = arrayOf("Search Tab", "Like Tab")


    // 초기화
    var mData : ArrayList<Fragment> = arrayListOf(data1,data2)


    constructor(fm : FragmentManager) : super(fm){

    }


    override fun getItem(position: Int): Fragment {
        return mData.get(position)
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}