package com.androidprojects.androidcheatsheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.androidprojects.androidcheatsheet.Charts.LineChartActivity;
import com.androidprojects.androidcheatsheet.Firebase.FirebaseActivity;
import com.androidprojects.androidcheatsheet.MaterialDesign.MaterialDesignSamples;
import com.androidprojects.androidcheatsheet.Samples.IntentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutFirebase = (LinearLayout) findViewById(R.id.linearFirebase);
        layoutFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirebaseActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutBroadcast = (LinearLayout) findViewById(R.id.linearBR);
        layoutBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntentActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutMaterial = (LinearLayout) findViewById(R.id.linearMaterial);
        layoutMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MaterialDesignSamples.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutCharts = (LinearLayout) findViewById(R.id.layoutCharts);
        layoutMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(intent);
            }
        });

    }


}
