package com.mattsmith.demigodstocklevel;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mattb_000 on 14/12/2017.
 */

public class ProductListAdapter extends ArrayAdapter<ProductListItem> {

    ProductListItem_Delegate _delegate;

    public ProductListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ProductListAdapter(Context context, int resource, List<ProductListItem> items) {
        super(context, resource, items);
    }

    public void setDelegate(ProductListItem_Delegate delegate) {
        _delegate = delegate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.product_list_item, null);
        }

        ProductListItem p = getItem(position);

        if (p != null) {


            if (v != null) {
                AppCompatImageView acIAddView = (AppCompatImageView) v.findViewById(R.id.product_item_add);
                acIAddView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _delegate.onClickIncrement(((TextView) (
                                (View) v.getParent())
                                .findViewById(R.id.product_item_text)).getText().toString()
                        );
                        notifyDataSetChanged();
                    }
                });

                AppCompatImageView acRemoveIView = (AppCompatImageView) v.findViewById(R.id.product_item_remove);
                acRemoveIView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _delegate.onClickDecrease(((TextView) (
                                (View) v.getParent())
                                .findViewById(R.id.product_item_text)).getText().toString()
                        );
                        notifyDataSetChanged();
                    }
                });

                EditText itemCountView = (EditText) v.findViewById(R.id.product_item_count);
                itemCountView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Log.d("Matt", "actionId: " + actionId + "/KeyEvent: " + event);
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            _delegate.onCountManuallyUpdated(((TextView) (
                                    (View) v.getParent())
                                    .findViewById(R.id.product_item_text)).getText().toString(), ((TextView) (
                                    (View) v.getParent())
                                    .findViewById(R.id.product_item_count)).getText().toString()
                            );
                            notifyDataSetChanged();
                        }
                        return false;
                    }
                });
            }
           /* if(p.isVisable())
                parent.removeView(v);
            else
                parent.addView(v);*/
            v.setVisibility(p.isVisable() ? View.VISIBLE : View.GONE);
            //v. (p.isVisable() ? View.VISIBLE : View.GONE);

            if (v.getVisibility() == View.GONE) {
                TextView tmp = (EditText) v.findViewById(R.id.product_item_count);
                tmp.setFocusable(false);
            }
            else if (v.getVisibility() == View.VISIBLE)
            {
                TextView tr = (EditText) v.findViewById(R.id.product_item_count);
               tr.setFocusable(true);
            }

            //Log.d("Matt", "ID: "+ p.get() );

            TextView tt1 = (EditText) v.findViewById(R.id.product_item_count);
            TextView tt2 = (TextView) v.findViewById(R.id.product_item_text);

            if (tt1 != null) {
                int tmpCount = p.getitemCount();
                tt1.setText(Integer.toString(tmpCount));


                if (tmpCount <= 5) tt1.setBackgroundColor(Color.RED);
                if (tmpCount > 5 && tmpCount < 10) tt1.setBackgroundColor(Color.rgb(255, 140, 0));
                if (tmpCount >= 10) tt1.setBackgroundColor(Color.GREEN);
            }

            if (tt2 != null) {
                tt2.setText(p.getName());
                tt2.setBackgroundResource(p.getResource());
            }
            if ((tt1 != null) && (tt2 != null)) {
                Log.d("Matt", "Log: '" + p.getName() + "' - " + Integer.toString(p.getitemCount()));
            }
        }
        Log.d("Matt", "getView called");
        return v;
    }

    @Override
    public int getCount() {
        Log.d("Matt", "Log: 'super.getCount()' - " + Integer.toString(super.getCount()));
        return super.getCount();
    }
}
