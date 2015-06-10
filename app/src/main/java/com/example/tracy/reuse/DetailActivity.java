package com.example.tracy.reuse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
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
    String new_phone;

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
        //itemsView = (TextView) findViewById(R.id.items);


        if (getIntent().hasExtra("bus_name")) {
            busId = this.getIntent().getExtras().getString("bus_id");
            busName = this.getIntent().getExtras().getString("bus_name");
            busWeb = this.getIntent().getExtras().getString("bus_website");
            busPhone = this.getIntent().getExtras().getString("bus_phone");
            busAdd = this.getIntent().getExtras().getString("bus_address");

            if (busPhone.length() == 10) {
                new_phone = "(" + busPhone.substring(0,3) + ")" + busPhone.substring(3,6) + "-" + busPhone.substring(6);
            }
            else {
                new_phone = busPhone;
            }

            nameView.setText(busName);
            webView.setPaintFlags(webView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            webView.setText(busWeb);
            phoneView.setPaintFlags(phoneView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            phoneView.setText(new_phone);
            addressView.setPaintFlags(addressView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        // listView.setOnItemClickListener((android.widget.AdapterView.OnItemClickListener) this);

    }

    //launches browser to business website
    public void onClick_web(View v) {
        if (busWeb.equals("n/a")) {
            Toast.makeText(this, "No website currently available", Toast.LENGTH_LONG).show();
        }
        else {
            if (!busWeb.startsWith("http://") && !busWeb.startsWith("https://"))
                busWeb = "http://" + busWeb;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(busWeb));
            try {
                startActivity(browserIntent);
            } catch (Exception e) {
                Intent unrestrictedwebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(busWeb));
                startActivity(unrestrictedwebIntent);
            }
        }
    }
    //launches phone dialer with business phone
    public void onClick_phone(View v) {
        if (busPhone.length() == 10) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + busPhone));
            try {
                startActivity(callIntent);
            } catch (Exception e) {
                Intent unrestrictedcallIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + busPhone));
                startActivity(unrestrictedcallIntent);
            }
        }
        else {
            Toast.makeText(this, "No phone number currently available", Toast.LENGTH_LONG).show();
        }
    }
    //launches map with business address
    public void onClick_map(View v) {
        if (busAdd.equals("n/a")) {
            Toast.makeText(this, "No address currently available", Toast.LENGTH_LONG).show();

        }
        else {
            String modAdd = busAdd.replace(' ', '+');
            LatLng p1 = getLocationFromAddress(modAdd);
            double lat = p1.latitude;
            double lng = p1.longitude;
            String uri = String.format(Locale.ENGLISH, "https://www.google.com/maps/place/%s/@%f,%f,13z", modAdd, p1.latitude, p1.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                try {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(unrestrictedIntent);
                } catch (ActivityNotFoundException innerEx) {
                    Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng((int) (location.getLatitude() * 1E6),
                    (int) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}