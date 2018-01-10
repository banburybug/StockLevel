package com.mattsmith.demigodstocklevel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ProductListItem {

    private String _name;
    private String _type;
    private int _itemCount;
    private int _resource;
    private String _categoryString;
    private boolean _isVisable;

    public ProductListItem(String _name, String _type, int _itemCount, int _resource, String _categoryString, boolean _isVisable) {
        this._name = _name;
        this._type = _type;
        this._itemCount = _itemCount;
        this._resource = _resource;
        this._categoryString = _categoryString;
        this._isVisable = _isVisable;
    }

/* ProductListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductListItem(Context context, AttributeSet attrs, int defStylet) {
        super(context, attrs);
    }*/

    /*@Override
    public String toString() {
        return "Product [id=" + _ID + ", name=" + _name + ", type=" + _type + ", count=" + _itemCount
                + ", resource=" + _resource + "]";
    }*/

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", _name);
            jsonObject.put("type", _type);
            jsonObject.put("count", _itemCount);
            jsonObject.put("resource", _resource);
            //jsonObject.put("category", _category);
            jsonObject.put("category", _categoryString);
            jsonObject.put("show", _isVisable);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getName() {

        return _name;
    }

    public String getType() {
        return _type;
    }

    public int getitemCount() {
        return _itemCount;
    }

    public int getResource() {
        return _resource;
    }

    public String getCategory() {
        return _categoryString;
    }

    public boolean isVisable() {
        return _isVisable;
    }

    public void incrementItemCount() {
        _itemCount++;
    }

    public void decreaseItemCount() {
        _itemCount--;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setProductType(String type) {
        _type = type;
    }

    public void setCount(int count) {
        _itemCount = count;
    }

    public void setRourceID(int resource) {
        _resource = resource;
    }

    public void setCategory(String cat) {
        _categoryString = cat;
    }

    public void setvisable(boolean visable) {
        _isVisable = visable;
    }


}
