package com.example.test.adapter


import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Array.newInstance
//import javax.xml.datatype.DatatypeFactory.newInstance
//
////work with viewpager2 for sliding between fragments
////for each item in cityList, create one individual fragment
//class FragmentAdapter(fragmentActivity: FragmentActivity, val cityList: List<String>): FragmentStateAdapter(fragmentActivity) {
//
//    override fun createFragment(position: Int): TabFragment {
//        //create a new fragment with a passed argument of the city name
//        return TabFragment.newInstance(cityList[position])
//    }
//    override fun getItemCount(): Int {
//        return cityList.size
//    }
//
//}