package com.blocksdecoded.coinwave.presentation.posts

import com.blocksdecoded.utils.coroutine.model.onError
import com.blocksdecoded.utils.coroutine.model.onSuccess
import com.blocksdecoded.coinwave.domain.usecases.posts.PostsUseCases
import com.blocksdecoded.coinwave.presentation.main.MenuClickListener
import com.blocksdecoded.core.mvp.BaseMvpPresenter
import com.blocksdecoded.utils.coroutine.launchSilent
import com.blocksdecoded.utils.coroutine.model.onResult

class PostsPresenter(
    override var view: PostsContract.View?,
    private val mMenuListener: MenuClickListener,
    private val mPostUseCases: PostsUseCases
) : BaseMvpPresenter<PostsContract.View>(), PostsContract.Presenter {

    private var mInitialized = false

    override fun onResume() {
        super.onResume()
        if (!mInitialized) {
            mInitialized = true
            getPosts()
        }
    }

    //region Contract

    override fun onPostClick(id: Int) = launchSilent(scope) {
        mPostUseCases.getPost(id)?.also {
            it.url?.also {
                view?.openPost(it)
            }
        }
    }

    override fun getPosts() = launchSilent(scope) {
        view?.showLoading()
        mPostUseCases.getPosts()
            ?.onResult { view?.stopLoading() }
            ?.onSuccess { view?.showPosts(it) }
            ?.onError { view?.showLoadingError() }
    }

    override fun getNextPosts() = launchSilent(scope) {
        mPostUseCases.getNextPosts()
                ?.onSuccess { view?.nextPosts(it) }
                ?.onError { view?.showErrorMessage() }
    }

    override fun onMenuClick() {
        mMenuListener.onMenuClick()
    }

    //endregion
}