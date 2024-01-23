package com.example.groceryy;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom_view_order extends BaseAdapter {
    private final Context context;
    String[] product_name, order_date, quantity;

    public Custom_view_order(Context applicationContext, String[] product_name, String[] order_date, String[] quantity) {
        this.context = applicationContext;
//        this.id=id;
        this.product_name = product_name;
        this.order_date = order_date;
        this.quantity = quantity;
//        this.amount = amount;
    }

    @Override
    public int getCount() {
        return product_name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
//            gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_order, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.t1);
        TextView tv2 = (TextView) gridView.findViewById(R.id.t2);
        TextView tv3 = (TextView) gridView.findViewById(R.id.t3);
//        TextView tv4 = (TextView) gridView.findViewById(R.id.t4);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
//        tv4.setTextColor(Color.BLACK);


        tv1.setText(product_name[i]);
        tv2.setText(order_date[i]);
        tv3.setText(quantity[i]);
//


        return gridView;
    }
}