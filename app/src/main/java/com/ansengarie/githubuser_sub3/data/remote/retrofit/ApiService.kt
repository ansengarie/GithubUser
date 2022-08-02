package com.ansengarie.githubuser_sub3.data.remote.retrofit

import com.ansengarie.githubuser_sub3.data.remote.response.FollowResponseItem
import com.ansengarie.githubuser_sub3.data.remote.response.GithubUser
import com.ansengarie.githubuser_sub3.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") query: String?
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String?
    ): Call<GithubUser>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String?
    ): Call<ArrayList<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String?
    ): Call<ArrayList<FollowResponseItem>>
}