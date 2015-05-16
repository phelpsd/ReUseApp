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
public class CategoryAdapter extends ArrayAdapter<Category> {
    private Activity activity;
    private ArrayList<Category> list_category;
    private static LayoutInflater inflater = null;

    //creates list adapter

    public CategoryAdapter(Activity activity, int textViewResourceid, ArrayList<Category> _list_category) {
        super(activity, textViewResourceid, _list_category);
        try {
            this.activity = activity;
            this.list_category = _list_category;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {

        }
    }
    // get size of list
    public int getCount()  {
        return list_category.size();
    }
    //get item at index
    public Category getItem(Category position) {
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
                vi = inflater.inflate(R.layout.row_category, null);
                holder = new ViewHolder();

                holder.nameTextView = (TextView) vi.findViewById(R.id.text_name);
                vi.setTag(holder);
            }else{
                holder = (ViewHolder) vi.getTag();
            }

            holder.nameTextView.setText(list_category.get(position).getName());
        } catch(Exception e){

        }
       return vi;
    }
}
