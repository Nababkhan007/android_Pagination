package com.khan.pagination.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.khan.pagination.R;
import com.khan.pagination.adapter.UserAdapter;
import com.khan.pagination.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<UserModel> userList;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getDataFromAPI(page);

        nestedScrollChangeListener();
    }

    private void nestedScrollChangeListener() {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() -
                            v.getMeasuredHeight()) {
                        page++;
                        progressBar.setVisibility(View.VISIBLE);
                        getDataFromAPI(page);
                    }
                });
    }

    private void getDataFromAPI(int page) {
        String url = "https://reqres.in/api/users?page=" + page;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> {
            try {
                int totalPage = response.getInt("total_pages");
                if (page > totalPage) {
                    Toast.makeText(this, "That's all the data...", Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                JSONArray dataArray = response.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    String firstName = jsonObject.getString("first_name");
                    String lastName = jsonObject.getString("last_name");
                    String email = jsonObject.getString("email");
                    String imageUrl = jsonObject.getString("avatar");
                    userList.add(new UserModel(firstName, lastName, email, imageUrl));

                    userAdapter = new UserAdapter(userList, this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(userAdapter);
                }

            } catch (JSONException e) {
                progressBar.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Fail to get data...",
                    Toast.LENGTH_SHORT).show();
        });

        queue.add(jsonObjectRequest);
    }

    private void init() {
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.nested_scroll_view);
    }
}
