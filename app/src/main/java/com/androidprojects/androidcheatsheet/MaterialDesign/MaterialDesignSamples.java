package com.androidprojects.androidcheatsheet.MaterialDesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

import com.androidprojects.androidcheatsheet.R;

/**
 * Created by Nikhitha Tarala on 2/23/2017.
 */

public class MaterialDesignSamples extends AppCompatActivity {

    CheckBox checkBox;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

    }
}
