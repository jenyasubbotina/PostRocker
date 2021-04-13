package com.jenyasubbotina.postrocker.network;

import com.jenyasubbotina.postrocker.pojo.ContestsResponsePojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi
{
    @GET("/api/contests")
    public Call<ContestsResponsePojo> getAllContests();
}