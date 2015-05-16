package com.example.tracy.reuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CategoryActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    public static final String ENDPOINT = "http://reuse-group17.appspot.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCategories();
    }

    void getCategories(){

        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI servAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object


        //queries the API
        servAPI.getCategories(new Callback<List<Category>>(){


            @Override
            public void success(List<Category> categories, Response response) {

                populate_list(categories);

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();

            }
        });



}

    public void populate_list(List<Category> categories){
        ListView listView = (ListView) findViewById(R.id.listView);
        CategoryAdapter servAdapt;

        servAdapt = new CategoryAdapter(this, 0, (ArrayList)categories);

        listView.setAdapter(servAdapt);

        listView.setOnItemClickListener((android.widget.AdapterView.OnItemClickListener) this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Category category = (Category) parent.getAdapter().getItem(position);

        category.getId();

        Intent itemIntent = new Intent(this, ItemActivity.class);

        itemIntent.putExtra("cat_id", category.getId());
        itemIntent.putExtra("cat_name", category.getName());

        startActivity(itemIntent);

    }
}
