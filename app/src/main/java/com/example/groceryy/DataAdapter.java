package com.example.groceryy;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataAdapter extends BaseAdapter{

    private Context Context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> d;
    ArrayList<String> e;
    ArrayList<String> f;

    public DataAdapter(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c,ArrayList<String> d,ArrayList<String> e,ArrayList<String> f) {
        this.Context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return c.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.activity_data_adapter, null);

        }
        else
        {
            gridView=(View)convertView;

        }
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(Context.getApplicationContext());
        TextView tv1=(TextView)gridView.findViewById(R.id.textView1);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView3);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView4);
        Button b1=(Button)gridView.findViewById(R.id.btn1);
        EditText e1=(EditText)gridView.findViewById(R.id.e1);
        b1.setTag(position);
        e1.setFocusable(true);
        // CheckBox cb=(CheckBox)gridView.findViewById(R.id.cb);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));
        tv4.setText(d.get(position));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = e1.getText().toString();
                int pos = (Integer)view.getTag();

                String x=a.get(pos).toString();
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("pid",qty);
                //ed.putString("longi",longi.get(i));
                //   ed.putString("path",path.get(i));
                ed.commit();
                Intent i = new Intent(Context.getApplicationContext(),viewproduct.class);
                Context.startActivity(i);
                //  Intent j=new Intent(DataAdapter.this,ViewProductFinal.class);
              //  startActivity(j);
                Toast.makeText(Context.getApplicationContext(),x, Toast.LENGTH_SHORT).show();

            }
        });




        return gridView;

    }



}

