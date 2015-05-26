package com.example.tracy.reuse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tracy on 5/16/2015.
 */




public class DetailActivity extends ActionBarActivity {

    public static final String ENDPOINT = "http://reuse-group17.appspot.com/";

    String busName;
    String busId;
    String busWeb;
    String busPhone;
    String busAdd;

    TextView nameView;
    TextView webView;
    TextView phoneView;
    TextView addressView;
    TextView itemsView;
    Business bus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameView = (TextView) findViewById(R.id.bus_name);
        webView = (TextView) findViewById(R.id.website);
        phoneView = (TextView) findViewById(R.id.phone);
        addressView = (TextView) findViewById(R.id.address);
        itemsView = (TextView) findViewById(R.id.items);


        if (getIntent().hasExtra("bus_name")) {
            busId = this.getIntent().getExtras().getString("bus_id");
            busName = this.getIntent().getExtras().getString("bus_name");
            busWeb = this.getIntent().getExtras().getString("bus_website");
            busPhone = this.getIntent().getExtras().getString("bus_phone");
            busAdd = this.getIntent().getExtras().getString("bus_address");

            nameView.setText(busName);
            webView.setText(busWeb);
            phoneView.setText(busPhone);
            addressView.setText(busAdd);

            bus = new Business(busId, busName, busPhone, busWeb, busAdd);

            getItemsByBus(bus);

        }


    }

    void getItemsByBus(Business bus){
        //builds a rest adapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();

        ReUseAPI rAPI = restAdapter.create(ReUseAPI.class);
        //creates a user object

        //Category current_cat = new Category(catId, catName);

        //queries the API
        rAPI.getItemsByBusiness(bus, new Callback<List<Item>>(){

            @Override
            public void success(List<Item> items, Response response) {
                itemsView = (TextView) findViewById(R.id.items);

                itemsView.setText("Items Accepted");
                for(int i = 0; i < items.size(); i++)
                {
                    itemsView.append("\n");
                    itemsView.append(items.get(i).getName());
                }


            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();

            }
        });

    }
    public void onClick_map(View v){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", 12f, 2f, busAdd);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

}