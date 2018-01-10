package com.mattsmith.demigodstocklevel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mattsmith.demigodstocklevel.databinding.ProductListItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScrollingActivity extends Activity implements ProductListItem_Delegate {

    ProductListAdapter _customAdapter;
    public List<ProductListItem> _productList;
    public List<ProductListItem> _fullProductList;
    Context _context;
    ProductListItemBinding _productItemBinding;

    FloatingActionButton _button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling);

        //Declare product item binding
       // _productItemBinding = DataBindingUtil.setContentView(this, R.layout.product_list_item);
        _productItemBinding =  DataBindingUtil.inflate(getLayoutInflater(), R.layout.product_list_item,
                (ViewGroup)findViewById(R.id.Item_Products), false);


        //User user = new User("Test", "User");
        //binding.setUser(user);


        _context = getWindow().getContext();
        // Defined Array values to show in ListView

        _productList = new ArrayList<ProductListItem>();

        _fullProductList= new ArrayList<ProductListItem>();

        //Have we managed to load anything?
        List<String> loadedData = loadPreferences();

        if (!loadedData.contains("Scarab"))
            addItem("Scarab", "Product", 0, R.drawable.scarabbackground, "BUGS");
        if (!loadedData.contains("Skeleton cat"))
            addItem("Skeleton cat", "Product", 0, R.drawable.cat, "CLEAR ACRYLIC");
        if (!loadedData.contains("Skeleton Dog"))
            addItem("Skeleton Dog", "Product", 0, R.drawable.dog, "CLEAR ACRYLIC");
        if (!loadedData.contains("Axolotl")) addItem("Axolotl", "Product", 0, R.drawable.axolotl, "UNDERWATER");
        if (!loadedData.contains("Rainbow Succulent Cluster Floral"))
            addItem("Rainbow Succulent Cluster Floral", "Product", 0, R.drawable.rainbowsucculent, "FLORAL");
        if (!loadedData.contains("Teal Octopus Kraken"))
            addItem("Teal Octopus Kraken", "Product", 0, R.drawable.tealoctopuskraken, "UNDERWATER");
        if (!loadedData.contains("Woodland Fox"))
            addItem("Woodland Fox", "Product", 0, R.drawable.fox, "WOODLAND");
        if (!loadedData.contains("Bunny")) addItem("Bunny", "Product", 0, R.drawable.bunny, "WOODLAND");
        if (!loadedData.contains("Fish")) addItem("Fish", "Product", 0, R.drawable.fish, "UNDERWATER");
        if (!loadedData.contains("Wolf")) addItem("Wolf", "Product", 0, R.drawable.wolf, "WOODLAND");
        if (!loadedData.contains("Bird")) addItem("Bird", "Product", 0, R.drawable.birds, "WOODLAND");


        // Get ListView object from xml
        final ListView list = (ListView) findViewById(R.id.Item_Products);

        // get data from the table by the ListAdapter
        _customAdapter =
                new ProductListAdapter(this, R.layout.product_list_item, _productList);

        _customAdapter.setDelegate(this);

        // Assign adapter to ListView
        list.setAdapter(_customAdapter);

        AppBarLayout appBarView = (AppBarLayout) findViewById(R.id.app_bar);
        appBarView.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("Matt", "verticalOffset: " + verticalOffset +
                        ":(" + (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange()) + ")");

                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    Log.d("Matt", "Collapsed");
                    //  Collapsed

                    LinearLayout layout = (LinearLayout) findViewById(R.id.Items_Layout);
                    if (layout != null) {
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
                        //lp.topMargin = (lp.topMargin - verticalOffset);
                        if (lp.topMargin >= appBarLayout.getTotalScrollRange()) {
                            lp.topMargin = (lp.topMargin - appBarLayout.getTotalScrollRange());
                            layout.setLayoutParams(lp);
                        }
                    }
                } else if (verticalOffset == 0) {
                    Log.d("Matt", "Expanded");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.Items_Layout);
                    if (layout != null) {
                        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
                        //
                        // lp.topMargin = (lp.topMargin + verticalOffset);
                        if (lp.topMargin < appBarLayout.getTotalScrollRange()) {
                            lp.topMargin = (lp.topMargin + appBarLayout.getTotalScrollRange());
                            layout.setLayoutParams(lp);
                        }
                    }

                }
            }
        });

        _button1 = (FloatingActionButton) findViewById(R.id.fab);
        _button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(_context, _button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(_context,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                        List<ProductListItem> tmpList = new ArrayList<ProductListItem>();;
                        //Hide all category
                        for (ProductListItem product : _fullProductList) {
                            if (item.getTitle().toString().equalsIgnoreCase("ALL")) {
                                tmpList.add(product);
                                product.setvisable(true);
                            }
                            else if (product.getCategory().equalsIgnoreCase(item.getTitle().toString()))
                            {
                                tmpList.add(product);
                                product.setvisable(true);
                               // _customAdapter.remove(product);
                            }
                            else {
                                product.setvisable(false);
                              //  _customAdapter.add(product);
                            }
                        }

                        Log.d("Matt", "Log: '_fullProductList' - " + Integer.toString(_fullProductList.size()));
                        Log.d("Matt", "Log: '_productList' - ' - " + Integer.toString(_productList.size()));
                        Log.d("Matt", "Log: 'tmpList' - ' - " + Integer.toString(tmpList.size()));

                        _customAdapter.clear();
                        _customAdapter.addAll(tmpList);// put update ArrayList Value addAll() method

                        //_customAdapter.addAll(_productList);
                       _customAdapter.notifyDataSetChanged();

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
    }

    public void addItem(String name, String productType, int stockLevel, int resourceID, String cat) {
        ProductListItem item = new ProductListItem(name, productType, stockLevel, resourceID, cat, true);
        _productItemBinding.setProductItem(item);

        _productList.add(item);

        _fullProductList.add(item);
    }

    public void updatePreference(ProductListItem savingItem) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        Gson gson = new Gson();

        String obj = gson.toJson(savingItem);

        ed.putString(savingItem.getName(), obj);
        Log.d("Saving values ...", savingItem.getName() + ": " + obj.toString());
        ed.commit();
    }

    public List<String> loadPreferences() {

        List<String> list = new ArrayList<String>();

        try {
            Gson gson = new Gson();
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            JsonParser parser = new JsonParser();

            Map<String, ?> keys = sharedPref.getAll();

            //Return false if empty
            if (keys.isEmpty()) return list;

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("Loading values ...", entry.getKey() + ": " +
                        entry.getValue().toString());
                list.add(entry.getKey().toString());
                _productList.add(gson.fromJson(entry.getValue().toString(), ProductListItem.class));
                _fullProductList.add(gson.fromJson(entry.getValue().toString(), ProductListItem.class));
            }
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return list;
        }

        return list;
    }

    @Override
    public void onClickDecrease(String name) {

        for (ProductListItem product : _productList) {
            if (product.getName().equals(name)) {
                product.decreaseItemCount();
                updatePreference(product);
            }
        }
    }

    @Override
    public void onClickIncrement(String name) {

        for (ProductListItem product : _productList) {
            if (product.getName().equals(name)) {
                product.incrementItemCount();
                updatePreference(product);
            }
        }
    }

    @Override
    public void onCountManuallyUpdated(String name, String newCount) {

        for (ProductListItem product : _productList) {
            if (product.getName().equals(name)) {
                product.setCount(Integer.parseInt(newCount));
                updatePreference(product);
            }
        }
    }
}