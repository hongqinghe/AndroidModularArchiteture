package com.effective.android.component.system.vm

import androidx.lifecycle.ViewModel
import com.effective.android.base.rxjava.RxSchedulers
import com.effective.android.component.system.data.SystemRepository
import com.effective.android.component.blog.bean.Chapter
import com.effective.android.service.net.BaseResult
import io.reactivex.Flowable

class SystemViewModel  : ViewModel(){

    fun getTreeChapters(): Flowable<BaseResult<List<Chapter>>> {
        return SystemRepository.get().getTreeChapters()
                .compose(RxSchedulers.flowableIoToMain())
    }
}