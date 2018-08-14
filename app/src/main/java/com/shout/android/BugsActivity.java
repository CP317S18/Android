package com.shout.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class BugsActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug_report);

        ImageButton back = findViewById(R.id.bugsBack);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));

    }
}
