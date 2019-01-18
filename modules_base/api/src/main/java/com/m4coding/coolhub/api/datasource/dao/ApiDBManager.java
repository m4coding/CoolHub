package com.m4coding.coolhub.api.datasource.dao;

import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser;
import com.m4coding.coolhub.api.greendao.gen.DaoMaster;
import com.m4coding.coolhub.api.greendao.gen.DaoSession;
import com.m4coding.coolhub.base.base.BaseApplication;

import org.greenrobot.greendao.database.Database;


/**
 * @author mochangsheng
 * @description api 数据库管理器
 */

public class ApiDBManager {
    private final static String DB_NAME = "api_db";
    private static ApiDBManager sInstance;
    private DaoMaster.DevOpenHelper mOpenHelper;
    private DaoSession mDaoSession;

    public ApiDBManager() {
        mOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), DB_NAME, null);
        Database db = mOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static ApiDBManager getInstance() {
        if (sInstance == null) {
            synchronized (ApiDBManager.class) {
                if (sInstance == null) {
                    sInstance = new ApiDBManager();
                }
            }
        }
        return sInstance;
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void insertAuthUser(AuthUser authUser) {

        if (null == authUser) {
            return;
        }
        mDaoSession.deleteAll(AuthUser.class);
        mDaoSession.insertOrReplace(authUser);
    }

    public void deleteAuthUser() {
        mDaoSession.deleteAll(AuthUser.class);
    }

    public AuthUser getAuthUser() {
        return mDaoSession.getAuthUserDao().queryBuilder().limit(1).build().unique();
    }
}
