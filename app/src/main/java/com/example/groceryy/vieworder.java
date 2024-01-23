package com.example.groceryy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class vieworder extends AppCompatActivity {
    ListView l;
    String[] product_name,order_date,quantity,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworder);
        l=(ListView) findViewById(R.id.li);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String ip=sh.getString("ip","");
        String url="http://"+ip+":5000/view_order";

//        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                        JSONArray js = jsonObj.getJSONArray("data");
//                        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();

//                        id=new String[js.length()];
                        product_name = new String[js.length()];
                        order_date = new String[js.length()];
                        quantity= new String[js.length()];
//                        amount= new String[js.length()];
                        for (int i = 0; i < js.length(); i++)
                        {
                            JSONObject u = js.getJSONObject(i);

//                            id[i]=u.getString("id");
                            product_name[i] = u.getString("product_name");
                            order_date[i] = u.getString("order_date");
                            quantity[i] = u.getString("quantity");
//                            amount[i] = u.getString("amount");

                        }

                        l.setAdapter(new Custom_view_order(getApplicationContext(),product_name, order_date,quantity));
                    } else {
                        Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"eee"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams()
            {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String,String> params=new HashMap<>();

                String lid = sh.getString("lid", "");
                params.put("lid",lid);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}