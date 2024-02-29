package viewModel.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class FragmentsAdapter(manager: FragmentManager) :
    FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var FragmentList: MutableList<Fragment> = ArrayList()
    var FragmentTitle: MutableList<String> = ArrayList()
    override fun getCount(): Int {
        return FragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return FragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return FragmentTitle[position]
    }

     fun addFragment(fragment: Fragment, title : String) {
        FragmentTitle.add(title)
         FragmentList.add(fragment)
    }


}