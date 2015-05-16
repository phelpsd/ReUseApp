package com.example.tracy.reuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ItemActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button mainButton;
    String catName;
    String catId;
    TextView titleTextView;


    public static final String ENDPOINT = "http://reuse-group17.appspot.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        catName = "Items";

        if(getIntent().hasExtra("cat_name")){
            catName = this.getIntent().getExtras().getString("cat_name");
            catId = this.getIntent().getExtras().getString("cat_id");
        }

        titleTextView = (TextView) findViewById(R.id.item_title);

        titleTextView.setText(catName);


        getItemsByCat(catId, catName);

}

    void getItemsByCat(String catId, String catName){
        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI rAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object

        Category current_cat = new Category(catId, catName);

        //queries the API
        rAPI.getItemsByCategory(current_cat, new Callback<List<Item>>(){

            @Override
            public void success(List<Item> items, Response response) {
                populate_list(items);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();

            }
        });


        }

    void getItems(String catId){

        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI servAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object


        //queries the API
        servAPI.getItems(new Callback<List<Item>>() {



            @Override
            public void success(List<Item> items, Response response) {
                populate_list(items);
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();

            }
        });



    }

    public void populate_list(List<Item> items){
        ListView listView = (ListView) findViewById(R.id.listView);
        ItemAdapter servAdapt;

        servAdapt = new ItemAdapter(this, 0, (ArrayList)items);

        listView.setAdapter(servAdapt);

        listView.setOnItemClickListener((android.widget.AdapterView.OnItemClickListener) this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getAdapter().getItem(position);

        item.getId();
        // change this to be (this, BusinessActivity.class) once BusinessActivity exists
        Intent busIntent = new Intent(this, BusinessActivity.class);

        busIntent.putExtra("item_id", item.getId());
        busIntent.putExtra("item_name", item.getName());
        busIntent.putExtra("cat_name", catName);
        busIntent.putExtra("cat_id", catId);


        startActivity(busIntent);

    }
}

