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

public class Custom_view_recipe  extends BaseAdapter {
    private final Context context;
    String[] product_name,Recipies,rec_pic,Recipies_name;

    public Custom_view_recipe(Context applicationContext, String[] product_name, String[] Recipies,String[] rec_pic,String[] Recipies_name) {
        this.context = applicationContext;
//        this.id=id;
        this.product_name = product_name;
        this.Recipies = Recipies;
        this.rec_pic = rec_pic;
        this.Recipies_name=Recipies_name;


    }

    @Override
    public int getCount() {
        return product_name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_recipe, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.t1);
        TextView tv2 = (TextView) gridView.findViewById(R.id.t2);
        TextView tv3 = (TextView) gridView.findViewById(R.id.t3);

//        tv1.setTextColor(Color.);
//        tv2.setTextColor(Color.BLACK);
        tv1.setText(product_name[i]);
        tv2.setText(Recipies[i]);
        tv3.setText(Recipies_name[i]);
        ImageView tvimage = (ImageView) gridView.findViewById(R.id.imageView);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/static/media/" + rec_pic[i];
//        Toast.makeText(context, "---" + url, Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(url).into(tvimage);



        return gridView;
    }
}
