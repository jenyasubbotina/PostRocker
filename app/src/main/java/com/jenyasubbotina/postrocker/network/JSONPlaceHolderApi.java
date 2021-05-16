package com.jenyasubbotina.postrocker.network;

import com.jenyasubbotina.postrocker.pojo.AllUsersResponsePojo;
import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TasksPojo;
import com.jenyasubbotina.postrocker.pojo.TasksResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthBodyPojo;
import com.jenyasubbotina.postrocker.pojo.TokenAuthResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TokenRefreshBodyPojo;
import com.jenyasubbotina.postrocker.pojo.UserPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi
{
    @GET("/api/contests")
    Call<ContestsResponsePojo> getAllContests();

    @GET("/api/contests/{id}/tasks")
    Call<TasksResponsePojo> getTasksByContest(@Path("id") long id);

    @GET("/api/contests/{id}")
    Call<ContestsPojo> getContestById(@Path("id") long id);

    @GET("/api/tasks")
    Call<TasksResponsePojo> getAllTasks();

    @GET("/api/tasks/?")
    Call<TasksResponsePojo> getTaskByKeyword(@Query("search") String word);

    @GET("/api/tasks/{id}")
    Call<TasksPojo> getSingleTask(@Path("id") long id);

    @POST("/api/token-auth")
    Call<TokenAuthResponsePojo> getTokenAuth(@Body TokenAuthBodyPojo body);

    @POST("/api/token-refresh")
    Call<TokenAuthResponsePojo> refreshTokenAuth(@Body TokenRefreshBodyPojo body);

    @GET("/api/users/{id}")
    Call<UserPojo> getUserInfo(@Path("id") long id);

    @GET("/api/users")
    Call<AllUsersResponsePojo> getAllUsers();

    @GET("/api/users/?")
    Call<AllUsersResponsePojo> getUserByUsername(@Query("search") String username);
}
