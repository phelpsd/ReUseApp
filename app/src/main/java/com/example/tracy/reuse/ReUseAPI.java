package com.example.tracy.reuse;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Tracy on 4/28/2015.
 * 	(r'/category', 'category.Category'),
 (r'/item', 'item.Item'),
 (r'/business', 'business.Business'),
 (r'/', 'front.Front'),
 */
public interface ReUseAPI {


    @GET("/category")
    public void getCategories(Callback<List<Category>> categories_list); //callback class member of retrofit library

    @GET("/item")
    public void getItems(Callback<List<Item>> items_list); //callback class member of retrofit library

    @GET("/business")
    public void getBusinesses(Callback<List<Business>> business_list); //callback class member of retrofit library

    @POST("/item_by_cat")
    public void getItemsByCategory(@Body Category curr_cat, Callback<List<Item>> category_items);

    @POST("/bus_by_item")
    public void getBusinessesByItem(@Body Item curr_item, Callback<List<Business>> item_businesses);

    @POST("/item_by_bus")
    public void getItemsByBusiness(@Body Business curr_bus, Callback<List<Item>> business_items);

}

