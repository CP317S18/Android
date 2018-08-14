package com.shout.android;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class TermsOfServicesActivity extends AppCompatActivity {
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        ImageButton back = findViewById(R.id.tosBack);
        back.setOnClickListener((view) -> NavUtils.navigateUpFromSameTask(this));
    }
}