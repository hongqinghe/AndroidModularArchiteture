package com.effective.android.component.system.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.effective.android.base.fragment.BaseVmFragment
import com.effective.android.component.system.vm.SystemViewModel
import com.effective.android.component.system.R
import com.effective.android.component.blog.bean.Chapter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.system_fragment_laout.*

class SystemFragment: BaseVmFragment<SystemViewModel>() {

    private var fetchProjectDisposable: Disposable? = null
    val articleFragments: HashMap<Int, ArticleParentFragment> = HashMap()
    var chapters: List<Chapter>? = null

    override fun getViewModel(): Class<SystemViewModel> = SystemViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.system_fragment_laout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pageState.toLoading("正在加载")
        initData()
    }

    private fun initData() {
        pageState.visibility = View.VISIBLE
        fetchProjectDisposable = viewModel.getTreeChapters()
                .subscribe({
                    if (it.isSuccess) {
                        if (!it.data.isNullOrEmpty()) {
                            pageState.visibility = View.GONE
                            chapters = it.data!!
                            articlePager.adapter = object : FragmentPagerAdapter(childFragmentManager) {

                                override fun getItem(position: Int): Fragment {
                                    var fragment: ArticleParentFragment? = articleFragments[position]
                                    if (fragment == null) {
                                        fragment = ArticleParentFragment(chapters!![position].children)
                                        articleFragments[position] = fragment
                                    }
                                    return fragment
                                }

                                override fun getCount(): Int = chapters!!.size

                                override fun getPageTitle(position: Int): CharSequence? = chapters!![position].name
                            }
                            tabLayout.setViewPager(articlePager)
                        } else {
                            pageState.toEmpty("当前页面没有项目", "尝试刷新", Runnable {
                                pageState.toLoading("正在加载")
                                initData()
                            })
                        }
                    } else {
                        pageState.toEmpty("网络请求失败", "重新刷新", Runnable {
                            pageState.toLoading("正在加载")
                            initData()
                        })
                    }

                }, {
                    pageState.toEmpty("网络请求失败", "重新刷新", Runnable {
                        pageState.toLoading("正在加载")
                        initData()
                    })
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (fetchProjectDisposable != null && fetchProjectDisposable!!.isDisposed) {
            fetchProjectDisposable?.dispose()
        }
    }
}