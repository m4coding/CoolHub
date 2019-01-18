package com.m4coding.coolhub.business.login.ui.activity

import android.content.ComponentName
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.billy.cc.core.component.CC
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.api.exception.ApiException
import com.m4coding.coolhub.base.utils.DeviceUtils
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.activity.BaseToolbarActivity
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.component.ComponentUtils
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.base.widgets.BusinessProgressDialog
import com.m4coding.coolhub.business.login.R
import com.m4coding.coolhub.business.login.contract.LoginContract
import com.m4coding.coolhub.business.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.business_login_activity_login.*

/**
 * @author mochangsheng
 * @description
 */
class LoginActivity : BaseToolbarActivity<LoginPresenter>(), LoginContract.View, View.OnClickListener{

    private var mProgressDialog: BusinessProgressDialog? = null


    companion object {
        fun newInstance(context: Context) {
            val intent = ComponentUtils.getIntent(context)
            intent.component = ComponentName(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        setContentView(R.layout.business_login_activity_login, getString(R.string.business_login_title))

        business_login_iv_eye.setOnClickListener(this)
        business_login_bt_login.setOnClickListener(this)
        business_login_et_username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                business_login_bt_login.isEnabled = isCanLoginEnable()
            }
        })

        business_login_et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                business_login_bt_login.isEnabled = isCanLoginEnable()
            }

        })

        business_login_et_username.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                business_login_et_username.removeOnLayoutChangeListener(this)
                business_login_et_username.postDelayed(
                        { DeviceUtils.showSoftKeyboard(this@LoginActivity, business_login_et_username) }
                        , 500)
            }
        })
    }

    override fun initData() {
        mPresenter = LoginPresenter(this)
    }

    override fun startLogin() {
        if (mProgressDialog == null) {
            mProgressDialog = BusinessProgressDialog.show(this)
        } else {
            mProgressDialog?.show()
        }
    }

    override fun onLoginSuccess(authUser: AuthUser) {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }

        //跳转首页
        val cc = CC.obtainBuilder(ComponentConstants.HOMEPAGE)
                .setActionName(ComponentConstants.HOMEPAGE_ACTION_SHOW)
                .build()
        PrintComponentMsgUtils.showResult(cc, cc.call())
        finish()
    }

    override fun onLoginError(apiException: ApiException?) {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
        showMessage(apiException?.displayMessage)
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_login_bt_login -> {
                if (business_login_bt_login.isEnabled) {
                    DeviceUtils.hideSoftKeyboard(this@LoginActivity, business_login_et_password)
                    mPresenter?.login(business_login_et_username.text?.toString(), business_login_et_password.text?.toString())
                }
            }
            R.id.business_login_iv_eye -> {
                if (business_login_et_password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                    //设置为明文显示
                    business_login_et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    business_login_iv_eye.setImageResource(R.drawable.vc_ic_eye_open)
                } else {
                    //设置为密文显示
                    business_login_et_password.transformationMethod = PasswordTransformationMethod.getInstance()
                    business_login_iv_eye.setImageResource(R.drawable.vc_ic_eye_close)
                }
                //光标在最后显示
                business_login_et_password.setSelection(business_login_et_password.length())
            }
        }
    }

    private fun isCanLoginEnable(): Boolean {
        return !TextUtils.isEmpty(business_login_et_username.text?.toString()?.trim())
                && !TextUtils.isEmpty(business_login_et_password.text?.toString()?.trim())
    }
}