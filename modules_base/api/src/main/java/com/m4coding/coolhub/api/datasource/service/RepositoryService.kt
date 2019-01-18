package com.m4coding.coolhub.api.datasource.service

import com.m4coding.coolhub.api.datasource.bean.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
interface RepositoryService {

    /**
     * 获取个人的仓库信息
     */
    @GET("user/repos")
    fun getMineRepo(@Query("page") page: Int): Observable<List<RepoBean>>

    /**
     * 获取用户的仓库信息 (只能获取pulic的仓库)
     */
    @GET("users/{user}/repos")
    fun getUserRepo(@Path("user") user:String, @Query("page") page: Int): Observable<List<RepoBean>>

    /**
     * 获取特定的仓库信息
     */
    @GET("repos/{owner}/{repo}")
    fun getRepoInfo(@Path("owner") user: String, @Path("repo") repo: String): Observable<RepoBean>

    /**
     * 获取获取仓库的Readme
     * @wiki https://developer.github.com/v3/repos/contents/
     */
    @GET("repos/{owner}/{repo}/readme")
    fun getRepoReadme(@Path("owner") user: String, @Path("repo") repo: String,
                      @Query("ref") branch: String): Observable<FileBean>

    /**
     * 获取文件，以Html方式返回
     */
    @GET
    @Headers("Accept: application/vnd.github.html")
    fun getFileAsHtmlStream(@Url url: String): Observable<Response<ResponseBody>>


    /**
     * 获取文件对应的Blob
     */
    @GET("repos/{owner}/{repo}/git/blobs/{file_sha}")
    fun getBlob(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path("file_sha") fileSha: String): Observable<BlobBean>

    /**
     * 获取文件
     */
    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepoFiles(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Path(value = "path", encoded = true) path: String,
            @Query("ref") branch: String
    ): Observable<List<FileBean>>


    /**
     * 获取对仓库的starers
     */
    @GET("repos/{username}/{repo}/stargazers")
    fun getStargazers(@Path("username") username: String,
                      @Path("repo") repo: String,
                      @Query("page") page: Int): Observable<List<UserBean>>

    /**
     * 获取对仓库的watcher
     */
    @GET("repos/{username}/{repo}/subscribers")
    fun getWatchers(@Path("username") username: String,
                    @Path("repo") repo: String,
                    @Query("page") page: Int): Observable<List<UserBean>>

    /**
     * 获取对仓库的forks信息
     */
    @GET("repos/{username}/{repo}/forks")
    fun getForks(
            @Path("username") username: String,
            @Path("repo") repo: String,
            @Query("page") page: Int): Observable<List<RepoBean>>


    /**
     * 获取仓库对应的Issue
     */
    @GET("repos/{username}/{repo}/issues")
    fun getRepoIssues(
            @Path("username") username: String,
            @Path("repo") repo: String,
            @Query("state") state: String,
            @Query("sort") sort: String,
            @Query("direction") direction: String,
            @Query("page") page: Int): Observable<List<IssueBean>>


    /**
     * 获取某个issue的timeline
     */
    @GET("repos/{username}/{repo}/issues/{issueNumber}/timeline")
    @Headers("Accept: application/vnd.github.mockingbird-preview,application/vnd.github.html,"
            + " application/vnd.github.VERSION.raw,application/vnd.github.squirrel-girl-preview")
    fun getIssueTimeline(
            @Path("username") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int,
            @Query("page") page: Int): Observable<List<IssueEventBean>>


    /**
     * 获取某个issue
     */
    @GET("repos/{username}/{repo}/issues/{issueNumber}")
    fun getIssueByNumber(
            @Path("username") owner: String,
            @Path("repo") repo: String,
            @Path("issueNumber") issueNumber: Int): Observable<IssueBean>


    /**
     * 检查是否已对仓库星标
     */
    @GET("user/starred/{owner}/{repo}")
    fun checkRepoStarred(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * 星标仓库
     */
    @PUT("user/starred/{owner}/{repo}")
    fun starRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * 取消星标仓库
     */
    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>


    @GET("user/subscriptions/{owner}/{repo}")
    fun checkRepoWatched(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>

    @PUT("user/subscriptions/{owner}/{repo}")
    fun watchRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>

    @DELETE("user/subscriptions/{owner}/{repo}")
    fun unwatchRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ResponseBody>>


    @GET("repos/{owner}/{repo}/branches")
    fun getBranches(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<List<BranchBean>>

    /*@GET("repos/{owner}/{repo}/tags")
    abstract fun getTags(
            @Path("owner") owner: String,
            @Path("repo") repo: String): Observable<Response<ArrayList<BranchBean>>>*/
}