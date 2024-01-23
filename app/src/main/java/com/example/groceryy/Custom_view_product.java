package com.example.groceryy;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Custom_view_product extends BaseAdapter {
    private final Context context;
    String[] product_name, Photo, price, quantity, id;


    public static final float t=0f;
    public Custom_view_product(Context applicationContext, String[] product_name, String[] price, String[] quantity, String[] Photo, String[] id) {
        this.context = applicationContext;

        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.quantity = quantity;
        this.Photo = Photo;

    }

    @Override
    public int getCount() {
        return id.length;
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
            gridView = inflator.inflate(R.layout.custom_view_product, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.t1);
        TextView tv2 = (TextView) gridView.findViewById(R.id.t2);
        TextView tv3 = (TextView) gridView.findViewById(R.id.t3);




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(product_name[i]);
        tv2.setText(price[i]);
        tv3.setText(quantity[i]);
        ImageView tvimage = (ImageView) gridView.findViewById(R.id.imageView2);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");

        String url = "http://" + ip + ":5000/static/media/" + Photo[i];
//        Toast.makeText(context, "---" + url, Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(url).into(tvimage);
        EditText ed=(EditText)gridView.findViewById(R.id.ed2);
        Button bt = (Button) gridView.findViewById(R.id.button4);
        Button bt1 = (Button) gridView.findViewById(R.id.button5);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String qty = ed.getText().toString();

                    String p = id[i].toString();
                    String pr = price[i].toString();
                    String ip = sh.getString("ip", "");
                    String url = "http://" + ip + ":5000/removecart";
                    RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                            Intent k = new Intent(context.getApplicationContext(), viewproduct.class);
                                            // SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                                            //SharedPreferences.Editor ed = sh.edit();
                                            //ed.putString("tota",String.valueOf(x));
                                            // ed.commit();
                                            String ft = jsonObj.getString("sum");
                                            k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            k.putExtra("total", ft);
                                            context.startActivity(k);

                                        } else {
                                            Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                            String lid = sh.getString("lid", "");
                            params.put("lid", lid);
                            params.put("pid", p);


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
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = ed.getText().toString();
                if(qty.equalsIgnoreCase("")) {
                    ed.setError("enter quantity");
                }
                else {
                    String p = id[i].toString();
                    String pr = price[i].toString();
                    //float t1=25;

                    // Toast.makeText(context.getApplicationContext(), String.valueOf(x), Toast.LENGTH_LONG).show();

                    //float x=t+t1;

                    String finalt = "25";
                    //Intent i=new Intent();

                    String ip = sh.getString("ip", "");
                    String url = "http://" + ip + ":5000/addorder";
                    RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    // response
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(context.getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                            Intent k = new Intent(context.getApplicationContext(), viewproduct.class);
                                            // SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                                            //SharedPreferences.Editor ed = sh.edit();
                                            //ed.putString("tota",String.valueOf(x));
                                            // ed.commit();
                                            bt.setEnabled(false);
                                            bt1.setEnabled(true);
                                            String ft = jsonObj.getString("sum");
                                            k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            k.putExtra("total", ft);
                                            context.startActivity(k);

                                        } else {
                                            Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                            String lid = sh.getString("lid", "");
                            params.put("lid", lid);
                            SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                            String pid = sh.getString("pid", "");
                            params.put("pid", p);
                            params.put("qty", qty);


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
            }


        });



        return gridView;
    }

}