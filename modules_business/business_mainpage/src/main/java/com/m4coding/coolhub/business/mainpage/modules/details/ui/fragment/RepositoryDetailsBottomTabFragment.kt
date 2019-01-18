package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.widget.ActionSheetDialog
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.utils.DialogUtils
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.base.widgets.BusinessProgressDialog
import com.m4coding.coolhub.business.base.widgets.CustomSheetDialog
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.business_mainpage_fragment_repository_details_bottom_tab.*

/**
 * 仓库详情底部操作栏
 */
class RepositoryDetailsBottomTabFragment : BaseFragment(), View.OnClickListener {

    private var mRepoBean: RepoBean? = null
    private var mProgressDialog: BusinessProgressDialog? = null
    private var branchDialog: CustomSheetDialog? = null


    companion object {
        private const val KEY_REPO = "key_repo"

        fun newInstance(repoBean: RepoBean): RepositoryDetailsBottomTabFragment {
            val fragment = RepositoryDetailsBottomTabFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_bottom_tab, container, false)
    }


    override fun initData() {
        business_mainpage_repo_details_bottom_tab_ll_star.setOnClickListener(this)
        business_mainpage_repo_details_bottom_tab_ll_watch.setOnClickListener(this)
        business_mainpage_repo_details_bottom_tab_ll_branch.setOnClickListener(this)


        mRepoBean = arguments?.getParcelable(KEY_REPO)

        updateState()
    }


    /**
     * 更新状态
     */
    @SuppressLint("CheckResult")
    fun updateState() {
        //star状态
        RepoDataSource.checkRepoStarred(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                .subscribe {
                    updateStarView(it.isSuccessful)
                }

        //watch状态
        RepoDataSource.checkRepoWatched(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                .subscribe {
                    updateWatchView(it.isSuccessful)
                }

        if (context is RepositoryDetailsActivity) {
            val activity = context as RepositoryDetailsActivity
            business_mainpage_repo_details_bottom_tab_tv_branch.text = activity.getCurrentBranch()?.name
        }
    }

    @SuppressLint("CheckResult")
    private fun star(isStar: Boolean) {
        showLoading()
        if (isStar) {
            RepoDataSource.starRepo(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                    .subscribe({
                        updateStarView(it.isSuccessful)
                        if (!it.isSuccessful) {
                            ToastUtils.showShort(R.string.business_mainpage_operate_fail)
                        } else {
                            ToastUtils.showShort(R.string.business_mainpage_operate_success)
                        }
                        dismissLoading()
                    }, {
                        dismissLoading()
                        it.printStackTrace()
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    })
        } else {
            RepoDataSource.unstarRepo(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                    .subscribe({
                        updateStarView( !it.isSuccessful)
                        if (!it.isSuccessful) {
                            ToastUtils.showShort(R.string.business_mainpage_operate_fail)
                        } else {
                            ToastUtils.showShort(R.string.business_mainpage_operate_success)
                        }
                        dismissLoading()
                    }, {
                        dismissLoading()
                        it.printStackTrace()
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    })
        }

    }


    @SuppressLint("CheckResult")
    private fun watch(isWatch: Boolean) {
        showLoading()
        if (isWatch) {
            RepoDataSource.watchRepo(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                    .subscribe({
                        updateWatchView(it.isSuccessful)
                        if (!it.isSuccessful) {
                            ToastUtils.showShort(R.string.business_mainpage_operate_fail)
                        } else {
                            ToastUtils.showShort(R.string.business_mainpage_operate_success)
                        }
                        dismissLoading()
                    }, {
                        dismissLoading()
                        it.printStackTrace()
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    })
        } else {
            RepoDataSource.unwatchRepo(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                    .subscribe({
                        updateWatchView( !it.isSuccessful)
                        if (!it.isSuccessful) {
                            ToastUtils.showShort(R.string.business_mainpage_operate_fail)
                        } else {
                            ToastUtils.showShort(R.string.business_mainpage_operate_success)
                        }
                        dismissLoading()
                    }, {
                        dismissLoading()
                        it.printStackTrace()
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    })
        }
    }

    private fun updateStarView(isStar: Boolean) {
        if (isStar) {
            context?.resources?.getColor(R.color.primary_text)?.let {
                business_mainpage_repo_details_bottom_tab_iv_star.setColorFilter(it)
            }
            business_mainpage_repo_details_bottom_tab_tv_star.setText(R.string.business_mainpage_repo_do_unstar)
        } else {
            context?.resources?.getColor(R.color.white_50)?.let {
                business_mainpage_repo_details_bottom_tab_iv_star.setColorFilter(it)
            }
            business_mainpage_repo_details_bottom_tab_tv_star.setText(R.string.business_mainpage_repo_do_star)
        }
    }

    private fun updateWatchView(isWatch: Boolean) {
        if (isWatch) {
            context?.resources?.getColor(R.color.primary_text)?.let {
                business_mainpage_repo_details_bottom_tab_iv_watch.setColorFilter(it)
            }
            business_mainpage_repo_details_bottom_tab_tv_watch.setText(R.string.business_mainpage_repo_do_unwatch)

        } else {
            context?.resources?.getColor(R.color.white_50)?.let {
                business_mainpage_repo_details_bottom_tab_iv_watch.setColorFilter(it)
            }
            business_mainpage_repo_details_bottom_tab_tv_watch.setText(R.string.business_mainpage_repo_do_watch)
        }
    }

    private fun isStarViewInStar(): Boolean {
        return business_mainpage_repo_details_bottom_tab_tv_star.text != getString(R.string.business_mainpage_repo_do_star)
    }

    private fun isWatchViewInWatch(): Boolean {
        return business_mainpage_repo_details_bottom_tab_tv_watch.text != getString(R.string.business_mainpage_repo_do_watch)
    }

    private fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = BusinessProgressDialog.show(context)
        } else {
            mProgressDialog?.show()
        }
    }

    private fun dismissLoading() {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_mainpage_repo_details_bottom_tab_ll_star -> {
                star(!isStarViewInStar())
            }
            R.id.business_mainpage_repo_details_bottom_tab_ll_watch -> {
                watch(!isWatchViewInWatch())
            }
            R.id.business_mainpage_repo_details_bottom_tab_ll_branch -> {
                if (null == branchDialog) {
                    RepoDataSource.getBranches(mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "")
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(RxLifecycleUtils.bindToLifecycle(this@RepositoryDetailsBottomTabFragment, FragmentEvent.DESTROY))
                            .subscribe({
                                val strings = arrayOfNulls<String>(it.size)
                                for ((index, bean) in it.withIndex()) {
                                    strings[index] = bean.name
                                }
                                branchDialog = DialogUtils.getSheetDialog(context, strings) {
                                    parent, view, position, id ->
                                        if (context is RepositoryDetailsActivity) {
                                            val activity = context as RepositoryDetailsActivity
                                            activity.setCurrentBranch(it[position])
                                            business_mainpage_repo_details_bottom_tab_tv_branch.text = it[position].name
                                        }
                                }


                                branchDialog?.show()
                            }, {
                                it.printStackTrace()
                                ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                            })
                } else {
                    branchDialog?.show()
                }
            }
        }
    }

}