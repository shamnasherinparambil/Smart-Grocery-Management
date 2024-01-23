package com.example.groceryy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_quality extends BaseAdapter {
    private final Context context;
    String[] product_name, quality,Photo;

    public Custom_view_quality(Context applicationContext, String[] pro_name1, String[] quality1,String[] Photo) {
        this.context = applicationContext;
//        this.id=id;
        this.product_name = pro_name1;
        this.quality = quality1;
        this.Photo=Photo;
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
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_quality, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.t1);
        TextView tv2 = (TextView) gridView.findViewById(R.id.t2);
//        TextView tv3 = (TextView) gridView.findViewById(R.id.t3);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);


        tv1.setText(product_name[i]);
        tv2.setText(quality[i]);
//        tv3.setText(status[i]);
        ImageView tvimage = (ImageView) gridView.findViewById(R.id.imageView3);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");

        String url = "http://" + ip + ":5000/static/media/" + Photo[i];
//        Toast.makeText(context, "---" + url, Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(url).into(tvimage);

        return gridView;
    }
}
