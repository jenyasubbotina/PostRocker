package com.jenyasubbotina.postrocker.network;

import com.jenyasubbotina.postrocker.pojo.ContestsPojo;
import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;
import com.jenyasubbotina.postrocker.pojo.TasksResponsePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("/api/tasks/?search={word}")
    Call<TasksResponsePojo> getTaskByKeyword(@Path("word") String keyWord);
}