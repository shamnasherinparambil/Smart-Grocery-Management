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

public class view_recipe extends AppCompatActivity {
ListView li;
    String[] product_name, Recipies,id,rec_pic,Recipies_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        li=(ListView) findViewById(R.id.li);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String ip=sh.getString("ip","");
        String url="http://"+ip+":5000/recipe";

//        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                        JSONArray js = jsonObj.getJSONArray("data");
//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

//                        id=new String[js.length()];
                        product_name = new String[js.length()];
                        Recipies = new String[js.length()];
                        rec_pic = new String[js.length()];
                        Recipies_name=new String[js.length()];

                        for (int i = 0; i < js.length(); i++)
                        {
                            JSONObject u = js.getJSONObject(i);

//                            id[i]=u.getString("id");
                            product_name[i] = u.getString("product_name");
                            Recipies[i] = u.getString("Recipies");
                            rec_pic[i] = u.getString("rec_pic");
                            Recipies_name[i]=u.getString("Recipies_name");


                        }

                        li.setAdapter(new Custom_view_recipe(getApplicationContext(),product_name, Recipies,rec_pic,Recipies_name));
                    } else {
                        Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
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

                return params;
            }
        };
        requestQueue.add(postRequest);

    }
}