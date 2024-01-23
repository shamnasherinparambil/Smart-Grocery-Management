package com.example.groceryy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewProductFinal extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Handler handler;
    ListView lv;
    TextView t1;
    SharedPreferences sp;
    String url = "";
    String val="";
    ArrayList<String> id,name,price,quantity,a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_final);
        lv = (ListView) findViewById(R.id.listView);
        t1=(TextView)findViewById(R.id.textView2);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        val=sp.getString("pid", "");
        t1.setText(val);
        url = "http://" + sp.getString("ip", "") + ":5000/viewproduct";
        handler = new Handler();
        updateDateDisplay();

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {

        }
    }
    Runnable r=new Runnable() {

        @Override
        public void run() {
            updateDateDisplay();
//            test();
            handler.postDelayed(r, 3000);
        }
    };
    void updateDateDisplay() {

        RequestQueue requestQueue= Volley.newRequestQueue(ViewProductFinal.this);

        Toast.makeText(getApplicationContext(),url, Toast.LENGTH_LONG).show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    if (jsonArray.length() > 0) {

                        id = new ArrayList<String>();
                        name= new ArrayList<String>();

                        price = new ArrayList<String>();
                        quantity = new ArrayList<String>();
                        a = new ArrayList<String>();
                        b = new ArrayList<String>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jo = jsonArray.getJSONObject(i);
                            id.add(jo.getString("id"));
                            name.add(jo.getString("product_name"));
                            price.add(jo.getString("price"));
                            quantity.add(jo.getString("quantity"));

                        }
                        a.add("0");
                        b.add("0");
                        lv.setAdapter(new DataAdapter(getApplicationContext(),id,name,price,quantity,a,b));
                        lv.setOnItemClickListener(ViewProductFinal.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewProductFinal.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);


    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //SharedPreferences.Editor ed = sp.edit();
        Toast.makeText(ViewProductFinal.this,a.get(i), Toast.LENGTH_LONG).show();
        //Intent j=new Intent(ViewProductFinal.this,Home.class);
        //startActivity(j);

        //ed.putString("lati",lati.get(i));
        //ed.putString("longi",longi.get(i));
        //   ed.putString("path",path.get(i));
       // ed.commit();

    }
}