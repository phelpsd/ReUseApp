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


public class BusinessActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button mainButton;
    String itemName;
    String itemId;
    TextView titleTextView;


    public static final String ENDPOINT = "http://reuse-group17.appspot.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemName = "Businesses";

        if(getIntent().hasExtra("item_name")){
            itemName = this.getIntent().getExtras().getString("item_name");
            itemId = this.getIntent().getExtras().getString("item_id");
        }


        titleTextView = (TextView) findViewById(R.id.item_title);

        titleTextView.setText(itemName);


        getBussesByCat(itemId, itemName);

    }

    void getBussesByCat(String itemId, String itemName){
        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI rAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object

        Item current_item = new Item(itemId, itemName);


        rAPI.getBusinessesByItem(current_item, new Callback<List<Business>>() {
            @Override
            public void success(List<Business> businesses, Response response) {
                populate_list(businesses);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });

    }

    void getBusinesses(String catId){

        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI servAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object


        //queries the API
        servAPI.getBusinesses(new Callback<List<Business>>() {
            public void success(List<Business> businesses, Response response) {
                populate_list(businesses);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }

       });


        }

    public void populate_list(List<Business> businesses){
        ListView listView = (ListView) findViewById(R.id.listView);

        BusinessAdapter servAdapt;

        servAdapt = new BusinessAdapter(this, 0, (ArrayList)businesses);

        listView.setAdapter(servAdapt);

        listView.setOnItemClickListener((android.widget.AdapterView.OnItemClickListener) this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Business bus = (Business) parent.getAdapter().getItem(position);

        bus.getId();
        // change this to be (this, BusinessActivity.class) once BusinessActivity exists
        Intent busIntent = new Intent(this, DetailActivity.class);

        busIntent.putExtra("bus_id", bus.getId());
        busIntent.putExtra("bus_name", bus.getName());
        busIntent.putExtra("bus_phone", bus.getPhone());
        busIntent.putExtra("bus_address", bus.getAddress());
        busIntent.putExtra("bus_website", bus.getWebsite());


        startActivity(busIntent);

    }
}

