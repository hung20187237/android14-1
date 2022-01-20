package com.hust.httpconnection;

import com.hust.httpconnection.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("/jsons/users.json")
    Call<List<User>> getUsers();

}
