package com.example.groceryy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class viewproduct extends AppCompatActivity {
    String[] id,product_name,Photo,price,quantity;
    ListView l;
    TextView t1,t2;
    Button b1;
    public static final int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);
        l=(ListView) findViewById(R.id.li);
        t1=(TextView)findViewById(R.id.textView2);
        b1=(Button)findViewById(R.id.button10);

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Intent k=getIntent();
        String v=k.getStringExtra("total");
        t1.setText(v);






        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String ip=sh.getString("ip","");
        String lid = sh.getString("lid", "");
        String url="http://"+ip+":5000/view_product";


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

                        id=new String[js.length()];
                        product_name = new String[js.length()];
                        price = new String[js.length()];
                        quantity = new String[js.length()];
                        Photo=new String[js.length()];
//                        type = new String[js.length()];
//                        status = new String[js.length()];
                        for (int i = 0; i < js.length(); i++)
                        {
                            JSONObject u = js.getJSONObject(i);

                            id[i]=u.getString("id");
                            product_name[i] = u.getString("product_name");
                            price[i] = u.getString("price");
                            quantity[i] = u.getString("quantity");
                            Photo[i]=u.getString("Photo");
//                            type[i] = u.getString("type");
//                            status[i] = u.getString("status");


                        }

                        l.setAdapter(new Custom_view_product(getApplicationContext(),product_name, price,quantity,Photo,id));
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


                return params;
            }
        };
        requestQueue.add(postRequest);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://" + ip + ":5000/finalorder";
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("success"))
                                    {

                                        t1.setText(jsonObj.getString("sum"));

                                            Toast.makeText(viewproduct.this, "Successfully Logined", Toast.LENGTH_SHORT).show();


                                    }


                                    // }
                                    else {
                                        Toast.makeText(getApplicationContext(), "cart list not available", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("id", lid);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        });
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        startActivity(new Intent(getApplicationContext(),Home.class));
    }
}