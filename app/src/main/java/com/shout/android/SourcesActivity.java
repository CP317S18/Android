package com.shout.android;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class SourcesActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);


        ImageButton back = findViewById(R.id.sourcesBack);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));
    }
}