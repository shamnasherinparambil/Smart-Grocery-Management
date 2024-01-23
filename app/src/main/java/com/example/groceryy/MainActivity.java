package com.example.groceryy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText ed;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=(EditText) findViewById(R.id.ed1);
        bt=(Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = ed.getText().toString();
                if (val.length() == 0) {
                    ed.setError("Missing");
                } else {
                    SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = s.edit();
                    ed.putString("ip", val);
                    ed.commit();
                    startActivity(new Intent(getApplicationContext(),login.class));
                }
            }

        });
    }
}