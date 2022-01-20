package com.hust.httpconnection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hust.httpconnection.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lebavui.github.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<User>> call = retrofitAPI.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                userList = response.body();
                userAdapter = new UserAdapter(userList, MainActivity.this);
                recyclerView.setAdapter(userAdapter);
                userAdapter.setListener(user -> {
                    Intent intent = new Intent(MainActivity.this, UserDetail.class);
                    intent.putExtra("name", user.getName());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("website", user.getWebsite());
                    intent.putExtra("phone", user.getPhone());
                    intent.putExtra("address",
                            user.getCompany().getName() + ", "
                                    + user.getAddress().getSuite() + ", "
                                    + user.getAddress().getStreet() + ", "
                                    + user.getAddress().getCity());

                    intent.putExtra("background", user.getAvatar().getThumbnail());
                    intent.putExtra("avatar", user.getAvatar().getPhoto());
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }
}