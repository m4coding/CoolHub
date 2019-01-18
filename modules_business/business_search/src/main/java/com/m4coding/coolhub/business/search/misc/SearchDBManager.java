package com.m4coding.coolhub.business.search.misc;

import com.m4coding.coolhub.base.base.BaseApplication;
import com.m4coding.coolhub.business.search.greendao.gen.DaoMaster;
import com.m4coding.coolhub.business.search.greendao.gen.DaoSession;
import com.m4coding.coolhub.business.search.greendao.gen.SearchHistoryBeanDao;
import com.m4coding.coolhub.business.search.model.bean.SearchHistoryBean;

import org.greenrobot.greendao.database.Database;

import java.util.List;


/**
 * @author mochangsheng
 * @description search 数据库管理器
 */

public class SearchDBManager {
    private final static String DB_NAME = "search_db";
    private static SearchDBManager sInstance;
    private DaoMaster.DevOpenHelper mOpenHelper;
    private DaoSession mDaoSession;

    private static final int SIZE = 25;

    private SearchDBManager() {
        mOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), DB_NAME, null);
        Database db = mOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public static SearchDBManager getInstance() {
        if (sInstance == null) {
            synchronized (SearchDBManager.class) {
                if (sInstance == null) {
                    sInstance = new SearchDBManager();
                }
            }
        }
        return sInstance;
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void insertSearchHistory(SearchHistoryBean searchHistoryBean) {

        if (null == searchHistoryBean) {
            return;
        }

        List<SearchHistoryBean> list = mDaoSession.getSearchHistoryBeanDao()
                .queryBuilder()
                .where(SearchHistoryBeanDao.Properties.Content.eq(searchHistoryBean.getContent()))
                .list();

        //没有才插入
        if (list == null || list.isEmpty()) {
            mDaoSession.insert(searchHistoryBean);
            //超过限制后，要清除
            if (mDaoSession.getSearchHistoryBeanDao().queryBuilder().count() > SIZE) {
                List<SearchHistoryBean> tempList = mDaoSession.getSearchHistoryBeanDao().queryBuilder().list();
                for (int i = 0; i < tempList.size(); i++) {
                    if (i < tempList.size() - SIZE) {
                        mDaoSession.getSearchHistoryBeanDao().delete(tempList.get(i));
                    }
                }
            }
        }
    }

    public void clearSearchHistory() {
        mDaoSession.deleteAll(SearchHistoryBean.class);
    }

    public List<SearchHistoryBean> getSearchHistory() {
        return mDaoSession.getSearchHistoryBeanDao().queryBuilder().limit(SIZE).build().list();
    }

    /**
     * 查找历史  （模糊匹配）
     * @param message
     * @return
     */
    public List<SearchHistoryBean> searchHistory(String message) {
        return mDaoSession.getSearchHistoryBeanDao().queryBuilder()
                .where(SearchHistoryBeanDao.Properties.Content.like("%" + message + "%")).list(); //模糊查找.list();
    }
}
