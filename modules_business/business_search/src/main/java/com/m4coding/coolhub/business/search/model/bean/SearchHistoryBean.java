package com.m4coding.coolhub.business.search.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 由于greenDao不支持kotlin，所以bean的定义要用java
 */
@Entity
public class SearchHistoryBean {

    @Id //主键 （必须要有的，删除操作会需要，或其他操作）
    private Long id;

    //搜索历史
    private String content = null;

    @Generated(hash = 818543636)
    public SearchHistoryBean(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 1570282321)
    public SearchHistoryBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
