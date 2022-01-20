package com.hust.httpconnection;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hust.httpconnection.User.User;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TriedWithAsyncTask extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String apiUrl;
    Gson gson;
    List<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(null);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(TriedWithAsyncTask.this));

        apiUrl = "https://lebavui.github.io/jsons/users.json";
        gson = new Gson();
        userList = new ArrayList<>();

        userAdapter = new UserAdapter(userList, TriedWithAsyncTask.this);
        recyclerView.setAdapter(userAdapter);

        DataGetter dataGetter = new DataGetter();
        dataGetter.execute();

    }

    private class DataGetter extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(TriedWithAsyncTask.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder response = new StringBuilder();
            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(apiUrl);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();

                while (data != -1) {
                    response.append((char) data);
                    data = isr.read();
                }

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    userList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), User.class));
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressDialog.dismiss();

            userAdapter.notifyDataSetChanged();
        }
    }
}
