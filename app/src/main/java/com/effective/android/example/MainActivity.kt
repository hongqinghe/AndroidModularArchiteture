package com.effective.android.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.effective.android.base.activity.BaseActivity
import com.effective.android.base.systemui.StatusbarHelper
import example.androidmodulararchiteture.R
import kotlinx.android.synthetic.main.app_activity_main_layout.*

/**
 * e6e6e6
 */
class MainActivity : BaseActivity() {

    private val fragmentList = mutableListOf<Fragment>()

    override fun getLayoutRes(): Int {
        return R.layout.app_activity_main_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusbarHelper.translucentStatusBar(this)
        initData()
        initView()
    }

    private fun initData() {
        fragmentList.add(Sdks.componentBlogSdk.getMainFragment())
        fragmentList.add(Sdks.componentProjectSdk.getMainFragment())
        fragmentList.add(Sdks.componentPaccountsSdk.getMainFragment())
        fragmentList.add(Sdks.componentSystemSdk.getMainFragment())
        fragmentList.add(Sdks.componentMineSdk.getMainFragment())
    }

    private fun initView() {
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = fragmentList[position]

            override fun getCount(): Int = fragmentList.size
        }
        bottomTab.setupWithViewPager(viewPager)
    }
}
