package com.m4coding.coolhub.business.mainpage.modules.details.contract

import com.m4coding.coolhub.api.datasource.bean.BlobBean
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.mvp.IView

class CodeReaderContract {

    interface View : IView {
        fun onGetBlobSuccess(blobBean: BlobBean)
        fun onGetMdSuccess(content: String)
        fun onGetBlobFail()
        fun onGetBlobBegin()
    }

    interface Model : IModel
}