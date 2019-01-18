package com.m4coding.coolhub.api.datasource.bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 * @author mochangsheng
 * @description 授权认证bean
 */
@Entity
class AuthorizationBean {
    /**
     * {
            "id": 225766920,
            "url": "https://api.github.com/authorizations/225766920",
            "app": {
            "name": "test",
            "url": "http://xxxx",
            "client_id": "bff1f902f7ecaafd19fb"
            },
            "token": "80f62d78ced6c9fa3bc84d68edb775796f7e9e0d",
            "hashed_token": "f082bfe450ffdc13775502d8f63bddb165162248be3cb6660516740f0a991c18",
            "token_last_eight": "6f7e9e0d",
            "note": "Demo",
            "note_url": null,
            "created_at": "2018-10-08T06:12:40Z",
            "updated_at": "2018-10-08T06:12:40Z",
            "scopes": [
            "gist"
            ],
            "fingerprint": null
        }
     */

    @Id
    var id: String? = null
    var url: String? = null
    var app: AppBean? = null
    var token: String? = null
    var hashed_token: String? = null
    var token_last_eight: String? = null
    var note: String? = null
    var note_url: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var scopes: List<String>? = null
    var fingerprint: String? = null


    class AppBean {
        var name: String? = null
        var url: String? = null
        var client_id: String? = null
    }

    override fun toString(): String {
        return "AuthorizationBean(\n" +
                "id=$id, url=$url, \n" +
                "app=$app, \n" +
                "token=$token, \n" +
                "hashed_token=$hashed_token, \n" +
                "token_last_eight=$token_last_eight, \n" +
                "note=$note, note_url=$note_url, \n" +
                "created_at=$created_at, \n" +
                "updated_at=$updated_at, \n" +
                "scopes=$scopes, \n" +
                "fingerprint=$fingerprint)"
    }


}