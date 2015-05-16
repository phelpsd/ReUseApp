package com.example.tracy.reuse;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tracy on 3/9/2015.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private Activity activity;
    private ArrayList<Item> list_item;
    private static LayoutInflater inflater = null;

    //creates list adapter

    public ItemAdapter(Activity activity, int textViewResourceid, ArrayList<Item> _list_item) {
        super(activity, textViewResourceid, _list_item);
        try {
            this.activity = activity;
            this.list_item = _list_item;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }
    // get size of list
    public int getCount()  {
        return list_item.size();
    }
    //get item at index
    public Item getItem(Item position) {
        return position;
    }
    //get item id at index
    public long getItemId(int position) {
        return position;
    }
    //defines the view for each object
    public static class ViewHolder {
        public TextView nameTextView;
        public TextView descTextView;
    }


    //creates view for each list item
    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;

        try{
            if(convertView == null){
                vi = inflater.inflate(R.layout.row_item, null);
                holder = new ViewHolder();

                holder.nameTextView = (TextView) vi.findViewById(R.id.text_name);
                vi.setTag(holder);
            }else{
                holder = (ViewHolder) vi.getTag();
            }

            holder.nameTextView.setText(list_item.get(position).getName());
        } catch(Exception e){

        }
        return vi;
    }
}
